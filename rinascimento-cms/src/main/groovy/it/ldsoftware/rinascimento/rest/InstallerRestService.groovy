package it.ldsoftware.rinascimento.rest

import it.ldsoftware.rinascimento.service.InstallationService
import it.ldsoftware.rinascimento.view.install.DatabaseConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/installer")
class InstallerRestService {

    @Autowired
    private InstallationService installationService

    @GetMapping("/directories")
    ResponseEntity<String> createDirectories() {
        installationService.mkdirs()
        ResponseEntity.ok("OK")
    }

    @PostMapping("/database")
    ResponseEntity<String> installDatabase(@RequestBody DatabaseConfig config) {
        installationService.installDatabase(config)
        ResponseEntity.ok("OK")
    }

    @GetMapping("/administrator")
    void createAdmin() {

    }

    @GetMapping("/details")
    void siteDetails() {

    }

}
