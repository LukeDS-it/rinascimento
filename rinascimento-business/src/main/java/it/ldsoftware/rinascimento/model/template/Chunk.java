package it.ldsoftware.rinascimento.model.template;

import it.ldsoftware.primavera.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@Table(name = "cms_template_chunk")
public class Chunk extends BaseEntity {

    @Column(name = "chunk_order")
    private int order;

    private String widget, type, cssClass;

    private String params;

    @ManyToOne(fetch = LAZY)
    private Template template;

    @ManyToOne(fetch = LAZY)
    private Chunk chunk;

    @OrderBy("order")
    @OneToMany(mappedBy = "chunk", cascade = ALL, fetch = EAGER)
    private Set<Chunk> chunks = new HashSet<>();
}
