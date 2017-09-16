package it.ldsoftware.rinascimento.service

import groovy.xml.MarkupBuilder
import it.ldsoftware.rinascimento.extension.Widget
import it.ldsoftware.rinascimento.util.Constants
import it.ldsoftware.rinascimento.util.PageMode
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import it.ldsoftware.rinascimento.view.template.ChunkDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.servlet.http.HttpServletRequest

import static it.ldsoftware.rinascimento.util.PathUtils.resourcePath
import static it.ldsoftware.rinascimento.util.PathUtils.widgetResourcePath

@Service
class PageBuilder {

    @Autowired
    private WidgetLoader loader

    String buildPage(WebPageDTO page, Locale locale, HttpServletRequest request, PageMode mode) {
        page.init(locale)

        def sb = new StringWriter().append(Constants.HTML_START)
        MarkupBuilder builder = getBuilder(sb)

        page.template.chunks.each {
            initChunk it, page, locale, request, builder
        }

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

            page.template.chunks.each {
                renderChunk it, builder
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

    private def initChunk(ChunkDTO chunk, WebPageDTO page, Locale locale, HttpServletRequest request, MarkupBuilder builder) {
        if (chunk.widget){
            Widget extension = loader.getExtension(chunk.widget as String, page, locale, request, builder, chunk.params as String)
            page.wCss += extension.getCss()
            page.wJs += extension.getJs()
            chunk.widget = extension
        } else {
            chunk.chunks.each {
                initChunk(it, page, locale, request, builder)
            }
        }
    }

    private static renderChunk(ChunkDTO chunk, MarkupBuilder builder) {
        if (chunk.widget) {
            (chunk.widget as Widget).buildContent()
        } else {
            builder."${chunk.type}"(class: chunk.cssClass, '') {
                chunk.chunks.each {
                    renderChunk it, builder
                }
            }
        }
    }

}
