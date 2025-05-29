package com.meuprojeto.alertaapagao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Wind {
    @JsonProperty("speed")
    private double speed; // Velocidade do vento (m/s por padrão)
    @JsonProperty("deg")
    private int deg; // Direção do vento (graus)
    @JsonProperty("gust")
    private Double gust; // Rajada de vento (m/s) - pode ser nulo

    // Getters e Setters
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }
    public int getDeg() { return deg; }
    public void setDeg(int deg) { this.deg = deg; }
    public Double getGust() { return gust; }
    public void setGust(Double gust) { this.gust = gust; }

    // Método utilitário para converter m/s para km/h
    public double getSpeedKmh() {
        return speed * 3.6;
    }

    public Double getGustKmh() {
        return gust != null ? gust * 3.6 : null;
    }
}

