package it.ldsoftware.rinascimento.service

import groovy.xml.MarkupBuilder
import it.ldsoftware.rinascimento.extension.Widget
import it.ldsoftware.rinascimento.util.Constants
import it.ldsoftware.rinascimento.util.PageMode
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.servlet.http.HttpServletRequest

import static it.ldsoftware.rinascimento.util.PathUtils.*

@Service
class PageBuilder {

    @Autowired
    private WidgetLoader loader

    String buildPage(WebPageDTO page, Locale locale, HttpServletRequest request, PageMode mode) {
        page.init(locale)

        def sb = new StringWriter().append(Constants.HTML_START)
        MarkupBuilder builder = getBuilder(sb)

        loadExtensions page, locale, request, builder

        builder.html(lang: page.language ?: 'en') {
            head builder, page
            body builder, page
        }

        sb
    }

    private static MarkupBuilder getBuilder(StringWriter sb) {
        MarkupBuilder builder = new MarkupBuilder(sb)

        builder.doubleQuotes = true
        builder.expandEmptyElements = false
        builder.omitEmptyAttributes = true
        builder.omitNullAttributes = true
        builder
    }

    private static head(MarkupBuilder builder, WebPageDTO page) {
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

            page.wCss?.each {
                link href: widgetResourcePath(it), rel: 'stylesheet', type: 'text/css'
            }
        }
    }

    private static body(MarkupBuilder builder, WebPageDTO page) {
        builder.body {

            page.template.rows.each { row ->
                div(class: row.cssClass) {
                    row.columns.each { col ->
                        div(class: col.cssClass, '') {
                            col.widgets.each {
                                it.extension.buildContent()
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

            page.wJs?.each {
                script type: 'text/javascript', src: widgetResourcePath(it), ''
            }
        }
    }

    private void loadExtensions(WebPageDTO page, Locale locale, HttpServletRequest request, MarkupBuilder builder) {
        page.template.rows.each {
            it.columns.each {
                it.widgets.each {
                    Widget extension = loader.getExtension(it.script, page, locale, request, builder, it.params)
                    page.wCss += extension.getCss()
                    page.wJs += extension.getJs()
                    it.extension = extension
                }
            }
        }
    }

}
