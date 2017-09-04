package it.ldsoftware.rinascimento.multitenancy

import org.hibernate.context.spi.CurrentTenantIdentifierResolver
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder

import static it.ldsoftware.rinascimento.multitenancy.MultiTenancyUtils.CURRENT_TENANT

/**
 * This class gives the current tenant
 *
 * @author Luca Di Stefano
 */

@Component
class TenantResolver implements CurrentTenantIdentifierResolver {

    static String getCurrentTenant() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes()
        String identifier = MultiTenancyUtils.DEFAULT_ID
        if (attributes != null) {
            String tmp = (String) attributes.getAttribute(CURRENT_TENANT, RequestAttributes.SCOPE_REQUEST)
            if (tmp != null)
                identifier = tmp
        }
        return identifier
    }

    @Override
    String resolveCurrentTenantIdentifier() {
        getCurrentTenant()
    }

    @Override
    boolean validateExistingCurrentSessions() {
        true
    }
}
