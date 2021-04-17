package br.com.coffeeandit.app.cache;

import br.com.coffeeandit.app.business.voos.Voo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Component
@Log
public class VooRepositoryCache {

    private final Map<String, Voo> cache = new HashMap();
    private ScheduledExecutorService scheduledExecutorService;
    private Path dbFile;
    private ObjectMapper objectMapper = new ObjectMapper();

    public void put(final Voo voo) {
        appendIntoFile(voo);
        cache.putIfAbsent(voo.getSiglaVoo(), voo);

    }

    private synchronized void appendIntoFile(final Voo voo) {


        cache.computeIfAbsent(voo.getSiglaVoo(), key -> {
            try {
                Files.writeString(dbFile, objectMapper.writeValueAsString(voo), StandardOpenOption.CREATE,
                        StandardOpenOption.WRITE,
                        StandardOpenOption.APPEND);
            } catch (IOException ioException) {
                log.severe(ioException.getMessage());
            } finally {
                log.info("Finalizado processo de escrita no arquivo " + dbFile.getFileName().toString());
            }
            return voo;
        });
    }

    public Optional<Voo> get(final String key) {

        return Optional.ofNullable(cache.getOrDefault(key, null));

    }

    @PreDestroy
    public void destroy() throws IOException {
        Files.delete(dbFile);
    }

    public Stream read() throws IOException {

        List lines = new ArrayList<String>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(dbFile, StandardCharsets.UTF_8)) {
            lines.add(bufferedReader.readLine());
        }

        return lines.stream();


    }

    public List<String> readLines() throws IOException {

        return Files.readAllLines(dbFile, StandardCharsets.UTF_8);


    }


    @PostConstruct
    public void schedulerCleanCache() throws IOException {
        this.dbFile = Files.createTempFile("cache", ".tmp");
        if (Objects.isNull(scheduledExecutorService)) {
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

            Runnable runnable = () -> {
                log.info("Limpando a cache");
                try (FileChannel outChan = new FileOutputStream(dbFile.toFile(), false).getChannel()

                ) {
                    outChan.truncate(0);
                } catch (IOException e) {
                    log.severe(e.getMessage());
                }
                cache.clear();
            };
            scheduledExecutorService.schedule(runnable, 86400, TimeUnit.SECONDS);

        }
    }
}
