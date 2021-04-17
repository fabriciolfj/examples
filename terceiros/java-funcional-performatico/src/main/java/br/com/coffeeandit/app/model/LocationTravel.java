package br.com.coffeeandit.app.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LocationTravel implements BuilderObject {

    private static final String AMERICA_SAO_PAULO = "America/Sao_Paulo";
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationTravel that = (LocationTravel) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private Integer id;
    private Region region;
    private Double latitude;
    private Double longitude;

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    private final LocalDateTime dataConsulta;

    public List<Airport> getAirports() {
        if (Objects.isNull(airports)) {
            airports = new ArrayList<>();
        }
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }

    private List<Airport> airports;


    private LocationTravel() {
        dataConsulta = LocalDateTime.now(ZoneId.of(AMERICA_SAO_PAULO));
    }

    public String getName() {
        return name;
    }


    public Integer getId() {
        return id;
    }

    public Region getRegion() {
        return region;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public LocationTravel name(String name) {
        this.name = name;
        return this;
    }

    public LocationTravel id(Integer id) {
        this.id = id;
        return this;
    }

    public LocationTravel region(Region region) {
        this.region = region;
        return this;
    }

    public LocationTravel latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public LocationTravel longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public static LocationTravel builder() {
        return new LocationTravel();
    }

    public LocationTravel build() {
        return this;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", region=" + region +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", signature=" + signature() +

                '}';
    }
}
