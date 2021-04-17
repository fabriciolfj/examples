package br.com.coffeeandit.app.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VooPesquisa {

    @Schema(name = "origem", description = "Origem do voo.")
    private String origem;
    private String destino;
    private LocalDateTime dataSaida;
    private LocalDateTime dataChegada;
}
