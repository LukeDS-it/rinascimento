package it.ldsoftware.rinascimento.service

import it.ldsoftware.rinascimento.extension.Widget
import it.ldsoftware.rinascimento.multitenancy.MultiTenancyUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service

@Service
class WidgetLoader {

    @Autowired private ApplicationContext context
    @Autowired private MultiTenancyUtils tenancy

    private GroovyClassLoader loader = new GroovyClassLoader()

//    @Cacheable(cacheNames = "extCache") // TODO cache for tenant
    Widget getExtension(String scriptName) {
        return (Widget) loader.parseClass(getScript(scriptName)).newInstance(context)
    }

    private String getScript(String scriptName) {
        File customExt = new File(tenancy.getTenantExtensionDir() + scriptName)
        if (customExt.exists())
            return customExt.text
        getClass().getClassLoader().getResource("widgets/${scriptName}").text
    }

}
