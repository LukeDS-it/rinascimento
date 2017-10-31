package it.ldsoftware.rinascimento.service.impl

import it.ldsoftware.primavera.dal.base.BaseDAL
import it.ldsoftware.primavera.mapper.Mapper
import it.ldsoftware.primavera.services.AbstractBusinessService
import it.ldsoftware.rinascimento.model.content.QWebAddress
import it.ldsoftware.rinascimento.model.content.WebAddress
import it.ldsoftware.rinascimento.model.content.WebPage
import it.ldsoftware.rinascimento.repository.WebAddressDAL
import it.ldsoftware.rinascimento.service.WebPageService
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

import javax.transaction.Transactional

import static it.ldsoftware.rinascimento.model.content.PageStatus.PUBLISHED
import static it.ldsoftware.rinascimento.model.content.QWebPage.webPage
import static org.springframework.data.domain.Sort.Direction.DESC

@Service
class WebPageServiceImpl extends AbstractBusinessService<WebPageDTO, WebPage> implements WebPageService {

    private WebAddressDAL webAddressDAL

    WebPageServiceImpl(BaseDAL<WebPage> dal, Mapper<WebPage, WebPageDTO> mapper, WebAddressDAL webAddressDAL) {
        super(dal, mapper)
        this.webAddressDAL = webAddressDAL
    }

    @Transactional
    WebPageDTO findByAddress(String address) {
        WebAddress a = webAddressDAL.findOne(QWebAddress.webAddress.address.eq(address))
        mapper.convertToView(a.page)
    }

    @Override
    Page<WebPageDTO> findChildren(long parent, int page, int size) {
        dal.findAll(webPage.parent.id.eq(parent) & webPage.status.eq(PUBLISHED), new PageRequest(page, size))
                .map mapper.&convertToView
    }

    @Override
    Page<WebPageDTO> findLatest(int page, int size) {
        dal.findAll(webPage.status.eq(PUBLISHED), new PageRequest(page, size, DESC, "publicationDate"))
                .map mapper.&convertToView
    }
}
