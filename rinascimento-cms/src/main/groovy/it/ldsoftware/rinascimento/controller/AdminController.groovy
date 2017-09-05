package it.ldsoftware.rinascimento.controller

import it.ldsoftware.rinascimento.config.RinascimentoProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@EnableConfigurationProperties(RinascimentoProperties.class)
class AdminController extends AbstractPageController {

    @Autowired
    RinascimentoProperties properties

    @ResponseBody
    @RequestMapping("/cms-install-login")
    String configurerLoginCheck() {
        serveStaticPage('cms-install-login.html')
    }

    @ResponseBody
    @RequestMapping("/cms-install")
    String configurer() {
       serveStaticPage('cms-install.html')
    }

    @ResponseBody
    @RequestMapping("/cms-admin-login")
    String adminPanelLogin() {
       serveStaticPage('cms-admin-login.html')
    }

    @ResponseBody
    @RequestMapping("/cms-admin")
    String adminPanel() {
       serveStaticPage('cms-admin.html')
    }

}
