package br.com.coffeeandit.app.transport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("wikiDataId")
    private String wikiDataId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("city")
    private String city;
    @JsonProperty("name")
    private String name;
    @JsonProperty("country")
    private String country;
    @JsonProperty("countryCode")
    private String countryCode;
    @JsonProperty("region")
    private String region;
    @JsonProperty("regionCode")
    private String regionCode;
    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("wikiDataId")
    public String getWikiDataId() {
        return wikiDataId;
    }

    @JsonProperty("wikiDataId")
    public void setWikiDataId(String wikiDataId) {
        this.wikiDataId = wikiDataId;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("countryCode")
    public String getCountryCode() {
        return countryCode;
    }

    @JsonProperty("countryCode")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    @JsonProperty("region")
    public void setRegion(String region) {
        this.region = region;
    }

    @JsonProperty("regionCode")
    public String getRegionCode() {
        return regionCode;
    }

    @JsonProperty("regionCode")
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    @JsonProperty("latitude")
    public Double getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public Double getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


}