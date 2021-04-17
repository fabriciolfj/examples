package br.com.coffeeandit.app.api;

import br.com.coffeeandit.app.business.TravelBusiness;
import br.com.coffeeandit.app.entity.Travel;
import br.com.coffeeandit.app.transport.TravelDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TravelApi {

    private TravelBusiness travelBusiness;

    @Autowired
    public TravelApi(TravelBusiness travelBusiness) {
        this.travelBusiness = travelBusiness;
    }

    @GetMapping(path = "/travels/{id}")
    @Tag(description = "Api responsável pelos pacotes de viagens", name = "findTravels")
    @ApiResponse(description = "Viagens", responseCode = "200")
    public ResponseEntity<Travel> findTravels(@PathVariable(name = "id") Long id) {
        Optional<Travel> availableTravel = travelBusiness.findById(id);
        if (availableTravel.isPresent()) {
            return ResponseEntity.ok(availableTravel.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/travels/")
    @Tag(description = "Api responsável pelos pacotes de viagens", name = "findTravels")
    @ApiResponse(description = "Viagens", responseCode = "200")
    public ResponseEntity<Long> save(@RequestBody TravelDTO dto) {
        Long id = travelBusiness.edit(dto).getId();
        travelBusiness.processTravelNotifications(id);
        return ResponseEntity.ok(id);
    }
}
