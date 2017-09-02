package it.ldsoftware.rinascimento.mapper

import it.ldsoftware.primavera.mapper.base.BaseMapper
import it.ldsoftware.rinascimento.model.template.TemplateWidget
import it.ldsoftware.rinascimento.view.template.TemplateWidgetDTO
import org.springframework.stereotype.Component

@Component
class TemplateWidgetMapper extends BaseMapper<TemplateWidget, TemplateWidgetDTO> {
    @Override
    TemplateWidget getModelInstance(TemplateWidgetDTO templateWidgetDTO) {
        new TemplateWidget(
                script: templateWidgetDTO.script,
                params: templateWidgetDTO.params
        )
    }

    @Override
    TemplateWidgetDTO getViewInstance(TemplateWidget templateWidget) {
        new TemplateWidgetDTO(
                script: templateWidget.script,
                params: templateWidget.params
        )
    }
}
