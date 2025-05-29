package com.meuprojeto.alertaapagao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Clouds {
    // Nebulosidade (%)
    @JsonProperty("all")
    private int all;

    // Getters e Setters
    public int getAll() { return all; }
    public void setAll(int all) { this.all = all; }
}

