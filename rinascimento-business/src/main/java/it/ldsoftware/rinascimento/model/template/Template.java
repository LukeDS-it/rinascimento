package it.ldsoftware.rinascimento.model.template;

import it.ldsoftware.primavera.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Entity
@Getter @Setter
@Table(name = "cms_template")
public class Template extends BaseEntity {

    private String name, author, templateVersion;

    @OrderBy("order")
    @OneToMany(mappedBy = "template")
    private Set<TemplateResource> resources;

    @OrderBy("order")
    @OneToMany(mappedBy = "template", fetch = EAGER, cascade = CascadeType.ALL)
    private Set<TemplateRow> rows;

}