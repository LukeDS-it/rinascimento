package it.ldsoftware.rinascimento.service

import it.ldsoftware.rinascimento.extension.Widget
import it.ldsoftware.rinascimento.util.PageMode
import it.ldsoftware.rinascimento.util.PathUtils
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import it.ldsoftware.rinascimento.view.template.ChunkDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.thymeleaf.ITemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.context.IContext

import javax.servlet.http.HttpServletRequest

import static it.ldsoftware.rinascimento.util.PathUtils.resourcePath

@Service
class PageBuilder {

    private static final String PAGE_BASE = "static/page-base.html"

    @Autowired
    private WidgetLoader loader

    @Autowired
    private ITemplateEngine templateEngine

    /**
     * Builds the representation of the web page with given locale.
     *
     * @param page the page to be rendered
     * @param locale locale of the current request
     * @param request current request
     * @param mode page view mode
     * @return html representation of the page
     */
    String buildPage(WebPageDTO page, Locale locale, HttpServletRequest request, PageMode mode) {
        initPage(page, locale, request)

        def pageTemplate = getClass().getResource(PAGE_BASE).text
        def pageContext = makePageContext(page, locale, mode)

        templateEngine.process(pageTemplate, pageContext)
    }

    /**
     * Initializes the page.
     *
     * @param page the page
     * @param locale locale of the current session
     * @param request servlet request
     */
    private static void initPage(WebPageDTO page, Locale locale, HttpServletRequest request) {
        page.init(locale)
        page.template.chunks.parallelStream().each {
            initChunk it, page, locale, request
        }
    }

    private IContext makePageContext(WebPageDTO page, Locale locale, PageMode mode) {
        def propMap = [body       : renderChunks(page.template.chunks),
                       langCode   : locale.language,
                       metaTag    : page.meta,
                       pageCss    : page.css.collect { resourcePath(it, page.template) },
                       pageJs     : page.js.collect { resourcePath(it, page.template) },
                       pageMode   : mode.toString(),
                       templateCss: page.template.css.collect { resourcePath(it, page.template) },
                       templateJs : page.template.js.collect { resourcePath(it, page.template) },
                       widgetCss  : page.wCss.collect(PathUtils.&widgetResourcePath),
                       widgetJs   : page.wJs.collect(PathUtils.&widgetResourcePath)]
        new Context(locale, propMap)
    }

    private def initChunk(ChunkDTO chunk, WebPageDTO page, Locale locale, HttpServletRequest request) {
        if (chunk.widget) {
            Widget extension = loader.getExtension(chunk.widget as String, page, locale, request, chunk.params as String)
            page.wCss += extension.getCss()
            page.wJs += extension.getJs()
            chunk.rendered = extension.render()
        } else {
            chunk.chunks.each {
                initChunk(it, page, locale, request)
            }
        }
    }

    private String renderChunks(List<ChunkDTO> chunks) {
        chunks.collect this.&renderChunk
    }

    private String renderChunk(ChunkDTO chunk) {
        if (chunk.widget) {
            chunk.rendered
        } else {
            def sb = new StringBuilder("<")

            sb.append(chunk.type)

            if (chunk.cssClass) {
                sb.append('class="')
                sb.append(chunk.cssClass)
                sb.append('"')
            }
            sb.append(">")

            chunk.chunks.each {
                sb.append(renderChunk(it))
            }

            sb.append("<${chunk.type}>")

            sb.toString()
        }
    }
}
