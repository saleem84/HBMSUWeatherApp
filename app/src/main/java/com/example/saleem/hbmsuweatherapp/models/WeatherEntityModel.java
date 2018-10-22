package com.example.saleem.hbmsuweatherapp.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class WeatherEntityModel {

    @NonNull
    @PrimaryKey
    private Integer WeatherId;

    private Double lat;
    private Double lon;

    private String sunrise;
    private String sunset;

    private String titleMain;
    private String description;

    private Double temperature;
    private Double pressure;
    private Integer humidity;
    private Double temp_min;
    private Double temp_max;

    private String visibility;

    private Double wind_speed;

    private String dtWeatherTime;

    private String dtLastUpdate;

    private Integer id;

    private String name;

    private String imageURL;

    public WeatherEntityModel() {
    }

    public WeatherEntityModel(@NonNull Integer weatherId, Double lat, Double lon, String sunrise, String sunset, String titleMain, String description, Double temperature, Double pressure, Integer humidity, Double temp_min, Double temp_max, String visibility, Double wind_speed, String dtWeatherTime, String dtLastUpdate, Integer id, String name, String imageURL) {
        WeatherId = weatherId;
        this.lat = lat;
        this.lon = lon;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.titleMain = titleMain;
        this.description = description;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.visibility = visibility;
        this.wind_speed = wind_speed;
        this.dtWeatherTime = dtWeatherTime;
        this.dtLastUpdate = dtLastUpdate;
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
    }

    @NonNull
    public Integer getWeatherId() {
        return WeatherId;
    }

    public void setWeatherId(@NonNull Integer weatherId) {
        WeatherId = weatherId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getTitleMain() {
        return titleMain;
    }

    public void setTitleMain(String titleMain) {
        this.titleMain = titleMain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Double temp_min) {
        this.temp_min = temp_min;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Double temp_max) {
        this.temp_max = temp_max;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(Double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public String getDtWeatherTime() {
        return dtWeatherTime;
    }

    public void setDtWeatherTime(String dtWeatherTime) {
        this.dtWeatherTime = dtWeatherTime;
    }

    public String getDtLastUpdate() {
        return dtLastUpdate;
    }

    public void setDtLastUpdate(String dtLastUpdate) {
        this.dtLastUpdate = dtLastUpdate;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
