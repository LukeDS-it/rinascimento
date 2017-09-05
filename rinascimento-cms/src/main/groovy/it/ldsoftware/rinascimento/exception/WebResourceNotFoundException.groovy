package it.ldsoftware.rinascimento.exception

class WebResourceNotFoundException extends RuntimeException {

    String resource

    WebResourceNotFoundException(String resource) {
        super("The resource at url ${resource} has not been found.")
        this.resource = resource
    }

}
