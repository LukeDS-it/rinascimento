package it.ldsoftware.rinascimento.service

import it.ldsoftware.rinascimento.exception.DirectoryCreationException
import it.ldsoftware.rinascimento.exception.TenantExistingException
import it.ldsoftware.rinascimento.multitenancy.MultiTenancyUtils
import it.ldsoftware.rinascimento.multitenancy.TenantResolver
import it.ldsoftware.rinascimento.view.install.DatabaseConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InstallationService {

    @Autowired
    MultiTenancyUtils utils

    @Autowired
    TenantResolver resolver

    void mkdirs() {
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
    }

    void installDatabase(DatabaseConfig config) {
        File propFile = new File(utils.getTenantProperties())

        propFile.text = ""
            + config.url ? "database.url=${config.url}\n" : ""
            + config.username ? "database.user=${config.username}\n" : ""
            + config.password ? "database.pass=${config.password}\n" : ""
            + config.driverClass ? "database.driverClassName=${config.driverClass}\n" : ""
    }
}
