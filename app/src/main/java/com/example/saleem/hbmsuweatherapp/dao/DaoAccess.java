package com.example.saleem.hbmsuweatherapp.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.saleem.hbmsuweatherapp.models.WeatherEntityModel;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    void insertOnlySingleWeather (WeatherEntityModel weather);

    @Insert
    void insertMultipleWeathers (List<WeatherEntityModel> weathersList);

    @Query("SELECT * FROM WeatherEntityModel WHERE weatherId = :weatherId")
    WeatherEntityModel fetchOneWeatherbyWeatherId (int weatherId);

    @Query("SELECT * FROM WeatherEntityModel WHERE id = :cityId")
    WeatherEntityModel fetchOneWeatherbyCityId (int cityId);

    @Query("SELECT * FROM WeatherEntityModel")
    List<WeatherEntityModel> fetchAllWeatherList ();

    @Query("DELETE FROM WeatherEntityModel")
    void deleteWeatherList ();

    @Update
    void updateWeather (WeatherEntityModel weather);

    @Delete
    void deleteWeather (WeatherEntityModel weather);
}