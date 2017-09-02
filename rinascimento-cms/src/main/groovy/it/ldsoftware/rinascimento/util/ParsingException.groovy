package it.ldsoftware.rinascimento.util

class ParsingException extends RuntimeException {
    ParsingException(String message) {
        super(message)
    }

    ParsingException(String message, Throwable t) {
        super(message, t)
    }
}
