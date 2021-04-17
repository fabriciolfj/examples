package br.com.coffeeandit.app.business.voos;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@EqualsAndHashCode(of = "id")
@ToString(of = "numero")
public class Assento {
    public String getId() {
        return id;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    private String preco;

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getPercentualAdicional() {
        return percentualAdicional;
    }

    public void setPercentualAdicional(BigDecimal percentualAdicional) {
        this.percentualAdicional = percentualAdicional;
    }

    public boolean isPrimeiraClasse() {
        return primeiraClasse;
    }

    public void setPrimeiraClasse(boolean primeiraClasse) {
        this.primeiraClasse = primeiraClasse;
    }

    private String id;
    private String numero;

    public boolean isOcupado() {
        return ocupado;
    }

    private boolean ocupado;
    private BigDecimal percentualAdicional;
    private boolean primeiraClasse;

    public Assento(String id, String numero, BigDecimal percentualAdicional, boolean primeiraClasse) {
        this.id = id;
        this.numero = numero;
        this.ocupado = ocupado;
        this.percentualAdicional = percentualAdicional;
        this.primeiraClasse = primeiraClasse;
    }

    public void ocupado() {
        this.ocupado = true;
    }

    public void liberar() {
        this.ocupado = false;
    }
}
