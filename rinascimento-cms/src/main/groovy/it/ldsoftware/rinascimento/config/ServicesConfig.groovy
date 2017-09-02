package it.ldsoftware.rinascimento.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = "it.ldsoftware.rinascimento.services")
class ServicesConfig {
}
