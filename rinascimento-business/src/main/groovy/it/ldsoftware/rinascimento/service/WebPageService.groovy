package it.ldsoftware.rinascimento.service

import it.ldsoftware.primavera.services.interfaces.BusinessService
import it.ldsoftware.rinascimento.view.content.WebPageDTO

interface WebPageService extends BusinessService<WebPageDTO> {
    WebPageDTO findByAddress(String address)
}