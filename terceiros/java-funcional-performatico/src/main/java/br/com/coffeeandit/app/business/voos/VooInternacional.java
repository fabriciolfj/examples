package br.com.coffeeandit.app.business.voos;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class VooInternacional extends Voo {

    private final Set<Assento> assentos;


    protected VooInternacional(Set<Assento> assentos) {
        this.assentos = assentos;
    }

    @Override
    public int capacidadeMaxima() {
        return assentos.size();
    }

    private CurrencyUnit usd = Monetary.getCurrency("USD");
    private Money money = Money.of(0, usd);


    @Override
    public void setValorBruto(BigDecimal preco) {
        money = Money.of(preco, usd);
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
            return money.add(Money.of(resultado, usd)).toString();
        }
        return money.toString();
    }

    @Override
    public Set<Assento> getAssentos() {
        return assentos;
    }


    @Override
    public LocalTime getTempoVoo() {

        int i = ThreadLocalRandom.current().nextInt(12, 24);
        return LocalTime.of(i, ThreadLocalRandom.current().nextInt(1, 59 + 1));


    }
}
