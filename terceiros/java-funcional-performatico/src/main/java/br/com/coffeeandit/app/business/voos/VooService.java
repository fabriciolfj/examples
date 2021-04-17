package br.com.coffeeandit.app.business.voos;

import br.com.coffeeandit.app.business.LocationBusiness;
import br.com.coffeeandit.app.cache.VooRepositoryCache;
import br.com.coffeeandit.app.entity.Flight;
import br.com.coffeeandit.app.entity.SeatFlight;
import br.com.coffeeandit.app.model.CompaniaAerea;
import br.com.coffeeandit.app.observer.AssentoNotification;
import br.com.coffeeandit.app.repository.FlightRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log
public class VooService {

    private final LocationBusiness locationBusiness;
    private final Set<CompaniaAerea> companiasAereas = new HashSet<>();
    private final AssentoNotification assentoNotification;
    private final FlightRepository flightRepository;
    private VooRepositoryCache vooRepositoryCache;

    public VooService(final AssentoNotification assentoNotification, final LocationBusiness locationBusiness,
                      final FlightRepository flightRepository, final VooRepositoryCache vooRepositoryCache) {
        this.assentoNotification = assentoNotification;
        this.locationBusiness = locationBusiness;
        this.flightRepository = flightRepository;
        this.vooRepositoryCache = vooRepositoryCache;
    }

    @PostConstruct
    public void carregarCompanias() {
        companiasAereas.add(new GolLinhasAereas());
        companiasAereas.add(new AmericanAirlines());
    }


    public Set<Voo> buscarVoos(final String origem, final String destino, final LocalDateTime ida, final LocalDateTime volta) {
        var voos = new HashSet<Voo>();
        var origemViagem = locationBusiness.findLocation(origem).orElseThrow(() -> new IllegalArgumentException("Inválida Origem"));
        var destinoViagem = locationBusiness.findLocation(destino).orElseThrow(() -> new IllegalArgumentException("Inválido Destino"));

        origemViagem.parallelStream().forEach(saida -> {
            if (!saida.getAirports().isEmpty()) {
                destinoViagem.parallelStream().forEach(chegada -> {
                    if (!chegada.getAirports().isEmpty()) {

                        companiasAereas.parallelStream().forEach(companiaAerea -> {
                            if (companiaAerea.temAtendimento(saida, chegada)) {
                                voos.addAll(companiaAerea.voos(saida, chegada, ida, volta).parallelStream()
                                        .peek(this::salvarVoo)
                                        .peek(vooRepositoryCache::put)
                                        .collect(Collectors.toSet())

                                );


                            }
                        });
                    }
                });
            }
        });


        return voos;
    }

    public Optional<Assento> selecionarAssento(final String siglaVoo, final String numeroAssento) {
        Optional<Voo> optionalVoo = vooRepositoryCache.get(siglaVoo);
        if (optionalVoo.isPresent()) {
            var voo = optionalVoo.get();
            var optionalAtomicReference = voo.selecionarAssento(numeroAssento);
            Optional<Assento> assento = optionalAtomicReference.get();
            if (assento.isPresent()) {
                assentoNotification.selecionarAssento(assento.get(), voo);
            }

            return assento;

        }
        return Optional.empty();
    }

    public List<String> voosMaisProcurados() {

        try {
            return vooRepositoryCache.readLines();
        } catch (IOException ioException) {
            log.severe(ioException.getMessage());
        }
        return new ArrayList<>();
    }


    public Optional<Assento> liberarAssento(final String siglaVoo, final String numeroAssento) {
        Optional<Voo> optionalVoo = vooRepositoryCache.get(siglaVoo);
        if (optionalVoo.isPresent()) {
            var voo = optionalVoo.get();
            var optionalAtomicReference = voo.liberarAssento(numeroAssento);
            Optional<Assento> assento = optionalAtomicReference.get();
            if (assento.isPresent()) {
                assentoNotification.liberarAssento(assento.get(), voo);
            }

            return assento;

        }
        return Optional.empty();
    }

    private void salvarVoo(final Voo voo) {
        if (flightRepository.findByOriginAndDestinationAndDepartureTime(voo.getOrigem().getName(),
                voo.getDestino().getName(), voo.getDataSaida()
        ).isEmpty()) {
            flightRepository.save(vooFlightFunction.apply(voo));
        }

    }

    private Function<Voo, Flight> vooFlightFunction = voo -> {
        var flight = new Flight();
        flight.setDestination(voo.getDestino().getName());
        flight.setOrigin(voo.getOrigem().getName());
        flight.setIata(voo.getCompaniaAerea().getIata());
        flight.setId(voo.getId());
        flight.setSeats(
                voo.getAssentos().stream().map(assento -> {
                    var seatFlight = new SeatFlight();
                    seatFlight.setId(assento.getId());
                    seatFlight.setNumber(assento.getNumero());
                    seatFlight.setOccupied(assento.isOcupado());
                    seatFlight.setFlight(flight);
                    return seatFlight;
                }).collect(Collectors.toSet()));
        return flight;
    };
}
