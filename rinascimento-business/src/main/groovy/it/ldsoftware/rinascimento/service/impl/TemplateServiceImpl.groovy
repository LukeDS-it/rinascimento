package it.ldsoftware.rinascimento.service.impl

import it.ldsoftware.primavera.dal.base.BaseDAL
import it.ldsoftware.primavera.mapper.Mapper
import it.ldsoftware.primavera.services.AbstractBusinessService
import it.ldsoftware.rinascimento.model.template.Template
import it.ldsoftware.rinascimento.service.TemplateService
import it.ldsoftware.rinascimento.view.template.TemplateDTO
import org.springframework.stereotype.Service

@Service
class TemplateServiceImpl extends AbstractBusinessService<TemplateDTO, Template> implements TemplateService {
    TemplateServiceImpl(BaseDAL<Template> dal, Mapper<Template, TemplateDTO> mapper) {
        super(dal, mapper)
    }
}
