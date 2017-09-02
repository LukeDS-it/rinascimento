package it.ldsoftware.rinascimento.model.template;

import it.ldsoftware.primavera.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "cms_template_column")
public class TemplateColumn extends BaseEntity {

    @Column(name = "col_order")
    private int order;

    private String cssClass;

    @ManyToOne
    private TemplateRow row;

    @OrderBy("order")
    @OneToMany(mappedBy = "column", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TemplateWidget> widgets;
}