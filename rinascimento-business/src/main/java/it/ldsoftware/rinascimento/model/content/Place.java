package it.ldsoftware.rinascimento.model.content;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Getter @Setter
public class Place {

    private String name;
    private BigDecimal latitude, longitude;

}
