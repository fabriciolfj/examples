package br.com.coffeeandit.app.observer;

import br.com.coffeeandit.app.business.voos.Assento;
import br.com.coffeeandit.app.business.voos.Voo;

public interface AssentoObserver {

    void selecionarAssento(final Assento assento, final Voo voo);
    void liberarAssento(final Assento assento, final Voo voo);
}
