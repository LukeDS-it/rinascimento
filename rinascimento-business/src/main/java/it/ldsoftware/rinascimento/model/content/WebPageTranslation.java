package it.ldsoftware.rinascimento.model.content;

import it.ldsoftware.primavera.model.lang.Translation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.persistence.Table;

@Embeddable
@Getter @Setter
@Table(name = "cms_page_translation")
public class WebPageTranslation extends Translation {

    private String title;

    private String description;

    @Lob
    private String content;
}
