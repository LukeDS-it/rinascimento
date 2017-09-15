package it.ldsoftware.rinascimento.extension

import groovy.xml.MarkupBuilder
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import org.springframework.context.ApplicationContext

import javax.servlet.http.HttpServletRequest
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

    ApplicationContext context

    WebPageDTO page
    HttpServletRequest request
    MarkupBuilder builder
    Locale locale
    def params

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

    Object propertyMissing(String name){
        switch (name) {
            case 'requestParams':
                return request.getParameterMap()
            case 'mkp':
                return builder.mkp
            default:
                return null
        }
    }

    /**
     * All CMS widgets will need to implement this method that will add content to the page.
     */
    abstract void buildContent()

    /**
     * All CMS widgets will need to implement this method that will build the configuration bit for the widget.
     */
    abstract void buildConfig()

    /**
     * Will return a list of additional CSS that are needed to correctly render the widget.
     * CSS may be referred to as relative paths or absolute (http(s)://) paths. Relative paths will be resolved
     * to <code>/resources/widgets/your-resource-path</code>.
     * <p>
     * Duplicate resources due to loading the same widget multiple times or widgets having same resources will
     * automatically be removed. Please see the Widget Creation Guide for more information about paths.
     * </p>
     *
     * @return a list of strings.
     */
    List<String> getCss() {
        return []
    }

    /**
     * Will return a list of additional CSS that are needed to correctly render the widget.
     * CSS may be referred to as relative paths or absolute (http(s)://) paths. Relative paths will be resolved
     * to <code>/resources/widgets/your-resource-path</code>.
     * <p>
     * Duplicate resources due to loading the same widget multiple times or widgets having same resources will
     * automatically be removed. Please see the Widget Creation Guide for more information about paths.
     * </p>
     *
     * @return a list of strings.
     */
    List<String> getJs() {
        return []
    }

    // TODO getAuthor, getName, getDescription, getVersion

}
