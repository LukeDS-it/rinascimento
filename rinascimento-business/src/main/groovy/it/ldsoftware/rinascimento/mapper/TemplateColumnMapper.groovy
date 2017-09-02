package it.ldsoftware.rinascimento.mapper

import it.ldsoftware.primavera.mapper.base.BaseMapper
import it.ldsoftware.rinascimento.model.template.TemplateColumn
import it.ldsoftware.rinascimento.view.template.TemplateColumnDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TemplateColumnMapper extends BaseMapper<TemplateColumn, TemplateColumnDTO> {

    @Autowired
    private TemplateWidgetMapper widgetMapper

    @Override
    TemplateColumn getModelInstance(TemplateColumnDTO dto) {
        def c = new TemplateColumn(
                cssClass: dto.cssClass,
                widgets: dto.widgets.collect { widgetMapper.getModelInstance(it) }
        )

        c.widgets.eachWithIndex { it, ix ->
            it.setOrder(ix)
            it.setColumn(c)
        }

        c
    }

    @Override
    TemplateColumnDTO getViewInstance(TemplateColumn templateColumn) {
        new TemplateColumnDTO(
                cssClass: templateColumn.cssClass,
                widgets: templateColumn.widgets.collect { widgetMapper.getViewInstance(it) }

        )
    }
}
