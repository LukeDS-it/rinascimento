package widgets

import it.ldsoftware.rinascimento.extension.Widget

/**
 * This extension is the one that renders the main content of a web site.
 *
 * The content will go into a <code>&lt;content&gt;</code> tag to help
 * support of WAI-ARIA directives on usability.
 *
 * Configuration parameters are:
 * <ul>
 *     <li>css: custom css class name</li>
 *     <li>showTitle: boolean, indicates if page title must be rendered</li>
 * </ul>
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
        return [content  : page.content,
                css      : params.css,
                showTitle: params.showTitle,
                title    : page.title]
    }
}
