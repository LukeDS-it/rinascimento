package it.ldsoftware.rinascimento.exception

class DatabaseCreationException extends InstallationException {
    DatabaseCreationException(Throwable t) {
        super("Unexpected exception while creating database: ${t.message}", t)
    }
}
