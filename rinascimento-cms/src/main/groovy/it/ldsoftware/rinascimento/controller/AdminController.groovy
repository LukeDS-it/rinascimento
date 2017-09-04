package it.ldsoftware.rinascimento.controller

import it.ldsoftware.rinascimento.config.RinascimentoProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@EnableConfigurationProperties(RinascimentoProperties.class)
class AdminController {

    @Autowired
    RinascimentoProperties properties

    @RequestMapping("/cms-install-login")
    String configurerLoginCheck() {
        ""
    }

    @RequestMapping("/cms-install")
    String configurer() {
        ""
    }

}
