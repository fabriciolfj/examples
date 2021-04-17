package br.com.coffeeandit.app.business;

import br.com.coffeeandit.app.enums.CarTypeEnum;
import br.com.coffeeandit.app.model.*;
import br.com.coffeeandit.app.transport.CarBrand;
import br.com.coffeeandit.app.transport.CarModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Year;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log
@Component
public class CarBusiness {

    public static final Logger LOGGER = LoggerFactory.getLogger(CarBusiness.class);
    public static final String FIPE_CODE = "code";

    @Value("${car.url.brand}")
    public String brandUrl;

    @Value("${car.url.vehicles}")
    public String vehiclesUrl;

    private HttpClient client = HttpClient.newHttpClient();
    private ObjectMapper objectMapper = new ObjectMapper();
    private RestTemplate restTemplate = new RestTemplate();

    public Optional<List<Car>> findCars(String brand) {
        HttpRequest request = buildHttpRequest(brandUrl);
        try {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return objectMapper.readValue(
                            client.send(request, HttpResponse.BodyHandlers.ofString()).body(), CarBrand[].class);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }).thenApply(name -> {
                CarBrand selectedBrand = Arrays.asList(name).stream()
                        .filter(t -> t.getFipeName().equals(brand))
                        .findFirst().get();

                return findByBrand(selectedBrand.getId());
            }).get();
        } catch (Exception e) {
            log.info("Erro ao pesquisar marcas");
        }
        return Optional.empty();
    }

    private Optional<List<Car>> findByBrand(Integer code) {
        vehiclesUrl = vehiclesUrl.replace(FIPE_CODE, code.toString());
        List<CarModel> models = Arrays.asList(restTemplate.getForObject(vehiclesUrl, CarModel[].class));
        if (!CollectionUtils.isEmpty(models)) {
            return Optional.of(models.parallelStream()
                    .filter(car -> Objects.nonNull(car.getId()))
                    .map(transform)
                    .sorted(Comparator.comparing(Car::getName))
                    .collect(Collectors.toList()));
        }
        return Optional.empty();
    }

    private Function<CarModel, Car> transform = carModel -> {
        var car = new CarDSL()
                .model(carModel.getMarca())
                .name(carModel.getFipeName())
                .fabricationYear(Year.of(randonInt()))
                .type(CarTypeEnum.UTILITARY)
                .rentValue(rentValue());
        return car;
    };

    private HttpRequest buildHttpRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();
    }

    private int randonInt() {
        Random random = new Random();
        return random.ints(2000, 2020)
                .findFirst()
                .getAsInt();
    }

    private MonetaryAmount rentValue() {
        CurrencyUnit real = Monetary.getCurrency("BRL");
        return Money.of(randonInt(), real);
    }
}
