package it.ldsoftware.rinascimento.view.template

import it.ldsoftware.primavera.presentation.base.BaseDTO

/**
 * This class represents a page template.
 * <p>
 *     A template must visually represent the skeleton of the page, and it can be divided in
 *     chunks. Each chunk can either be a widget or a container of more chunks.
 *     See {@link ChunkDTO}'s documentation to understand what a chunk is.
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

    List<ChunkDTO> chunks = []

}
