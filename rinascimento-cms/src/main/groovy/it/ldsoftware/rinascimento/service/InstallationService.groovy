package it.ldsoftware.rinascimento.service

import groovy.util.logging.Slf4j
import it.ldsoftware.rinascimento.exception.DatabaseCreationException
import it.ldsoftware.rinascimento.exception.DirectoryCreationException
import it.ldsoftware.rinascimento.exception.TenantExistingException
import it.ldsoftware.rinascimento.multitenancy.MultiTenancyUtils
import it.ldsoftware.rinascimento.multitenancy.TenantResolver
import it.ldsoftware.rinascimento.view.install.DatabaseConfig
import org.hibernate.cfg.Configuration
import org.hibernate.cfg.Environment
import org.hibernate.tool.hbm2ddl.SchemaExport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.stereotype.Service

import javax.persistence.EntityManager

@Slf4j
@Service
class InstallationService {

    @Autowired
    MultiTenancyUtils utils

    @Autowired
    TenantResolver resolver

    @Autowired
    EntityManager entityManager

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
                    .findAll { !it }

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

            propFile.text = ""
            +config.url ? "database.url=${config.url}\n" : ""
            +config.username ? "database.user=${config.username}\n" : ""
            +config.password ? "database.pass=${config.password}\n" : ""
            +config.driverClass ? "database.driverClassName=${config.driverClass}\n" : ""

            def configuration = new Configuration()
            config.setProperty(Environment.SHOW_SQL, true)
            config.setProperty(Environment.HBM2DDL_AUTO, "create")
            config.setUrl(config.url)
            config.setUsername(config.username)
            config.setPassword(config.password)
            config.setDriverClass(properties.determineDriverClassName())

            def export = new SchemaExport(configuration)

            log.info "Creating DDL for tenant ${resolver.resolveCurrentTenantIdentifier()}"

            export.create(true, true)

            addBasicData()
        } catch (Exception e) {
            throw new DatabaseCreationException(e)
        }
    }

    private void addBasicData() {

    }
}
