package br.com.coffeeandit.app.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long idFlight;
    private Integer idCar;
    private String hotelName;

    @Column(name = "travel_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime travelDateTime;

}