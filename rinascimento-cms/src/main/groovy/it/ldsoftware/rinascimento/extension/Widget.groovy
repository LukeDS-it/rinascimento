package it.ldsoftware.rinascimento.extension

import groovy.xml.MarkupBuilder
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import org.springframework.context.ApplicationContext

import java.util.regex.Matcher

/**
 * This defines the basic extension of the CMS.
 *
 * Within an extension you will have access to the complete environment of
 * the application and will be able to get all the beans by using the
 * <code>get***</code> notation with *** the name of the bean you need.
 * E.g. <code>getUserRepository</code> to get the user repository.
 *
 * @author Luca Di Stefano
 */
abstract class Widget {

    private ApplicationContext context

    Widget(ApplicationContext context) {
        this.context = context
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    Object methodMissing(String name, Object arguments) {
        switch (name) {
            case ~/^getApp(?:lication)?Context$/:
                return context
            case ~/^get(?!All)(.+)$/:
                return context.getBean("${Matcher.lastMatcher[0][1]}".uncapitalize())
            case ~/^getAll(.+)$/:
                return context.getBeansOfType(Class.forName("$Matcher.lastMatcher[0][1]"))
            default:
                return null
        }
    }

    /**
     * All CMS extension will need to implement this method that will use
     * groovy's {@link MarkupBuilder} to add content to the page.
     *
     * @param builder the markup builder to create the content of the page
     * @param page the page object to make the extension aware of the context it's in
     * @param params additional parameters that are extension-specific
     * @return
     */
    abstract buildContent(MarkupBuilder builder, WebPageDTO page, Locale locale, def params)

    // TODO getAuthor, getName, getDescription, getVersion

}
