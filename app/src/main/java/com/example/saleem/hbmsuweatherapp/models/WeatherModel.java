package com.example.saleem.hbmsuweatherapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherModel {

    @SerializedName("coord")
    private Coordinates coord;

    @SerializedName("sys")
    private WeatherSystem sys;

    @SerializedName("weather")
    private List<Weather> weather;

    @SerializedName("main")
    private WeatherMain main;

    @SerializedName("visibility")
    private String visibility;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("clouds")
    private Clouds clouds;

    @SerializedName("dt")
    private String dt;

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    public WeatherModel(Coordinates coord, WeatherSystem sys, List<Weather> weather, WeatherMain main, String visibility, Wind wind, Clouds clouds, String dt, Integer id, String name) {
        this.coord = coord;
        this.sys = sys;
        this.weather = weather;
        this.main = main;
        this.visibility = visibility;
        this.wind = wind;
        this.clouds = clouds;
        this.dt = dt;
        this.id = id;
        this.name = name;
    }

    public Coordinates getCoord() {
        return coord;
    }

    public void setCoord(Coordinates coord) {
        this.coord = coord;
    }

    public WeatherSystem getSys() {
        return sys;
    }

    public void setSys(WeatherSystem sys) {
        this.sys = sys;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public WeatherMain getMain() {
        return main;
    }

    public void setMain(WeatherMain main) {
        this.main = main;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
