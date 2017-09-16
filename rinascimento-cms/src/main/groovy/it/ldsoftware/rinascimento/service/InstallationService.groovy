package it.ldsoftware.rinascimento.service

import groovy.util.logging.Slf4j
import it.ldsoftware.primavera.dal.people.UserDAL
import it.ldsoftware.rinascimento.exception.DatabaseCreationException
import it.ldsoftware.rinascimento.exception.DirectoryCreationException
import it.ldsoftware.rinascimento.exception.TenantExistingException
import it.ldsoftware.rinascimento.multitenancy.MultiTenancyUtils
import it.ldsoftware.rinascimento.multitenancy.TenantResolver
import it.ldsoftware.rinascimento.view.install.DatabaseConfig
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.boot.spi.MetadataImplementor
import org.hibernate.cfg.Environment
import org.hibernate.tool.hbm2ddl.SchemaExport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AnnotationTypeFilter
import org.springframework.stereotype.Service

import javax.persistence.Entity
import javax.persistence.EntityManager
import java.sql.DriverManager

@Slf4j
@Service
class InstallationService {

    @Autowired
    MultiTenancyUtils utils

    @Autowired
    TenantResolver resolver

    @Autowired
    EntityManager entityManager

    @Autowired
    ApplicationContext context

    @Autowired
    UserDAL userDAL

    void mkdirs() {
        try {
            def dirs = [
                    utils.getTenantTemplateDir(),
                    utils.getTenantExtensionDir(),
                    utils.getTenantResourceDir()
            ]

            def file = new File(utils.getTenantRootDir())
            if (file.exists())
                throw new TenantExistingException(resolver.resolveCurrentTenantIdentifier())

            file.mkdirs()

            boolean success = dirs
                    .collect { new File(it).mkdirs() }
                    .inject { acc, val -> acc && val }

            if (!success)
                throw new DirectoryCreationException("Could not create directories ${dirs}, please check permissions")

            def propFile = new File(utils.getTenantProperties())

            if (!propFile.createNewFile())
                throw new DirectoryCreationException("Could not create property file, please check permissions")
        } catch (DirectoryCreationException rethrow) {
            throw rethrow
        } catch (Exception e) {
            throw new DirectoryCreationException(e)
        }
    }

    void installDatabase(DatabaseConfig config) {
        try {
            def properties = new DataSourceProperties(username: config.username, password: config.password, url: config.url)

            def propFile = new File(utils.getTenantProperties())

            propFile.createNewFile()

            propFile.setText(""
                    + config.url ? "database.url=${config.url}\n" : ""
                    + config.username ? "database.user=${config.username}\n" : ""
                    + config.password ? "database.pass=${config.password}\n" : ""
                    + config.driverClass ? "database.driverClassName=${config.driverClass}\n" : "")

            def conn = DriverManager.getConnection(config.url, config.username, config.password)


            def dialect = config.dialect ?: "org.hibernate.dialect.H2Dialect" // FIXME programatically get it

            def metadata = new MetadataSources(
                    new StandardServiceRegistryBuilder()
                        .applySetting(Environment.DRIVER, properties.determineDriverClassName())
                        .applySetting(Environment.DIALECT, dialect)
                        .build()
            )

            def scanner = new ClassPathScanningCandidateComponentProvider(true)
            scanner.addIncludeFilter(new AnnotationTypeFilter(Entity))

            MultiTenancyUtils.getPackagesToScan(context).collect {
                scanner.findCandidateComponents(it)
            }.flatten().each { it ->
                metadata.addAnnotatedClass(Class.forName(((BeanDefinition)it).getBeanClassName()))
            }

            def export = new SchemaExport(metadata.buildMetadata() as MetadataImplementor, conn)

            log.info "Creating DDL for tenant ${resolver.resolveCurrentTenantIdentifier()}"

            export.create(true, true)
        } catch (Exception e) {
            throw new DatabaseCreationException(e)
        }
    }

}
