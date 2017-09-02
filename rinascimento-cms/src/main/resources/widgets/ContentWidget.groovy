package widgets

import groovy.xml.MarkupBuilder
import it.ldsoftware.rinascimento.extension.Widget
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import org.springframework.context.ApplicationContext

/**
 * This extension is the one that renders the main content of a web site.
 *
 * The content will go into a <code>&lt;content&gt;</code> tag to help
 * support of WAI-ARIA directives on usability.
 *
 * Configuration JSON is as follows:
 * <code><pre>
 *     {
 *         css: 'css-class-name',
 *         showTitle: bool
 *     }
 * </pre></code>
 */
class ContentWidget extends Widget {

    ContentWidget(ApplicationContext context) {
        super(context)
    }

    @Override
    def buildContent(MarkupBuilder builder, WebPageDTO page, Locale locale, def params) {
        return builder.content(class: params.css, page.content)
    }
}
