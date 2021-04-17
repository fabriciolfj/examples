package br.com.coffeeandit.app.business.voos;

import br.com.coffeeandit.app.model.CompaniaAerea;
import br.com.coffeeandit.app.model.LocationTravel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString(of = "siglaVoo")
public abstract class Voo {

    private static final String BRL = "BRL";

    protected Voo() {

    }

    private String id;

    public String getId() {
        if (Objects.isNull(id)) {
            id = getCompaniaAerea().getIata() + UUID.randomUUID().toString();
        }
        return id;
    }

    private LocationTravel origem;
    private LocationTravel destino;
    private CompaniaAerea companiaAerea;
    private String siglaVoo;

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }

    private LocalDateTime dataSaida;

    public abstract int capacidadeMaxima();

    public abstract void setValorBruto(final BigDecimal preco);

    public abstract String getPreco(final Assento assentoSelecionado);

    public abstract Set<Assento> getAssentos();


    public abstract LocalTime getTempoVoo();

    public AtomicReference<Optional<Assento>> selecionarAssento(String numero) {
        AtomicReference<Optional<Assento>> assentoSelecionado = new AtomicReference<>(Optional.empty());
        getDisponiveis().stream().forEach(assento -> {
            if (assento.getNumero().equalsIgnoreCase(numero)) {
                assento.ocupado();
                assento.setPreco(getPreco(assento));
                getCompaniaAerea().selecionarAssento(getSiglaVoo(), numero);
                assentoSelecionado.set(Optional.of(assento));

            }
        });
        return assentoSelecionado;
    }

    public AtomicReference<Optional<Assento>> liberarAssento(String numero) {
        AtomicReference<Optional<Assento>> assentoSelecionado = new AtomicReference<>(Optional.empty());
        getAssentos().stream().forEach(assento -> {
            if (assento.getNumero().equalsIgnoreCase(numero)) {
                assento.liberar();
                assento.setPreco(getPreco(assento));
                assentoSelecionado.set(Optional.of(assento));

            }
        });
        return assentoSelecionado;
    }

    public boolean isLotado() {
        return getAssentos().stream()
                .filter(assento ->
                        !assento.isOcupado())
                .count() == 0;
    }


    public Set<Assento> getDisponiveis() {
        return getAssentos().stream().filter(assento -> !assento.isOcupado()).collect(Collectors.toSet());
    }

}
