package it.ldsoftware.rinascimento.controller

import groovy.util.logging.Slf4j
import it.ldsoftware.primavera.i18n.LocalizationService
import it.ldsoftware.primavera.services.interfaces.PropertyService
import it.ldsoftware.rinascimento.exception.PageNotFoundException
import it.ldsoftware.rinascimento.exception.TenantNotConfiguredException
import it.ldsoftware.rinascimento.exception.WebResourceNotFoundException
import it.ldsoftware.rinascimento.service.TemplateService
import it.ldsoftware.rinascimento.util.PageMode
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.transaction.CannotCreateTransactionException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

import javax.servlet.http.HttpServletRequest

import static it.ldsoftware.rinascimento.util.Constants.ConfKeys.getKEY_404_TEMPLATE
import static it.ldsoftware.rinascimento.util.Constants.ConfKeys.getKEY_500_TEMPLATE
import static it.ldsoftware.rinascimento.util.Constants.Messages.*

@Slf4j
@ControllerAdvice
class ErrorController extends AbstractPageController {

    @Autowired
    private MessageSource messageSource

    @Autowired
    private TemplateService templateService

    @Autowired
    private PropertyService propertyService

    @ExceptionHandler(WebResourceNotFoundException.class)
    void resourceNotFound(WebResourceNotFoundException e) {
        log.info "Could not find resource at path ${e.resource}"
    }

    @ResponseBody
    @ExceptionHandler(CannotCreateTransactionException)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "The tenant has not been configured")
    String tenantNotConfigured(CannotCreateTransactionException e, Locale locale, HttpServletRequest request) {
        if (e.cause instanceof TenantNotConfiguredException) {
            log.error "The tenant for ${e.cause.tenant} has not been configured. Redirecting to configuration."
            return "redirect:/cms-install"
        }
        return buildErrorPage("Unknown error", e.message, KEY_500_TEMPLATE, locale, request)
    }

    @ResponseBody
    @ExceptionHandler(PageNotFoundException.class)
    String pageNotFound(HttpServletRequest request, Locale locale) {
        LocalizationService ls = new LocalizationService(messageSource, locale)

        buildErrorPage ls.translate(TITLE_NOT_FOUND), ls.translate(CONTENT_NOT_FOUND), KEY_404_TEMPLATE, locale, request
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String catchFinal(HttpServletRequest request, Locale locale, Exception e) {
        LocalizationService ls = new LocalizationService(messageSource, locale)

        log.error "Unexpected error while browsing page ${request.getContextPath()}", e

        buildErrorPage ls.translate(TITLE_SERVER_ERROR), ls.translate(CONTENT_SERVER_ERROR), KEY_500_TEMPLATE, locale, request

    }

    private String buildErrorPage(String title, String content, String template, Locale locale, HttpServletRequest request) {
        Long errorTemplate = propertyService.findByKey(template).getValue() as Long

        WebPageDTO page = new WebPageDTO(
                title: title,
                language: locale.language,
                content: content,
                template: templateService.findOne(errorTemplate)
        )

        buildPage page, locale, request, PageMode.VIEW
    }

}
