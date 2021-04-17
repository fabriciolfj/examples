package br.com.coffeeandit.app.config;

import br.com.coffeeandit.app.observer.AssentoNotification;
import br.com.coffeeandit.app.observer.log.LogAssentoObserver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

@Configuration
public class AplicacaoConfig {

//    public static final String PASSWORD = "password";
//    public static final String JKS = "JKS";

    @Bean
    public AssentoNotification createAssentoNotification(LogAssentoObserver logAssentoObserver) {
        var assentoNotification = new AssentoNotification();
        assentoNotification.addObserver(logAssentoObserver);
        return assentoNotification;
    }

//    @Bean
//    public KeyStore keyStore() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
//        KeyStore keyStore = KeyStore.getInstance(JKS);
//        keyStore.load(new ClassPathResource(
//                "keystore.jks").getInputStream(), PASSWORD.toCharArray());
//        return keyStore;
//    }
//
//    @Bean
//    public KeyPair keyPair(final KeyStore keyStore) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
//        final Certificate certificate = keyStore.getCertificate("1");
//        Key key = keyStore.getKey("1", PASSWORD.toCharArray());
//        final KeyPair keyPair = new KeyPair(certificate.getPublicKey(), (PrivateKey) key);
//        return keyPair;
//    }
}
