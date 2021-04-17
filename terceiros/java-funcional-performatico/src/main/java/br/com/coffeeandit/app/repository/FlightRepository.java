package br.com.coffeeandit.app.repository;

import br.com.coffeeandit.app.entity.Flight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends CrudRepository<Flight, String> {


   Optional<List<Flight>> findByOriginAndDestinationAndDepartureTime(final String origin, final String destination, final LocalDateTime departureTime);
}
