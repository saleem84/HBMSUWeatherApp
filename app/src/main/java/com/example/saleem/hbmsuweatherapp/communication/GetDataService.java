package com.example.saleem.hbmsuweatherapp.communication;

import com.example.saleem.hbmsuweatherapp.models.RetrofitWeather;
import com.example.saleem.hbmsuweatherapp.utils.ApiTags;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET(ApiTags.getAllWeatherData)
    Call<RetrofitWeather> getAllWeatherData(@Query("id") String id, @Query("units") String units, @Query("appid") String appid);
}