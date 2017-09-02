package it.ldsoftware.rinascimento.mapper

import it.ldsoftware.primavera.mapper.base.BaseMapper
import it.ldsoftware.rinascimento.model.template.ResourceType
import it.ldsoftware.rinascimento.model.template.Template
import it.ldsoftware.rinascimento.model.template.TemplateResource
import it.ldsoftware.rinascimento.view.template.TemplateDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TemplateMapper extends BaseMapper<Template, TemplateDTO> {

    @Autowired
    private TemplateRowMapper rowMapper

    @Override
    Template getModelInstance(TemplateDTO dto) {
        def t = new Template(
                name: dto.name,
                author: dto.author,
                templateVersion: dto.templateVersion,
                rows: dto.rows.collect { rowMapper.getModelInstance(it) }
        )
        t.setResources(collectResources(dto, t))
        t.rows.eachWithIndex { it, ix ->
            it.setTemplate(t)
            it.setOrder(ix)
        }
        t
    }

    @Override
    TemplateDTO getViewInstance(Template template) {
        new TemplateDTO(
                name: template.name,
                author: template.author,
                templateVersion: template.templateVersion,
                css: getResources(template, ResourceType.CSS),
                js: getResources(template, ResourceType.JS),
                rows: template.rows.collect { rowMapper.getViewInstance(it) }
        )
    }

    static List<String> getResources(Template template, ResourceType type) {
        template.resources.findAll { it.type == type }
                .sort { it.order }
                .collect { it.url }
    }

    static Set<TemplateResource> collectResources(TemplateDTO dto, Template template) {
        def css = dto.css.collect { new TemplateResource(url: it, type: ResourceType.CSS, template: template) }
        def js = dto.js.collect { new TemplateResource(url: it, type: ResourceType.JS, template: template) }

        css.eachWithIndex { res, i ->
            res.setOrder(i)
        }

        js.eachWithIndex { res, i ->
            res.setOrder(i)
        }

        css + js
    }
}
