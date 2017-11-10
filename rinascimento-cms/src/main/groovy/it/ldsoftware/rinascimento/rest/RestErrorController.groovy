package it.ldsoftware.rinascimento.rest

import groovy.util.logging.Slf4j
import it.ldsoftware.rinascimento.exception.DirectoryCreationException
import it.ldsoftware.rinascimento.exception.InstallationException
import it.ldsoftware.rinascimento.view.install.InstallationError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@Slf4j
@RestControllerAdvice
class RestErrorController {

    @ExceptionHandler(InstallationException)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    InstallationError handleInstallationException(InstallationException exception) {
        log.error "An unknown exception has happened", exception
        [exception.message, 'unknown']
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    InstallationError handleDirectoryFailure(DirectoryCreationException ex) {
        log.error "Unable to create directories ", ex
        [ex.message, 'Directory']
    }
}
