package it.ldsoftware.rinascimento.exception

class TenantExistingException extends RuntimeException {

    String tenant

    TenantExistingException(String tenant) {
        super("Tenant ${tenant} already exists")
        this.tenant = tenant
    }
}
