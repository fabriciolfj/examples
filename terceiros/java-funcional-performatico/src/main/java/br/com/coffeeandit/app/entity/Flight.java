package br.com.coffeeandit.app.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Flight {
    @Id
    private String id;
    private String origin;
    private String destination;
    private String iata;
    private LocalDateTime departureTime;
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SeatFlight> seats;

}
