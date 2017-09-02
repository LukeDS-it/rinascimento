package it.ldsoftware.rinascimento.view.template

import it.ldsoftware.primavera.presentation.base.BaseDTO

/**
 * This class represents a row of the template. It can have a list of {@link TemplateColumnDTO}
 * and may be stylised using a custom css class.
 * <p>
 *     A practical example, using the bootstrap theme, might be the following to represent
 *     a row with three columns, two lateral small columns and a bigger central column.
 *     <code><pre>
 *         new TemplateRowDTO(cssClass: 'row', columns: [
 *             new TemplateColumnDTO(cssClass: 'col-md-3'),
 *             new TemplateColumnDTO(cssClass: 'col-md-6'),
 *             new TemplateColumnDTO(cssClass: 'col-md-3')
 *         ])
 *     </pre></code>
 * </p>
 *
 * @author Luca Di Stefano
 */
class TemplateRowDTO extends BaseDTO {

    String cssClass
    List<TemplateColumnDTO> columns = new ArrayList<>()

    TemplateColumnDTO getAt(int i) {
        columns[i]
    }

}
