package br.com.coffeeandit.app.transport;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.List;

public class GeoLocalizations {

    @JsonProperty("data")
    private List<Location> data = null;


    @JsonProperty("data")
    public List<Location> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<Location> data) {
        this.data = data;
    }


}