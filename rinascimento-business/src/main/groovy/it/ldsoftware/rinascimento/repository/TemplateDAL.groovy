package it.ldsoftware.rinascimento.repository

import it.ldsoftware.primavera.dal.base.BaseDAL
import it.ldsoftware.rinascimento.model.template.Template
import org.springframework.stereotype.Repository

@Repository
interface TemplateDAL extends BaseDAL<Template> {
}
