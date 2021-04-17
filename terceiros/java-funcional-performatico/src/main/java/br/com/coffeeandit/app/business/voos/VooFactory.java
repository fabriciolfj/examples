package br.com.coffeeandit.app.business.voos;

import br.com.coffeeandit.app.model.CompaniaAerea;
import br.com.coffeeandit.app.model.LocationTravel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class VooFactory {
    private static final String BR = "BR";

    public static Voo criarVoo(final LocationTravel origem, final LocationTravel destino, final CompaniaAerea companiaAerea, final String siglaVoo,
                               final LocalDateTime dataSaida
    ) {

        var random = new Random();
        Voo voo = new VooNacional(assentosVooNacional());
        if (!BR.equalsIgnoreCase(destino.getRegion().getCountry().getCode())) {
            voo = new VooInternacional(assentosVooInternacional());
        }
        voo.setOrigem(origem);
        voo.setCompaniaAerea(companiaAerea);
        voo.setDestino(destino);
        voo.setSiglaVoo(siglaVoo);
        voo.setValorBruto(new BigDecimal(random.nextDouble()));
        return voo;
    }

    private static Set<Assento> assentosVooInternacional() {

        return construirAssentos(3, 'i', 8, BigDecimal.valueOf(74));


    }

    private static Set<Assento> assentosVooNacional() {

        return construirAssentos(3, 'f', 5, BigDecimal.valueOf(30));

    }

    private static Set<Assento> construirAssentos(final int fileiras, char poltronaMaxima, final int fileiraPrimeiraClasse, final BigDecimal percentualAdicionalPrimeiraClasse) {
        var assentos = new HashSet<Assento>();
        for (int n = 0; n < fileiras; n++) {
            for (char alphabet = 'a'; alphabet <= poltronaMaxima; alphabet++) {

                var numero = (n + 1) + "" + alphabet;
                assentos.add(new Assento(UUID.randomUUID().toString(),
                        numero,
                        percentualAdicionalPrimeiraClasse, n <= fileiraPrimeiraClasse));

            }
        }
        return assentos;
    }
}
