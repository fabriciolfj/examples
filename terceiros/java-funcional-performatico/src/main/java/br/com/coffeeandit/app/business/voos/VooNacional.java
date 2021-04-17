package br.com.coffeeandit.app.business.voos;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class VooNacional extends Voo {

    private final Set<Assento> assentos;


    protected VooNacional(Set<Assento> assentos) {
        this.assentos = assentos;

    }

    private CurrencyUnit brl = Monetary.getCurrency("BRL");
    private Money money = Money.of(0, brl);


    @Override
    public int capacidadeMaxima() {
        return assentos.size();
    }

    @Override
    public void setValorBruto(BigDecimal preco) {
        money = Money.of(preco, brl);
        if (Objects.nonNull(assentos)) {
            assentos.forEach(assento -> {
                assento.setPreco(getPreco(assento));
            });
        }
    }

    @Override
    public String getPreco(final Assento assentoSelecionado) {
        if (Objects.nonNull(assentoSelecionado)) {
            double resultado = money.getNumber().doubleValueExact()
                    * (assentoSelecionado.getPercentualAdicional().doubleValue() / 100d);
            return money.add(Money.of(resultado, brl)).toString();
        }
        return money.toString();
    }


    @Override
    public Set<Assento> getAssentos() {
        return assentos;
    }

    @Override
    public LocalTime getTempoVoo() {
        int i = ThreadLocalRandom.current().nextInt(0, 24);
        return LocalTime.of(i, ThreadLocalRandom.current().nextInt(1, 59 + 1));

    }

    @Override
    public boolean isLotado() {
        return assentos.stream()
                .filter(assento ->
                        !assento.isOcupado())
                .count() == 0;
    }


}
