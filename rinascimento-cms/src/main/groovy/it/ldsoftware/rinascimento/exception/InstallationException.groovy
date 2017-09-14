package it.ldsoftware.rinascimento.exception

class InstallationException extends RuntimeException {

    InstallationException(String message) {
        super("Insallation error: ${message}")
    }

    InstallationException(Throwable t) {
        super("Generic installation error", t)
    }

}
