package com.meuprojeto.alertaapagao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    @JsonProperty("id")
    private int id;
    @JsonProperty("main")
    private String main; // e.g., "Rain", "Clouds"
    @JsonProperty("description")
    private String description;
    @JsonProperty("icon")
    private String icon;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMain() { return main; }
    public void setMain(String main) { this.main = main; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
}

