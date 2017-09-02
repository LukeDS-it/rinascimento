package it.ldsoftware.rinascimento.exception

/**
 * This exception is raised if an user is trying to browse a page for a tenant that
 * has not been configured yet.
 *
 * @author Luca Di Stefano
 */
class TenantNotConfiguredException extends RuntimeException {
    TenantNotConfiguredException(String tenant, Throwable e) {
        super("Tenant not configured: ${tenant}", e)
    }
}