package com.example.rysbekov_api.models;

import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("humidity")
    private int humidity;

    @SerializedName("pressure")
    private int pressure;

    @SerializedName("name")
    private  String name;

    @SerializedName("temp_max")
    private Double tempMax;


    @SerializedName("temp_min")
    private Double tempMin;

    @SerializedName("temp")
    private  Double temp;

    public int getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public String getName() {
        return name;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public Double getTemp() {
        return temp;
    }

}
