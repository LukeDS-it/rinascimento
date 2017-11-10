package it.ldsoftware.rinascimento.view.install

class InstallationError {
    String message, step

    InstallationError(String message, String step) {
        this.message = message
        this.step = step
    }
}
