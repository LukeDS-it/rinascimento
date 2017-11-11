package it.ldsoftware.rinascimento.extension

import it.ldsoftware.rinascimento.multitenancy.MultiTenancyUtils
import it.ldsoftware.rinascimento.service.WebPageService
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import org.springframework.context.ApplicationContext
import org.thymeleaf.ITemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.context.IContext

import javax.servlet.http.HttpServletRequest

/**
 * A widget class is the basic companion object that is needed to create a widget that will be rendered on a page.
 * <p>
 * The companion object will need to create the map of parameters that the .html file has to render.
 * <p>
 * Within the companion object, you will have access to:
 * <ul>
 *     <li>The configuration parameters of the widget (these are fixed for the current template)</li>
 *     <li>The current page ({@link WebPageDTO}, to access page-specific parameters.</li>
 *     <li>The current request {@link HttpServletRequest}</li>
 *     <li>All the data of the database (See documentation for the getter methods to learn more)</li>
 * </ul>
 *
 * @author Luca Di Stefano
 */
abstract class Widget {

    private static final String ERROR_TEMPLATE = "error-widget.html",
                                FRAGMENT_START = "<!-- START FRAGMENT -->", FRAGMENT_END = "<!-- END FRAGMENT -->"

    private ApplicationContext context
    private ITemplateEngine templateEngine
    private MultiTenancyUtils multiTenancy

    /**
     * Parameters that go in input to the widget.
     * The actual content is a Groovy object created by the {@link groovy.json.JsonSlurper}
     * giving in input the parameters in JSON format.
     */
    def params

    /**
     * Current locale of the browsing session.
     */
    Locale locale

    /**
     * View object of the page that is currently being rendered
     */
    WebPageDTO page

    /**
     * Current request of the browsing session.
     */
    HttpServletRequest request

    final String render() {
        def errors = checkParameters()
        def model
        def template

        if (errors) {
            model = makeErrorModel(errors)
            template = loadTemplate(ERROR_TEMPLATE)
        } else {
            model = getModel()
            template = loadTemplate(getTemplateName())
        }

        def widgetContext = makeWidgetContext(model)
        templateEngine.process(template, widgetContext)
    }

    private static Map<String, Object> makeErrorModel(List<String> errors) {
        ["message": "",
         "errors" : errors]
    }

    private String loadTemplate(String templateName) {
        def template
        def resPath = multiTenancy.getTenantExtensionDir().concat("resources/html/${templateName}")
        def resFile = new File(resPath)

        if (resFile.exists())
            template = resFile.text
        else
            template = getClass().getClassLoader().getResource("widgets/resources/html/${templateName}").text

        template.substring(template.indexOf(FRAGMENT_START) + FRAGMENT_START.length(), template.indexOf(FRAGMENT_END))
    }

    private IContext makeWidgetContext(Map<String, Object> model) {
        new Context(locale, model)
    }

    /**
     * @return The service used to search the web pages stored in the database.
     */
    WebPageService getPageRepository() {
        context.getBean(WebPageService)
    }

    /**
     * All widgets will need to implement the method, that will check if the parameters have been correctly set.
     * If the function returns a non empty list, then instead of rendering the widget, all the errors in the list
     * will be rendered.
     */
    abstract List<String> checkParameters()

    /**
     * This function must be implemented by the companion object. It must return all the values that the .html template
     * expects to find inside the scope in order to correctly render.
     *
     * @return a map of string and objects
     */
    abstract Map<String, Object> getModel()

    /**
     * Represents the name of the template.
     * Convention is the name of the widget in hyphen-case, however, this is not enforced.
     */
    abstract String getTemplateName()

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

    void setContext(ApplicationContext context) {
        this.context = context
    }

    void setTemplateEngine(ITemplateEngine templateEngine) {
        this.templateEngine = templateEngine
    }

    void setMultiTenancy(MultiTenancyUtils multiTenancy) {
        this.multiTenancy = multiTenancy
    }
}
