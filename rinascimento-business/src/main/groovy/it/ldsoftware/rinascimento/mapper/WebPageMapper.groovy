package it.ldsoftware.rinascimento.mapper

import it.ldsoftware.primavera.mapper.base.BaseMapper
import it.ldsoftware.rinascimento.model.content.WebPage
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import org.springframework.stereotype.Component

@Component
class WebPageMapper extends BaseMapper<WebPage, WebPageDTO> {
    @Override
    WebPage getModelInstance(WebPageDTO dto) {
        new WebPage()
    }

    @Override
    WebPageDTO getViewInstance(WebPage entity) {
        new WebPageDTO(
                mnemonicTitle: entity.title,
                preview: entity.preview,
                publicationDate: entity.publicationDate,
                startValidity: entity.startValidity,
                endValidity: entity.endValidity,
                lastEdit: entity.lastEdit,
                address: entity.addresses.sort { it.id }.first()
        )
    }
}
