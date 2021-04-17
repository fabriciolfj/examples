package br.com.coffeeandit.app.cache;

import br.com.coffeeandit.app.model.LocationTravel;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Log
public class LocationCache {

    private final Map<Integer, LocationTravel> cache = new HashMap();
    private ScheduledExecutorService scheduledExecutorService;

    public void put(final LocationTravel locationTravel) {
        cache.putIfAbsent(locationTravel.getId(), locationTravel);
    }

    public Optional<LocationTravel> get(final Integer key) {

        return Optional.ofNullable(cache.getOrDefault(key, null));

    }

    public Optional<LocationTravel> findByName(final String name) {

        return cache.values().stream().filter(destination -> name.equalsIgnoreCase(destination.getName())).findFirst();


    }

    @PostConstruct
    public void schedulerCleanCache() {
        if (Objects.isNull(scheduledExecutorService)) {
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

            Runnable runnable = () -> {
                log.info("Limpando a cache");
                cache.clear();
            };
            scheduledExecutorService.schedule(runnable, 600, TimeUnit.SECONDS);

        }
    }
}
