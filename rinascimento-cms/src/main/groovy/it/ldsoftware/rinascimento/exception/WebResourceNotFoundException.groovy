package it.ldsoftware.rinascimento.exception

class WebResourceNotFoundException extends RuntimeException {

    WebResourceNotFoundException(String resource) {
        super("The resource at url ${resource} has not been found.")
    }

}
