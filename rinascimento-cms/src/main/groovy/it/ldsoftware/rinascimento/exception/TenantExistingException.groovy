package it.ldsoftware.rinascimento.exception

class TenantExistingException extends InstallationException {

    String tenant

    TenantExistingException(String tenant) {
        super("Tenant ${tenant} already exists")
        this.tenant = tenant
    }
}
