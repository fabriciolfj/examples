package br.com.coffeeandit.app.api;

import br.com.coffeeandit.app.business.voos.Assento;
import br.com.coffeeandit.app.business.voos.Voo;
import br.com.coffeeandit.app.business.voos.VooService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class VooApi {

    private VooService vooService;

    public VooApi(final VooService vooService) {
        this.vooService = vooService;
    }

    @PostMapping(path = "/voos/")
    public ResponseEntity<Set<Voo>> voosMaisProcurados(@RequestBody VooPesquisa vooPesquisa) {
        Set<Voo> voos = vooService.buscarVoos(vooPesquisa.getOrigem(), vooPesquisa.getDestino(), vooPesquisa.getDataSaida(), vooPesquisa.getDataChegada());
        return ResponseEntity.ok(voos);

    }
    @ApiResponse(description = "Voos mais pesquisados", responseCode = "200")
    @GetMapping(path = "/voos/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> voosMaisProcurados() {

        return ResponseEntity.ok(vooService.voosMaisProcurados());

    }

    @DeleteMapping(path = "/voo/{siglaVoo}/assento/{numeroAssento}/liberar")
    public ResponseEntity<Assento> liberarAssento(@PathVariable(name = "siglaVoo") final String siglaVoo,
                                                  @PathVariable(name = "numeroAssento") final String numeroAssento) {

        Optional<Assento> assento = vooService.liberarAssento(siglaVoo, numeroAssento);
        if (assento.isPresent()) {
            return ResponseEntity.ok(assento.get());
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping(path = "/voo/{siglaVoo}/assento/{numeroAssento}/selecionar")
    public ResponseEntity<Assento> selecionarAssento(@PathVariable(name = "siglaVoo") final String siglaVoo,
                                                     @PathVariable(name = "numeroAssento") final String numeroAssento) {

        Optional<Assento> assento = vooService.selecionarAssento(siglaVoo, numeroAssento);
        if (assento.isPresent()) {
            return ResponseEntity.ok(assento.get());
        }
        return ResponseEntity.notFound().build();

    }
}
