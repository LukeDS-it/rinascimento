package it.ldsoftware.rinascimento.config

import it.ldsoftware.rinascimento.multitenancy.MultiTenancyInterceptor
import it.ldsoftware.rinascimento.multitenancy.MultiTenancyResourceResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.resource.PathResourceResolver

/**
 * This class configures the MVC environment adding the custom interceptor
 *
 * @author Luca Di Stefano
 */
@Configuration
@ComponentScan(basePackages = ["it.ldsoftware.rinascimento.multitenancy", "it.ldsoftware.rinascimento.controllers"])
class MvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private MultiTenancyResourceResolver resourceResolver
    @Autowired
    private MultiTenancyInterceptor interceptor

    @Override
    void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
    }

    @Override
    void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .resourceChain(false)
                .addResolver(resourceResolver)
                .addResolver(new PathResourceResolver())
        registry.addResourceHandler("/app/**")
                .resourceChain(false)
                .addResolver(resourceResolver)
        registry.addResourceHandler("/node_modules/**")
                .resourceChain(false)
                .addResolver(resourceResolver)
    }
}

