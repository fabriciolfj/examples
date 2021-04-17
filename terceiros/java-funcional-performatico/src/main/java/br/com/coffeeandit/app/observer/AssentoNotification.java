package br.com.coffeeandit.app.observer;

import br.com.coffeeandit.app.business.voos.Assento;
import br.com.coffeeandit.app.business.voos.Voo;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class AssentoNotification {

    private Set<AssentoObserver> assentos = new HashSet<>();

    public void addObserver(final AssentoObserver assentoObserver) {
        assentos.add(assentoObserver);
    }

    public void removeObserver(final AssentoObserver assentoObserver) {
        assentos.remove(assentoObserver);
    }

    public Consumer<? super Assento> selecionarAssento(final Assento assento, final Voo voo) {
        assentos.parallelStream().forEach(assentoObserver -> assentoObserver.selecionarAssento(assento, voo));
        return assento1 -> {};
    }

    public Consumer<Assento> liberarAssento(final Assento assento, final Voo voo) {
        assentos.parallelStream().forEach(assentoObserver -> assentoObserver.liberarAssento(assento, voo));
        return assento1 -> {};
    }
}
