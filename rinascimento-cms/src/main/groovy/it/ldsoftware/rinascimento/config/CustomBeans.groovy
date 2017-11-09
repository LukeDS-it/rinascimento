package it.ldsoftware.rinascimento.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.ITemplateEngine
import org.thymeleaf.TemplateEngine

@Configuration
class CustomBeans {
    @Bean
    ITemplateEngine getTemplateEngine() {
        new TemplateEngine()
    }
}
