package it.ldsoftware.rinascimento.mapper

import it.ldsoftware.primavera.mapper.base.BaseMapper
import it.ldsoftware.rinascimento.model.content.WebPage
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import org.springframework.stereotype.Component

@Component
class WebPageMapper extends BaseMapper<WebPage, WebPageDTO> {
    @Override
    WebPage getModelInstance(WebPageDTO webPageDTO) {
        return null
    }

    @Override
    WebPageDTO getViewInstance(WebPage webPage) {
        return null
    }
}
