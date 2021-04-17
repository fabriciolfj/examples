package br.com.coffeeandit.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = {"name", "city"})
public class Airport {

    @JsonProperty("name")
    private String name;
    @JsonIgnore
    @JsonProperty("city")
    private String city;
    @JsonIgnore
    @JsonProperty("country")
    private String country;
    @JsonProperty("country_code")
    private Object countryCode;
    @JsonProperty("iata")
    private String iata;
    @JsonProperty("icao")
    private String icao;
    @JsonProperty("x")
    private String x;
    @JsonProperty("y")
    private String y;
    @JsonProperty("elevation")
    private String elevation;
    @JsonProperty("apid")
    private String apid;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("dst")
    private String dst;
    @JsonProperty("tz_id")
    private String tzId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("source")
    private String source;
    @JsonProperty("ap_uid")
    private String apUid;
    @JsonProperty("ap_name")
    private String apName;
}
