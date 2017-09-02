package it.ldsoftware.rinascimento.model.template;

import it.ldsoftware.primavera.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "cms_template_resource")
public class TemplateResource extends BaseEntity {

    @Column(name = "res_order")
    private int order;

    private String url;

    @ManyToOne
    private Template template;

    @Enumerated(EnumType.STRING)
    private ResourceType type;

}
