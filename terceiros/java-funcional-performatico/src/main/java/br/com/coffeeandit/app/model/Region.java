package br.com.coffeeandit.app.model;

import java.util.Objects;

public class Region {

    private String name;
    private String code;

    private Region() {

    }

    public static Region builder() {
        return new Region();
    }

    public Region build() {
        return this;
    }

    public String getName() {
        return name;
    }

    public Region name(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Region code(String code) {
        this.code = code;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public Region country(Country country) {
        this.country = country;
        return this;
    }

    private Country country;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return Objects.equals(code, region.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Region{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", country=" + country +
                '}';
    }
}
