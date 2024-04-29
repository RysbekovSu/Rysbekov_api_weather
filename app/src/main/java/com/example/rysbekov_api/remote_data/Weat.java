package com.example.rysbekov_api.remote_data;

import com.example.rysbekov_api.models.Model;
import com.example.rysbekov_api.models.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Weat{
    @GET("/data/2.5/weather")
    Call<Model> getCurrentWeather(
            @Query("q") String name,
            @Query("appid") String key);

    @GET("/data/2.5/weather?lat=44.34&lon=10.99&appid=c712fbc6e2ab21a5f2633d814000ace6")
    Call<WeatherModel> getWeather(
            @Query("q") String name,
            @Query("appid") String key);
}