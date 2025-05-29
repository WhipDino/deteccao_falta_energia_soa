package com.meuprojeto.alertaapagao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MainData {
    @JsonProperty("temp")
    private double temp; // Temperatura (em Kelvin por padrão, pode precisar converter)
    @JsonProperty("feels_like")
    private double feelsLike;
    @JsonProperty("temp_min")
    private double tempMin;
    @JsonProperty("temp_max")
    private double tempMax;
    @JsonProperty("pressure")
    private int pressure; // Pressão atmosférica (hPa)
    @JsonProperty("humidity")
    private int humidity; // Umidade (%)

    // Getters e Setters
    public double getTemp() { return temp; }
    public void setTemp(double temp) { this.temp = temp; }
    public double getFeelsLike() { return feelsLike; }
    public void setFeelsLike(double feelsLike) { this.feelsLike = feelsLike; }
    public double getTempMin() { return tempMin; }
    public void setTempMin(double tempMin) { this.tempMin = tempMin; }
    public double getTempMax() { return tempMax; }
    public void setTempMax(double tempMax) { this.tempMax = tempMax; }
    public int getPressure() { return pressure; }
    public void setPressure(int pressure) { this.pressure = pressure; }
    public int getHumidity() { return humidity; }
    public void setHumidity(int humidity) { this.humidity = humidity; }

    // Método utilitário para converter Kelvin para Celsius
    public double getTempCelsius() {
        return temp - 273.15;
    }
}

