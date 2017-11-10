package it.ldsoftware.rinascimento.multitenancy

import it.ldsoftware.rinascimento.exception.TenantNotConfiguredException
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.stereotype.Service

import javax.sql.DataSource

/**
 * Builds all data sources for all tenants and gives back the one that is needed
 * for the current tenant.
 *
 * If at first the requested data source is not present in the list, then
 * tries to instantiate it, if this is not successful throws an exception.
 *
 * @author Luca Di Stefano
 */
@Service
class DataSourceProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    @Autowired
    private DataSource dataSource
    @Autowired
    private MultiTenancyUtils utils

    private Map<String, DataSource> sources = new HashMap<>()

    /**
     * This function will return the default DataSource created
     * instantiated by Spring at boot. This function is needed
     * because Hibernate does its initializations using that
     * DataSource.
     *
     * @return the default DataSource
     */
    @Override
    protected DataSource selectAnyDataSource() {
        return dataSource
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        sources.computeIfAbsent tenantIdentifier, {k -> getDataSource(tenantIdentifier)}
    }

    DataSource getDataSource(String tenant) {
        File file = new File(utils.getTenantProperties())
        Properties properties = new Properties()

        try {
            properties.load(new FileInputStream(file))
        } catch (IOException e) {
            throw new TenantNotConfiguredException(tenant, e)
        }

        DataSourceProperties dsProp = new DataSourceProperties()
        dsProp.setUrl(properties.getProperty("database.url"))
        dsProp.setUsername(properties.getProperty("database.user"))
        dsProp.setPassword(properties.getProperty("database.pass"))
        dsProp.determineDriverClassName()

        return DataSourceBuilder.create()
                .url(dsProp.getUrl())
                .driverClassName(dsProp.getDriverClassName())
                .username(dsProp.getUsername())
                .password(dsProp.getPassword())
                .build()
    }

}
