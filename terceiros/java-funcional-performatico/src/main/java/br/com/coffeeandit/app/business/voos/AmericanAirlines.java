package br.com.coffeeandit.app.business.voos;

import br.com.coffeeandit.app.model.CompaniaAerea;
import br.com.coffeeandit.app.model.LocationTravel;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;

public class AmericanAirlines implements CompaniaAerea {

    private static final String BR = "BR";

    @Override
    public String getName() {
        return "American Airlines";
    }

    @Override
    public String getIata() {
        return "AA";
    }

    @Override
    public boolean temAtendimento(final LocationTravel origem, final LocationTravel destino) {
        return BR.equalsIgnoreCase(origem.getRegion().getCountry().getCode())
                && !BR.equalsIgnoreCase(origem.getRegion().getCountry().getCode());
    }

    @Override
    public Set<Voo> voos(final LocationTravel origem, final LocationTravel destino, final LocalDateTime ida,
                         final LocalDateTime volta) {

        var random = new Random(1000);
        Voo vooIda = VooFactory.criarVoo(origem, destino, this, getIata() + random.nextInt(), ida);
        Voo vooVolta = VooFactory.criarVoo(origem, destino, this, getIata() + random.nextInt(), ida);

        return Set.of(vooIda, vooVolta);
    }

    @Override
    public void selecionarAssento(String siglaVoo, String numeroAssento) {

    }
}
