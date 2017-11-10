package it.ldsoftware.rinascimento.exception

class InstallationException extends RuntimeException {

    InstallationException(String message) {
        super("Insallation error: ${message}")
    }

    InstallationException(String s, Throwable t) {
        super(s, t)
    }

}
