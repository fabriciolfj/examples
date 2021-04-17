package br.com.coffeeandit.app.observer.log;

import br.com.coffeeandit.app.business.voos.Assento;
import br.com.coffeeandit.app.business.voos.Voo;
import br.com.coffeeandit.app.observer.AssentoObserver;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
@Log
public class LogAssentoObserver implements AssentoObserver {
    @Override
    public void selecionarAssento(final Assento assento, final Voo voo) {
        log.info("Assento selecionado " + assento + " no voo " + voo);
    }

    @Override
    public void liberarAssento(final Assento assento, final Voo voo) {
        log.info("Assento liberado " + assento + " no voo " + voo);

    }
}
