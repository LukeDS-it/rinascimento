package it.ldsoftware.rinascimento.controller

import it.ldsoftware.primavera.services.interfaces.UserService
import it.ldsoftware.rinascimento.exception.PageNotFoundException
import it.ldsoftware.rinascimento.service.PageBuilder
import it.ldsoftware.rinascimento.service.WebPageService
import it.ldsoftware.rinascimento.util.PageMode
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.context.SecurityContextHolder

import javax.servlet.http.HttpServletRequest
import javax.transaction.Transactional

class AbstractPageController {

    @Autowired
    PageBuilder pageBuilder
    @Autowired
    WebPageService pageService
    @Autowired
    UserService userService

    String buildPage(WebPageDTO page, Locale locale, HttpServletRequest request, PageMode mode) {
        checkPermissionsOn page
        pageBuilder.buildPage page, locale, request, mode
    }

    WebPageDTO findPage(String address) {
        def page = pageService.findByAddress address
        if (!page)
            throw new PageNotFoundException(address)
        page
    }

    String serveStaticPage(String pageResource) {
        def resource = getClass().getClassLoader().getResource(pageResource)
        if (resource)
            resource.text
        throw new PageNotFoundException("pageResource")
    }

    @Transactional
    void checkPermissionsOn(WebPageDTO page) {
        if (page.groupsAllowed || page.rolesAllowed) {
            def auth = SecurityContextHolder.getContext().getAuthentication()

            if (!auth.isAuthenticated())
                throw new AuthenticationCredentialsNotFoundException("The page ${page.title} cannot be accessed anonymously")

            if (page.groupsAllowed) {
                def user = userService.findByUsername(auth.name)
                if (!user.groups.code.containsAll(page.groupsAllowed.code))
                    throw new AccessDeniedException("The user ${user.name} is not in the right group to access to the page ${page.title}")
            }

            if (page.rolesAllowed) {
                if (!auth.getAuthorities().authority.containsAll(page.rolesAllowed.code))
                    throw new AccessDeniedException("The user ${auth.name} hasn't got sufficient permissions to access to the page ${page.title}")
            }
        }
    }

}
