package com.meuprojeto.alertaapagao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rain {
    // Volume de chuva na última 1 hora, em mm
    @JsonProperty("1h")
    private Double volume1h;

    // Volume de chuva nas últimas 3 horas, em mm (pode não estar sempre presente)
    @JsonProperty("3h")
    private Double volume3h;

    // Getters e Setters
    public Double getVolume1h() { return volume1h; }
    public void setVolume1h(Double volume1h) { this.volume1h = volume1h; }
    public Double getVolume3h() { return volume3h; }
    public void setVolume3h(Double volume3h) { this.volume3h = volume3h; }
}

