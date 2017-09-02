package it.ldsoftware.rinascimento.view.template

import it.ldsoftware.primavera.presentation.base.BaseDTO

/**
 * This class represents a template building block. It contains a reference to an extension script
 * and will be used to dynamically load it at page compilation time. It can also contain extra
 * parameters to further configure the aspect of the block itself.
 *
 * @author Luca Di Stefano
 */
class TemplateWidgetDTO extends BaseDTO {

    String script, params

}
