package br.com.coffeeandit.app.transport;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.time.LocalDateTime;

@Getter
@Setter
@Log
public class TravelDTO {

    private Long id;
    private Long idFlight;
    private Integer idCar;
    //private HotelRecord hotelRecord;
    private LocalDateTime travelDateTime;


    public void printTravelInformation() {
//
//        String travelInformation = """
//                SUA VIAGEM ESTA CONFIRMADA COM AS SEGUINTES INFORMAÇÕES:
//                """;
//
//        travelInformation += """
//                    VOO:""" + idFlight;
//
//        travelInformation += """
//
//                    CARRO: """ + idFlight;
//
//        travelInformation += """
//
//                    HOTEL: """ + hotelRecord.name() + " " + hotelRecord.stars() + " ESTRELAS ";
//
//        log.info(travelInformation);
    }

}