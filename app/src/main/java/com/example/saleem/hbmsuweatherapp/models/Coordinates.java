package com.example.saleem.hbmsuweatherapp.models;

import com.google.gson.annotations.SerializedName;

public class Coordinates {

        @SerializedName("lat")
        private double lat;

        @SerializedName("lon")
        private double lon;

    public Coordinates(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
