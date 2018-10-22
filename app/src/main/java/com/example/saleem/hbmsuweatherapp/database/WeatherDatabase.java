package com.example.saleem.hbmsuweatherapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.saleem.hbmsuweatherapp.dao.DaoAccess;
import com.example.saleem.hbmsuweatherapp.models.WeatherEntityModel;

@Database(entities = {WeatherEntityModel.class}, version = 1, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}
