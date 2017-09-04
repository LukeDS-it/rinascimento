package it.ldsoftware.rinascimento.service

import groovy.xml.MarkupBuilder
import it.ldsoftware.rinascimento.extension.Widget
import it.ldsoftware.rinascimento.multitenancy.MultiTenancyUtils
import it.ldsoftware.rinascimento.util.ExtensionUtils
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service

import javax.servlet.http.HttpServletRequest

@Service
class WidgetLoader {

    @Autowired private ApplicationContext context
    @Autowired private MultiTenancyUtils tenancy

    private GroovyClassLoader loader = new GroovyClassLoader()

    Widget getExtension(String scriptName) {
        Widget widget = (Widget) loader.parseClass(getScript(scriptName)).newInstance()
        widget.context = context
        widget
    }

    Widget getExtension(String scriptName, WebPageDTO page, Locale locale, HttpServletRequest request, MarkupBuilder builder, String params) {
        def widget = getExtension scriptName
        widget.locale = locale
        widget.params = ExtensionUtils.jsonToObj params
        widget.builder = builder
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
