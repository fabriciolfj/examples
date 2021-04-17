package br.com.coffeeandit.app.business.voos;

import br.com.coffeeandit.app.model.CompaniaAerea;
import br.com.coffeeandit.app.model.LocationTravel;
//import br.com.coffeeandit.module.ReservarAssentoGol;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;

public class GolLinhasAereas implements CompaniaAerea {

    private static final String BR = "BR";
    Set<String> airports = Set.of("EZE", "ROS", "MIA", "VVI", "MVD", "MDZ", "SCL", "CUN", "MCO");

//    private ReservarAssentoGol reservarAssentoGol = new ReservarAssentoGol();


    @Override
    public String getName() {
        return "Gol Linhas Aéreas Inteligentes";
    }

    @Override
    public String getIata() {
        return "G3";
    }

    @Override
    public boolean temAtendimento(final LocationTravel origem, final LocationTravel destino) {
        if (BR.equalsIgnoreCase(origem.getRegion().getCountry().getCode())
                && BR.equalsIgnoreCase(destino.getRegion().getCountry().getCode())) {
            return true;
        } else if (BR.equalsIgnoreCase(origem.getRegion().getCountry().getCode())) {
            if (destino.getAirports().stream().anyMatch(airport -> airports.contains(airport.getIata()))) {
                return true;

            }
        }
        return false;
    }

    @Override
    public Set<Voo> voos(final LocationTravel origem, final LocationTravel destino, final LocalDateTime ida, final LocalDateTime volta) {

        var random = new Random(1000);

        Voo vooIda = VooFactory.criarVoo(origem, destino, this, getIata() + random.nextInt(), ida);
        Voo vooVolta = VooFactory.criarVoo(origem, destino, this, getIata() + random.nextInt(), ida);

        return Set.of(vooIda, vooVolta);
    }

    @Override
    public void selecionarAssento(String siglaVoo, String numeroAssento) {
//        reservarAssentoGol.reservarAssento(numeroAssento, siglaVoo);
    }
}
