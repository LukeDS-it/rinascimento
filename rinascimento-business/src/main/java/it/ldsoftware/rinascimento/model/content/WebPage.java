package it.ldsoftware.rinascimento.model.content;

import it.ldsoftware.primavera.model.lang.Translatable;
import it.ldsoftware.primavera.model.people.User;
import it.ldsoftware.primavera.model.security.Group;
import it.ldsoftware.primavera.model.security.Role;
import it.ldsoftware.rinascimento.model.template.Template;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@Table(name = "cms_page")
@AssociationOverrides(@AssociationOverride(name = "translations", joinTable = @JoinTable(name = "cms_page_translations")))
public class WebPage extends Translatable<WebPageTranslation> {

    private boolean isEvent;

    private String title, preview;

    private LocalDateTime startValidity, endValidity, publicationDate, lastEdit;

    @Enumerated(EnumType.STRING)
    private PageStatus status;

    @Embedded
    private EventData event;

    @ManyToOne
    private User author;

    @ManyToOne
    private Template template;

    @ManyToOne
    private WebPage parent;

    @OneToMany(mappedBy = "page", fetch = LAZY, cascade = ALL)
    private Set<WebAddress> addresses;

    @ManyToMany(fetch = LAZY, cascade = ALL)
    private Set<Tag> tags;

    @ManyToMany(fetch = LAZY, cascade = ALL)
    private Set<Group> groupsAllowed;

    @ManyToMany(fetch = LAZY, cascade = ALL)
    private Set<Role> rolesAllowed;

}
