package com.entertainment.project.common.retrofit;

import com.entertainment.project.modules.weather.domain.WeatherModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Sick on 2017/2/10.
 */
public interface ApiInterface {
    @GET("https://free-api.heweather.com/v5/weather")
    Observable<WeatherModel> getWeatherApi(@Query("city") String city, @Query("key") String key);
}
