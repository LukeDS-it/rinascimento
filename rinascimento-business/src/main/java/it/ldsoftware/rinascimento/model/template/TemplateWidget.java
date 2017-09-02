package it.ldsoftware.rinascimento.model.template;

import it.ldsoftware.primavera.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter @Setter
@Table(name = "cms_template_widget")
public class TemplateWidget extends BaseEntity {

    @Column(name = "block_order")
    private int order;

    private String script;

    private String params;

    @ManyToOne
    private TemplateColumn column;

}
