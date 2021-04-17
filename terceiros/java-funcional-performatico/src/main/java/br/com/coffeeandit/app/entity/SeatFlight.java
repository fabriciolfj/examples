package br.com.coffeeandit.app.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class SeatFlight {
    @Id
    private String id;
    private String number;
    private boolean occupied;
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;
}
