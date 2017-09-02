package it.ldsoftware.rinascimento.model.template;

import it.ldsoftware.primavera.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "cms_template_row")
public class TemplateRow extends BaseEntity {

    @Column(name = "row_order")
    private int order;

    private String cssClass;

    @ManyToOne
    private Template template;

    @OrderBy("order")
    @OneToMany(mappedBy = "row", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TemplateColumn> columns;

}