package it.ldsoftware.rinascimento.config

import it.ldsoftware.rinascimento.multitenancy.MultiTenancyUtils
import org.hibernate.context.spi.CurrentTenantIdentifierResolver
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter

import javax.sql.DataSource

import static org.hibernate.MultiTenancyStrategy.DATABASE
import static org.hibernate.cfg.AvailableSettings.*

/**
 * Configuration for multitenancy
 *
 * @author Luca Di Stefano
 */
@Configuration
@EnableConfigurationProperties(JpaProperties.class)
class MultiTenancyConfig {

    @Autowired
    private DataSource dataSource
    @Autowired
    private JpaProperties jpaProperties
    @Autowired
    private MultiTenantConnectionProvider provider
    @Autowired
    private CurrentTenantIdentifierResolver resolver

    @Bean
    JpaVendorAdapter jpaVendorAdapter() {
        new HibernateJpaVendorAdapter()
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(ApplicationContext context) {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean()
        bean.setDataSource(dataSource)
        bean.setPackagesToScan(MultiTenancyUtils.getPackagesToScan(context))
        bean.setJpaVendorAdapter(jpaVendorAdapter())

        Map<String, Object> jpaProperties = new HashMap<>()
        jpaProperties.put(MULTI_TENANT, DATABASE)
        jpaProperties.put(MULTI_TENANT_CONNECTION_PROVIDER, provider)
        jpaProperties.put(MULTI_TENANT_IDENTIFIER_RESOLVER, resolver)
        jpaProperties.putAll(this.jpaProperties.getHibernateProperties(dataSource))
        bean.setJpaPropertyMap(jpaProperties)
        bean
    }

}
