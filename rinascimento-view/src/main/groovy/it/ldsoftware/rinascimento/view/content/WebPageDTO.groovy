package it.ldsoftware.rinascimento.view.content

import it.ldsoftware.primavera.presentation.lang.TranslatableDTO
import it.ldsoftware.primavera.presentation.security.GroupDTO
import it.ldsoftware.primavera.presentation.security.RoleDTO
import it.ldsoftware.rinascimento.view.template.TemplateDTO

import java.time.LocalDateTime

class WebPageDTO extends TranslatableDTO<WebPageTranslationDTO> {

    String mnemonicTitle, title, language, content, description, address, preview

    LocalDateTime startValidity, endValidity, publicationDate, lastEdit;

    TemplateDTO template

    Set<GroupDTO> groupsAllowed
    Set<RoleDTO> rolesAllowed

    Set<String> css = [], js = [], wCss = [], wJs = []

    Map<String, String> meta = new HashMap<>()

    void init(Locale locale) {
        if (translations) {
            def translation = translations.get locale.language
            if (!translation)
                translation = translations.get defaultLang

            title = translation.title
            description = translation.description
            language = translation.language
            content = translation.content
        }
    }

}
