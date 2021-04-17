package br.com.coffeeandit.app.business;

import br.com.coffeeandit.app.cache.LocationCache;
import br.com.coffeeandit.app.model.Airport;
import br.com.coffeeandit.app.model.Country;
import br.com.coffeeandit.app.model.LocationTravel;
import br.com.coffeeandit.app.model.Region;
import br.com.coffeeandit.app.transport.GeoLocalizations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LocationBusiness {


    public static final String CIDADE = "<CIDADE>";
    @Value("${locations.url}")
    private String locationsApiURL;
    private RestTemplate restTemplate = new RestTemplate();
    private AirportBusiness airportBusiness;
    private LocationCache locationCache;


    public LocationBusiness(final AirportBusiness airportBusiness, final LocationCache locationCache) {
        this.airportBusiness = airportBusiness;
        this.locationCache = locationCache;
    }

    private Function<br.com.coffeeandit.app.transport.Location, LocationTravel> transform = location -> {

        var country = Country.builder().code(location.getCountryCode())
                .name(location.getCountry()).build();
        var region = Region.builder().code(location.getRegion()).name(location.getRegionCode())
                .country(country)
                .build();

        var destination = LocationTravel.builder().id(location.getId())
                .name(location.getName())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .region(region)
                .build();

        return destination;
    };

    public Optional<List<LocationTravel>> findLocation(final String destinationName) {
        Optional<LocationTravel> destinationCacheByName = locationCache.findByName(destinationName);
        if (destinationCacheByName.isEmpty()) {
            return searchLocations(destinationName);
        }

        return Optional.of(List.of(destinationCacheByName.get()));


    }

    private Optional<List<LocationTravel>> searchLocations(final String destinationName) {
        var urlLocations = locationsApiURL.replace(CIDADE, destinationName);
        var locationsBody = restTemplate.getForObject(urlLocations, GeoLocalizations.class);
        if (Objects.nonNull(locationsBody.getData())) {

            return Optional.of(locationsBody.getData().parallelStream()
                    .filter(location -> Objects.nonNull(location.getId()))
                    .map(transform)
                    .map(destination -> {
                        LocationTravel infoLocationTravel = null;
                        try {
                            infoLocationTravel = infoDestination(destination);
                            return infoLocationTravel;
                        } finally {
                            var infoDestinationRunnable = infoLocationTravel;
                            CompletableFuture.runAsync(() -> locationCache.put(infoDestinationRunnable));

                        }
                    })
                    .sorted(Comparator.comparing(LocationTravel::getName))
                    .collect(Collectors.toList()));

        }


        return Optional.empty();

    }

    private LocationTravel infoDestination(final LocationTravel locationTravel) {
        Optional<List<Airport>> airports = airportBusiness.searchAirports(locationTravel);
        if (airports.isPresent()) {
            locationTravel.setAirports(
                    airports.orElse(new ArrayList<>()));
        }
        return locationTravel;
    }


}
