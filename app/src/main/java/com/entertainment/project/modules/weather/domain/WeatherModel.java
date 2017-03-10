package com.entertainment.project.modules.weather.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Sick on 2017/2/15.
 */
public class WeatherModel {
    @SerializedName("HeWeather5")
    @Expose
    public ArrayList<Weather> weathers = new ArrayList<>();
}
