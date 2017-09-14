package it.ldsoftware.rinascimento.rest

import it.ldsoftware.rinascimento.service.InstallationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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

    @GetMapping("/database")
    void installDatabase() {

    }

    @GetMapping("/administrator")
    void createAdmin() {

    }

    @GetMapping("/details")
    void siteDetails() {

    }

}
