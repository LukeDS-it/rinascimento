package it.ldsoftware.rinascimento.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = 'it.ldsoftware.rinascimento.model')
@EnableJpaRepositories(basePackages = 'it.ldsoftware.rinascimento.repository')
@ComponentScan(basePackages = ['it.ldsoftware.rinascimento.mapper', 'it.ldsoftware.rinascimento.service'])
class BusinessConfig {
}
