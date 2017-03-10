package com.entertainment.project.modules.weather.model;

import com.entertainment.project.common.Constants;
import com.entertainment.project.common.retrofit.RetrofitSingleton;
import com.entertainment.project.common.retrofit.RxUtils;
import com.entertainment.project.modules.weather.domain.Weather;
import com.entertainment.project.modules.weather.domain.WeatherModel;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Sick on 2017/2/15.
 */
public class WeatherService {
    public Observable<Weather> getDataByWeatherApi(String city) {
        return RetrofitSingleton.getInstance().getApiService().getWeatherApi(city, Constants.WEATHER_KEY)
                .flatMap(new Func1<WeatherModel, Observable<WeatherModel>>() {
                    @Override
                    public Observable<WeatherModel> call(WeatherModel weatherModel) {
                        String status = weatherModel.weathers.get(0).status;
                        if (Constants.WeatherStatus.NORMAL_STATUS.equals(status)) {
                            return Observable.just(weatherModel);
                        } else if (Constants.WeatherStatus.NO_MORE_REQUESTS_STATUS.equals(status)) {
                            return Observable.error(new RuntimeException("%>_<% API免费次数已用完！"));
                        } else if (Constants.WeatherStatus.UNKNOWN_CITY_STATUS.equals(status)) {
                            return Observable.error(new RuntimeException(String.format("API无法识别%s", city)));
                        } else {
                            return Observable.error(new RuntimeException("API发生异常"));
                        }
                    }
                })
                .map(new Func1<WeatherModel, Weather>() {
                    @Override
                    public Weather call(WeatherModel weatherModel) {
                        return weatherModel.weathers.get(0);
                    }
                })
                .compose(RxUtils.rxSchedulerHelper());
    }
}
