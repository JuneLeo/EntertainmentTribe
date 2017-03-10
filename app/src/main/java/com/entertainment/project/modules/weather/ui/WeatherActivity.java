package com.entertainment.project.modules.weather.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.entertainment.project.R;
import com.entertainment.project.base.BaseActivity;
import com.entertainment.project.modules.weather.domain.Weather;
import com.entertainment.project.modules.weather.model.WeatherService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by Sick on 2017/2/15.
 */
public class WeatherActivity extends BaseActivity {

    @Bind(R.id.btn_weather)
    Button mBtnWeather;
    private String TAG = WeatherActivity.class.getSimpleName();
    WeatherService weatherService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        weatherService = new WeatherService();

    }

    @OnClick(R.id.btn_weather)
    public void onClick() {

    }

    /**
     * 将Observable的生命周期与activity绑定
     *
     * @return
     */
    public Observable<Weather> fetchDataByWeatherApi() {
        return weatherService.getDataByWeatherApi("北京")
                .compose(this.bindToLifecycle());
    }

}
