package it.ldsoftware.rinascimento.service

import it.ldsoftware.primavera.services.interfaces.BusinessService
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import org.springframework.data.domain.Page

interface WebPageService extends BusinessService<WebPageDTO> {
    /**
     * Finds a web page starting from its address
     */
    WebPageDTO findByAddress(String address)

    /**
     * Finds a set of pages starting from the parent, limiting the result
     */
    Page<WebPageDTO> findChildren(long parent, int page, int size)

    /**
     * Finds the latest published articles
     */
    Page<WebPageDTO> findLatest(int page, int size)
}