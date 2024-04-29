package com.example.rysbekov_api.remote_data;



import com.example.rysbekov_api.models.Model;
import com.example.rysbekov_api.models.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    public static final   String URL ="06816f399f341125658e39ba0b71744c";

    @GET("/data/2.5/weather")
    Call<Model> getCurrentWeather(
            @Query("q") String name,
            @Query("appid") String key);


    @GET("/data/2.5/weather?q=London&appid=06816f399f341125658e39ba0b71744c")
    Call<WeatherModel> getWeather(
            @Query("q") String name,
            @Query("appid")String key);



    String url = "api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=06816f399f341125658e39ba0b71744c";


}
