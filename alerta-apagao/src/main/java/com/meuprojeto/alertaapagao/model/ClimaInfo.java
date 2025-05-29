package com.meuprojeto.alertaapagao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClimaInfo {

    @JsonProperty("coord")
    private Coord coord;

    @JsonProperty("weather")
    private List<Weather> weather;

    @JsonProperty("base")
    private String base;

    @JsonProperty("main")
    private MainData main;

    @JsonProperty("visibility")
    private int visibility;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("rain")
    private Rain rain; // Pode ser nulo se não estiver chovendo

    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("dt")
    private long dt; // Timestamp da coleta dos dados

    @JsonProperty("sys")
    private Sys sys; // Informações do sistema (país, nascer/pôr do sol)

    @JsonProperty("timezone")
    private int timezone; // Shift em segundos do UTC

    @JsonProperty("id")
    private int id; // ID da cidade

    @JsonProperty("name")
    private String name; // Nome da cidade

    @JsonProperty("cod")
    private int cod; // Código de status interno

    // Getters e Setters
    public Coord getCoord() { return coord; }
    public void setCoord(Coord coord) { this.coord = coord; }
    public List<Weather> getWeather() { return weather; }
    public void setWeather(List<Weather> weather) { this.weather = weather; }
    public String getBase() { return base; }
    public void setBase(String base) { this.base = base; }
    public MainData getMain() { return main; }
    public void setMain(MainData main) { this.main = main; }
    public int getVisibility() { return visibility; }
    public void setVisibility(int visibility) { this.visibility = visibility; }
    public Wind getWind() { return wind; }
    public void setWind(Wind wind) { this.wind = wind; }
    public Rain getRain() { return rain; }
    public void setRain(Rain rain) { this.rain = rain; }
    public Clouds getClouds() { return clouds; }
    public void setClouds(Clouds clouds) { this.clouds = clouds; }
    public long getDt() { return dt; }
    public void setDt(long dt) { this.dt = dt; }
    public Sys getSys() { return sys; }
    public void setSys(Sys sys) { this.sys = sys; }
    public int getTimezone() { return timezone; }
    public void setTimezone(int timezone) { this.timezone = timezone; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCod() { return cod; }
    public void setCod(int cod) { this.cod = cod; }

    // Classe interna para Sys
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sys {
        @JsonProperty("type")
        private int type;
        @JsonProperty("id")
        private int id;
        @JsonProperty("country")
        private String country;
        @JsonProperty("sunrise")
        private long sunrise;
        @JsonProperty("sunset")
        private long sunset;

        // Getters e Setters
        public int getType() { return type; }
        public void setType(int type) { this.type = type; }
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public long getSunrise() { return sunrise; }
        public void setSunrise(long sunrise) { this.sunrise = sunrise; }
        public long getSunset() { return sunset; }
        public void setSunset(long sunset) { this.sunset = sunset; }
    }
}

