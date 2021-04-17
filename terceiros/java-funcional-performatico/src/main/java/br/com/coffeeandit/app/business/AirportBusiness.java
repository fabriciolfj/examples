package br.com.coffeeandit.app.business;

import br.com.coffeeandit.app.model.Airport;
import br.com.coffeeandit.app.model.DataAirport;
import br.com.coffeeandit.app.model.LocationTravel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@Service
public class AirportBusiness {

    public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    public static final String CONTENT_TYPE = "Content-Type";
    private HttpClient client = HttpClient.newHttpClient();

    @Value("${airport.url}")
    private String transactionURL;
    private ObjectMapper objectMapper = new ObjectMapper();


    public Optional<List<Airport>> searchAirports(final LocationTravel locationTravel) {

        var parameters = new HashMap<String, String>();

        parameters.put("city", locationTravel.getName());
        parameters.put("country", "ALL");

        var form = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        var request = getHttpRequest(form);
        try {

            var dataAirport = Optional.ofNullable(objectMapper.readValue(
                    client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                            .thenApply(HttpResponse::body).get(), DataAirport.class));
            if (dataAirport.isPresent()) {
                return Optional.of(dataAirport.get().getAirports());
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    private HttpRequest getHttpRequest(String form) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(transactionURL))
                .headers(CONTENT_TYPE, APPLICATION_X_WWW_FORM_URLENCODED)
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();
        return request;
    }
}
