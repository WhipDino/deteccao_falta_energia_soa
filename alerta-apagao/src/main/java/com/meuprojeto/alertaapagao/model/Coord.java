package com.meuprojeto.alertaapagao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// Ignora propriedades desconhecidas para evitar erros se a API mudar
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coord {
    @JsonProperty("lon")
    private double lon;
    @JsonProperty("lat")
    private double lat;

    // Getters e Setters (ou tornar público se preferir)
    public double getLon() { return lon; }
    public void setLon(double lon) { this.lon = lon; }
    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }
}

