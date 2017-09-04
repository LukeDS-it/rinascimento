package it.ldsoftware.rinascimento.service

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

    void mkdirs(DatabaseConfig config) {

        def dirs = [
                utils.getTenantTemplateDir(),
                utils.getTenantExtensionDir(),
                utils.getTenantResourceDir()
        ]

        File file = new File(utils.getTenantRootDir())
        if (file.exists())
            throw new TenantExistingException(resolver.resolveCurrentTenantIdentifier())

        file.mkdirs()

        dirs.each { new File(it).mkdirs() }

        File propFile = new File(utils.getTenantProperties())

        propFile.createNewFile()

        propFile.text = ""
            + config.url ? "database.url=${config.url}\n" : ""
            + config.username ? "database.user=${config.username}\n" : ""
            + config.password ? "database.pass=${config.password}\n" : ""
            + config.driverClass ? "database.driverClassName=${config.driverClass}\n" : ""
    }

    void installDatabase(DatabaseConfig config) {

    }
}
