package it.ldsoftware.rinascimento.model.content;

import it.ldsoftware.primavera.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter @Setter
@Table(name = "cms_web_address")
public class WebAddress extends BaseEntity {

    String address;

    @ManyToOne(fetch = FetchType.LAZY)
    private WebPage page;

}
