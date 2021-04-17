package br.com.coffeeandit.app.model;

import java.util.Objects;

public class Country {

    private Country() {

    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private String code;
    private String name;

    public Country code(final String code) {
        this.code = code;
        return this;
    }

    public Country name(final String name) {
        this.name = name;
        return this;
    }

    public static Country builder() {
        return new Country();
    }

    public Country build() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(code, country.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
