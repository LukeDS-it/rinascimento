package it.ldsoftware.rinascimento.service.impl

import groovy.util.logging.Slf4j
import it.ldsoftware.primavera.presentation.base.AppPropertyDTO
import it.ldsoftware.primavera.presentation.people.UserVM
import it.ldsoftware.primavera.services.interfaces.GroupService
import it.ldsoftware.primavera.services.interfaces.PropertyService
import it.ldsoftware.primavera.services.interfaces.RoleService
import it.ldsoftware.rinascimento.exception.DatabaseCreationException
import it.ldsoftware.rinascimento.exception.DirectoryCreationException
import it.ldsoftware.rinascimento.exception.SiteConfigurationException
import it.ldsoftware.rinascimento.exception.TenantExistingException
import it.ldsoftware.rinascimento.multitenancy.DataSourceProvider
import it.ldsoftware.rinascimento.multitenancy.MultiTenancyUtils
import it.ldsoftware.rinascimento.multitenancy.TenantResolver
import it.ldsoftware.rinascimento.service.InstallationService
import it.ldsoftware.rinascimento.service.TemplateService
import it.ldsoftware.rinascimento.util.Constants
import it.ldsoftware.rinascimento.util.TemplateImporter
import it.ldsoftware.rinascimento.view.install.DatabaseConfig
import org.hibernate.HibernateException
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Environment
import org.hibernate.dialect.Dialect
import org.hibernate.engine.jdbc.dialect.internal.DialectFactoryImpl
import org.hibernate.engine.jdbc.dialect.internal.StandardDialectResolver
import org.hibernate.engine.jdbc.dialect.spi.DatabaseMetaDataDialectResolutionInfoAdapter
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfoSource
import org.hibernate.tool.hbm2ddl.SchemaExport
import org.hibernate.tool.schema.TargetType
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
import java.sql.SQLException

@Slf4j
@Service
class InstallationServiceImpl implements InstallationService {

    @Autowired
    MultiTenancyUtils utils

    @Autowired
    TenantResolver resolver

    @Autowired
    EntityManager entityManager

    @Autowired
    ApplicationContext context

    @Autowired
    DataSourceProvider dsProvider

    @Autowired
    PropertyService props

    @Autowired
    RoleService roles

    @Autowired
    GroupService groups

    @Autowired
    TemplateService templates

    @Override
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

    @Override
    void installDatabase(DatabaseConfig config) {
        try {
            def properties = new DataSourceProperties(username: config.username, password: config.password, url: config.url)
            def propFile = new File(utils.getTenantProperties())

            propFile.createNewFile()

            StringBuilder sb = new StringBuilder()
            if (config.url)
                sb.append "database.url=${config.url}\n"
            if (config.username)
                sb.append "database.user=${config.username}\n"
            if (config.password)
                sb.append "database.pass=${config.password}\n"
            if (config.driverClass)
                sb.append "database.driverClassName=${config.driverClass}\n"


            propFile.setText sb.toString()

            def dialect = config.dialect ?: determineDialect(config)

            def metadata = new MetadataSources(
                    new StandardServiceRegistryBuilder()
                            .applySetting(Environment.DATASOURCE, dsProvider.getDataSource(resolver.resolveCurrentTenantIdentifier()))
                            .applySetting(Environment.DRIVER, properties.determineDriverClassName())
                            .applySetting(Environment.DIALECT, dialect)
                            .build()
            )

            def scanner = new ClassPathScanningCandidateComponentProvider(true)
            scanner.addIncludeFilter(new AnnotationTypeFilter(Entity))

            MultiTenancyUtils.getPackagesToScan(context).collect {
                scanner.findCandidateComponents(it)
            }.flatten().each { it ->
                metadata.addAnnotatedClass(Class.forName(((BeanDefinition) it).getBeanClassName()))
            }

            log.info "Creating DDL for tenant ${resolver.resolveCurrentTenantIdentifier()}"
            new SchemaExport()
                    .setFormat(true)
                    .createOnly(EnumSet.of(TargetType.STDOUT, TargetType.DATABASE), metadata.buildMetadata())
        } catch (Exception e) {
            throw new DatabaseCreationException(e)
        }
    }

    private Dialect determineDialect(DatabaseConfig config) {
        def conn = DriverManager.getConnection(config.url, config.username, config.password)
        def dialectFactory = new DialectFactoryImpl(dialectResolver: new StandardDialectResolver())
        Dialect d = dialectFactory.buildDialect([:], new DialectResolutionInfoSource() {
            @Override
            DialectResolutionInfo getDialectResolutionInfo() {
                try {
                    return new DatabaseMetaDataDialectResolutionInfoAdapter( conn.getMetaData() )
                }
                catch ( SQLException sqlException ) {
                    throw new HibernateException(
                            "Unable to access java.sql.DatabaseMetaData to determine appropriate Dialect to use",
                            sqlException
                    )
                }
            }
        })
        conn.close()
        return d
    }

    @Override
    void addBaseData() {
        try {
            // TODO: default roles and groups from primavera
            // PrimaveraConstants.BASE_ROLES.each { roles.save it }
            // PrimaveraConstants.BASE_GROUPS.each { groups.save it }
            // TODO: groups must have roles inside
            Constants.BASE_ROLES.each { roles.save it }
            Constants.BASE_GROUPS.each { groups.save it }
            new File(getClass().getResource("classpath:/templates").toURI())
                    .listFiles()
                    .findAll { it.name.endsWith('.ezt') }
                    .collect { TemplateImporter.importTemplate it.text }
                    .each { templates.save it }
        } catch (Exception e) {
            throw new SiteConfigurationException("Error while adding basic data to the database", e)
        }
    }

    @Override
    void configureWebsite(List<AppPropertyDTO> properties) {
        try {
            properties.each { props.save it }
        } catch (Exception e) {
            throw new SiteConfigurationException("Could not configure website", e)
        }
    }

    @Override
    void configureUser(UserVM user) {
        createHomePage()
    }

    @Override
    void createHomePage() {

    }
}
