package it.ldsoftware.rinascimento.exception

class DirectoryCreationException extends InstallationException {
    DirectoryCreationException(String message) {
        super(message)
    }

    DirectoryCreationException(Throwable t) {
        super("Unexpected exception occurred while creating directories: ${t.message}", t)
    }
}
