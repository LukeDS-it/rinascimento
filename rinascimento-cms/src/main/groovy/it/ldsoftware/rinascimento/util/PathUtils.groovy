package it.ldsoftware.rinascimento.util

import it.ldsoftware.rinascimento.view.template.TemplateDTO

abstract class PathUtils {

    static String resourcePath(String resource, TemplateDTO template) {
        resource =~ /^https?:\/\// ? resource : "resources/templates/${template.name}/${resource}"
    }

    static String widgetResourcePath(String resource) {
        resource =~ /^https?:\/\// ? resource : "resources/widgets/${resource}"
    }

}
