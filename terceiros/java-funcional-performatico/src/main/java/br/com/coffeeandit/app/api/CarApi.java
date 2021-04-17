package br.com.coffeeandit.app.api;

import br.com.coffeeandit.app.business.CarBusiness;
import br.com.coffeeandit.app.model.Car;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CarApi {

    private CarBusiness carBusiness;

    @Autowired
    public CarApi(CarBusiness carBusiness) {
        this.carBusiness = carBusiness;
    }

    @GetMapping(path = "/cars/{brand}")
    @Tag(description = "Api de Localização de Carros", name = "findCars")
    @Parameter(name = "brand", description = "Nome da Marca Exemplo: Ford")
    @ApiResponse(description = "Carros com os modelos e anos da Marca", responseCode = "200")
    public ResponseEntity<List<Car>> findCars(@PathVariable(name = "brand") String brand) {
        Optional<List<Car>> availableCars = carBusiness.findCars(brand);
        if (availableCars.isPresent()) {
            return ResponseEntity.ok(availableCars.get());
        }
        return ResponseEntity.notFound().build();
    }

}
