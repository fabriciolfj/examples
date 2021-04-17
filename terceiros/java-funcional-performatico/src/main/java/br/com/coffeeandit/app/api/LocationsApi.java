package br.com.coffeeandit.app.api;

import br.com.coffeeandit.app.business.LocationBusiness;
import br.com.coffeeandit.app.model.LocationTravel;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class LocationsApi {

    private LocationBusiness locationBusiness;

    public LocationsApi(final LocationBusiness locationBusiness) {
        this.locationBusiness = locationBusiness;
    }

    @GetMapping(path = "/locations/{name}")
    @Tag(description = "Api de Localização de Destinos", name = "findLocations")
    @Parameter(name = "name", description = "Nome da Localização Exemplo: Porto Alegre")
    @ApiResponse(description = "Destino da Localização com Aeroportos e Geolocalização", responseCode = "200")
    public ResponseEntity<List<LocationTravel>> findLocations(@PathVariable(name = "name") final String location) {
        Optional<List<LocationTravel>> destinations = locationBusiness.findLocation(location);
        if (destinations.isPresent()) {
            return ResponseEntity.ok(destinations.get());
        }
        return ResponseEntity.notFound().build();
    }
}
