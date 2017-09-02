package it.ldsoftware.rinascimento.model.content;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.time.LocalDateTime;

@Embeddable
@Getter @Setter
public class EventData {

    private LocalDateTime eventStart;

    private LocalDateTime eventEnd;

    private String ticketLink;

    @Embedded
    private Place place;

}
