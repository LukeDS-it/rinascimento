package it.ldsoftware.rinascimento.view.content

import it.ldsoftware.primavera.presentation.lang.TranslatableDTO
import it.ldsoftware.primavera.presentation.security.GroupDTO
import it.ldsoftware.primavera.presentation.security.RoleDTO
import it.ldsoftware.rinascimento.view.template.TemplateDTO

class WebPageDTO extends TranslatableDTO<WebPageTranslationDTO> {

    String title, language, content

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
            language = translation.language
            content = translation.content
        }
    }

}
