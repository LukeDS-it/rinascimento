package widgets

import it.ldsoftware.rinascimento.extension.Widget
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import org.springframework.data.domain.Page

import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * This extension creates a series of v-cards containing the children of
 * the current web page.
 *
 * Configuration JSON is as follows:
 * <code><pre>
 *  {
 *      mode: 'string',
 *      parent: number,
 *      skip: number,
 *      max: number
 *  }
 * </pre></code>
 *
 * The widget has three modes:
 * <ul>
 *     <li>children: shows the children of the current page. This is the default value if nothing else is specified</li>
 *     <li>latest: shows the latest articles</li>
 *     <li>parent: Shows the children of another page, passed as a parameter</li>
 * </ul>
 *
 * The parameter "skip", if present, defines how many entries must be skipped
 * e.g. if the children are [1, 2, 3, 4], skip: 3 renders just element "4".
 *
 * The parameter "max" limits the results to "max". Defaults to 12
 */
class VCardsWidget extends Widget {

    final def DEFAULT_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)

    @Override
    List<String> checkParameters() {
        def errors = []
        if (!params.parent)
            errors += "Missing parameter: 'parent'."

        errors
    }

    @Override
    void buildActualContent() {
        def mode = params.mode
        def pageSize = params.size ?: 12
        Page<WebPageDTO> pages

        switch (mode) {
            default:
            case "children":
                pages = getPageRepository().findChildren(page.id, 0, pageSize)
                break
            case "latest":
                pages = getPageRepository().findLatest(0, pageSize)
                break
            case "parent":
                pages = getPageRepository().findChildren(params.parent as long, 0, pageSize)
                break
        }

        def content = pages.getContent().drop(params.skip ?: 0).each { it.init(locale) }

        builder.div class: 'cards-container', {
            content.each { page ->
                div class: 'v-card' {
                    a href: page.address, class: 'card-img', {
                        img src: page.imgPreview
                    }
                    div class: 'card-content' {
                        h3 {
                            a href: page.address, page.title
                        }
                        p class: 'date', page.publishedDate.format(DEFAULT_FORMATTER)
                        p class: 'content', page.description.length() > 31 ? (page.description.take(30) + "&hellip;") : page.description
                    }
                }
            }
        }

    }

    @Override
    void buildConfig() {

    }

    @Override
    List<String> getCss() {
        return ["css/vcards.css"]
    }
}
