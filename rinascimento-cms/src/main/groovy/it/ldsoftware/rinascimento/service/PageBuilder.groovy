package it.ldsoftware.rinascimento.service

import groovy.xml.MarkupBuilder
import it.ldsoftware.rinascimento.util.Constants
import it.ldsoftware.rinascimento.util.ExtensionUtils
import it.ldsoftware.rinascimento.util.PageMode
import it.ldsoftware.rinascimento.view.template.TemplateWidgetDTO
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import static it.ldsoftware.rinascimento.util.PathUtils.resourcePath

@Service
class PageBuilder {

    @Autowired
    private WidgetLoader loader

    String buildPage(WebPageDTO page, Locale locale, PageMode mode) {
        page.init(locale)

        def sb = new StringWriter().append(Constants.HTML_START)
        MarkupBuilder builder = new MarkupBuilder(sb)

        builder.doubleQuotes = true
        builder.expandEmptyElements = false
        builder.omitEmptyAttributes = true
        builder.omitNullAttributes = true

        builder.html(lang: page.language ?: 'en') {
            head builder, page
            body builder, page, locale, mode
        }

        sb
    }

    private static def head(MarkupBuilder builder, WebPageDTO page) {
        builder.head {
            title page.title

            meta charset: 'UTF-8'
            meta 'http-equiv': 'X-UA-COMPATIBLE', content: 'IE=edge'
            meta name: 'viewport', content: 'width=device-width, initial-scale=1, shrink-to-fit=no'
            meta name: 'lang', content: page.language ?: 'en'

            page.meta.each {
                meta name: it.key, content: it.value
            }

            page.template.css?.each {
                link href: resourcePath(it, page.template), rel: 'stylesheet', type: 'text/css'
            }

            page.css?.each {
                link href: resourcePath(it, page.template), rel: 'stylesheet', type: 'text/css'
            }

            // TODO plugin's css
        }
    }

    private def body(MarkupBuilder builder, WebPageDTO page, Locale locale, PageMode mode) {
        builder.body {

            page.template.rows.each { row ->
                div(class: row.cssClass) {
                    row.columns.each { col ->
                        div(class: col.cssClass ,'') {
                            col.widgets.each {
                                evalExtension it, page, locale, builder
                            }
                        }
                    }
                }
            }

            page.template.js?.each {
                script type: 'text/javascript', src: resourcePath(it, page.template), ''
            }

            page.js?.each {
                script type: 'text/javascript', src: resourcePath(it, page.template), ''
            }

            // TODO plugin's js
        }
    }

    private void evalExtension(TemplateWidgetDTO block, WebPageDTO page, Locale locale, MarkupBuilder builder) {
        loader.getExtension(block.script).buildContent(builder, page, locale, ExtensionUtils.jsonToObj(block.params))
    }

}
