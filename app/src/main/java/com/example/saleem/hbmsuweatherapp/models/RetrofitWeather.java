package com.example.saleem.hbmsuweatherapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RetrofitWeather {

    @SerializedName("cnt")
    private Integer cnt;

    @SerializedName("list")
    private List<WeatherModel> list;

    public RetrofitWeather(Integer cnt, List<WeatherModel> list) {
        this.cnt = cnt;
        this.list = list;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public List<WeatherModel> getList() {
        return list;
    }

    public void setList(List<WeatherModel> list) {
        this.list = list;
    }
}
