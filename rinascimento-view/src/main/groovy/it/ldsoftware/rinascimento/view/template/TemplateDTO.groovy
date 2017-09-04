package it.ldsoftware.rinascimento.view.template

import it.ldsoftware.primavera.presentation.base.BaseDTO

/**
 * This class represents a page template.
 * <p>
 *     A template must visually represent the skeleton of the page, and it can be divided in
 *     rows and columns, represented by {@link TemplateRowDTO} and {@link TemplateColumnDTO}.
 * </p>
 * <p>
 *     The template also contains references to the css and js that are used to visually style
 *     the various widgets and render the page dynamic. The css and js can both be referred as
 *     absolute paths (e.g. to use a CDN) or relative. In the latter case the CMS will refer
 *     to the resource as having its root path in the current template directory
 *     (e.g. css/main.css will be found under http://mysite.com/resources/templates/css/main.css)
 * </p>
 *
 * @author Luca Di Stefano
 */
class TemplateDTO extends BaseDTO {

    String name, templateVersion, author

    Set<String> css = [], js = []

    List<TemplateRowDTO> rows = new ArrayList<>()

    void close() {
        rows.remove(rows.size() - 1)
    }

    TemplateRowDTO getAt(int i) {
        rows[i]
    }

}
