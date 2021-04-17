package br.com.coffeeandit.app.model;

import br.com.coffeeandit.app.business.voos.Assento;
import br.com.coffeeandit.app.business.voos.Voo;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface CompaniaAerea {

    String getName();

    String getIata();

    boolean temAtendimento(final LocationTravel origem, final LocationTravel destino);

    Set<Voo> voos(final LocationTravel origem, final LocationTravel destino, final LocalDateTime ida, final LocalDateTime volta);

    void selecionarAssento(final String siglaVoo, final String numeroAssento);

}
