package it.ldsoftware.rinascimento.view.template

import it.ldsoftware.primavera.presentation.base.BaseDTO

/**
 * A chunk is a piece of a template. Chunks can either be widgets or containers of other chunks.
 * <p>
 *     If a chunk contains a widget <code>(widget != null && widget != "")</code> then just the
 *     widget is rendered with the associated parameters.
 * </p>
 * <p>
 *     In the other case, the chunk is treated as a container and will be rendered as an outer tag
 *     with "type" being its type and "cssClass" its associated class.
 *     All of its children chunks will be rendered recursively inside it.
 * </p>
 * <p>
 *     <strong>Note</strong>: When used as a view object, widget is treated as a string containing
 *     the name of the widget script.
 * </p>
 * @author Luca Di Stefano
 */
class ChunkDTO extends BaseDTO implements ChunkHolder {
    def widget, params
    String type = 'div', cssClass
}
