package it.ldsoftware.rinascimento.mapper

import it.ldsoftware.primavera.mapper.base.BaseMapper
import it.ldsoftware.rinascimento.model.template.TemplateRow
import it.ldsoftware.rinascimento.view.template.TemplateRowDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TemplateRowMapper extends BaseMapper<TemplateRow, TemplateRowDTO> {

    @Autowired
    private TemplateColumnMapper columnMapper

    @Override
    TemplateRow getModelInstance(TemplateRowDTO templateRowDTO) {
        def r = new TemplateRow(
                cssClass: templateRowDTO.cssClass,
                columns: templateRowDTO.columns.collect { columnMapper.getModelInstance(it) }
        )

        r.columns.eachWithIndex { it, ix ->
            it.setOrder(ix)
            it.setRow(r)
        }

        r
    }

    @Override
    TemplateRowDTO getViewInstance(TemplateRow templateRow) {
        new TemplateRowDTO(
                cssClass: templateRow.cssClass,
                columns: templateRow.columns.collect { columnMapper.getViewInstance(it) }
        )
    }
}
