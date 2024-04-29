package com.example.rysbekov_api.models;

import com.google.gson.annotations.SerializedName;

public class WeatherModel {
    @SerializedName("icon")
    private String icon;

    @SerializedName("cod")
    private Integer code;

//
//
//
//    @SerializedName("timezone")
//    private long timezone;
//
//
//    @SerializedName("speed")
//    private double speed;
//
//    @SerializedName("wind_kph")
//    private double wind_kph;
//
//    @SerializedName("sunrise")
//    private long sunrise;
//
//    @SerializedName("sunset")
//    private long sunset;
//
////    @SerializedName("all")
//    private int all;

    public String getIcon() {
        return icon;
    }

    public Integer getCode() {
        return code;
    }



//
//
//
//
//    public long getTimezone() {
//        return timezone;
//    }
//
//
//    public double getSpeed() {
//        return speed;
//    }
//
//    public double getWind_kph() {
//        return wind_kph;
//    }
//
//    public long getSunrise() {
//        return sunrise;
//    }
//
//    public long getSunset() {
//        return sunset;
//    }

//    public int getAll() {
//        return all;
//    }
}
