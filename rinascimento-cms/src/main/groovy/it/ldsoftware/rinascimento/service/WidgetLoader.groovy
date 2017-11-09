package it.ldsoftware.rinascimento.service

import it.ldsoftware.rinascimento.extension.Widget
import it.ldsoftware.rinascimento.multitenancy.MultiTenancyUtils
import it.ldsoftware.rinascimento.util.ExtensionUtils
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service
import org.thymeleaf.ITemplateEngine

import javax.servlet.http.HttpServletRequest

@Service
class WidgetLoader {

    @Autowired private MultiTenancyUtils tenancy
    @Autowired private ApplicationContext context
    @Autowired private ITemplateEngine templateEngine

    private GroovyClassLoader loader = new GroovyClassLoader()

    Widget getExtension(String scriptName) {
        Widget widget = (Widget) loader.parseClass(getScript(scriptName)).newInstance()
        widget.context = context
        widget.templateEngine = templateEngine
        widget.multiTenancy = tenancy
        widget
    }

    Widget getExtension(String scriptName, WebPageDTO page, Locale locale, HttpServletRequest request, String params) {
        def widget = getExtension scriptName
        widget.locale = locale
        widget.params = ExtensionUtils.jsonToObj params
        widget.page = page
        widget.request = request
        widget
    }

    private String getScript(String scriptName) {
        File customExt = new File(tenancy.getTenantExtensionDir() + scriptName)
        if (customExt.exists())
            return customExt.text
        getClass().getClassLoader().getResource("widgets/${scriptName}").text
    }

}
