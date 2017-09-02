package it.ldsoftware.rinascimento.view.template

import it.ldsoftware.primavera.presentation.base.BaseDTO

/**
 * This class represents a column of the template. It can be stylised with a css class
 * and can contain a list of {@link TemplateWidgetDTO}s. The widgets will be configured for
 * the template only, so the template will have a fixed aspect for all pages that are
 * using it (unless, of course, an extension provides a way to dynamically change its
 * contents, which is absolutely possible to give a greater customisation chances).
 *
 * @author Luca Di Stefano
 */
class TemplateColumnDTO extends BaseDTO {
    String cssClass
    List<TemplateWidgetDTO> widgets = new ArrayList<>()

    TemplateWidgetDTO getAt(int i) {
        widgets[i]
    }
}
