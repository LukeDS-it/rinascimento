package widgets

import it.ldsoftware.rinascimento.extension.Widget

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

    @Override
    String getTemplateName() {
        return "content-widget.html"
    }

    @Override
    List<String> checkParameters() { [] }

    @Override
    Map<String, Object> getModel() {
        return [
                showTitle: params.showTitle,
                css: params.css,
                content: page.content
        ]
    }
}
