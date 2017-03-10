package com.entertainment.project.common.retrofit;

import com.entertainment.project.BuildConfig;
import com.entertainment.project.common.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sick on 2017/2/10.
 */
public class RetrofitSingleton {
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    private static ApiInterface apiInterface;

    public static RetrofitSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        public static final RetrofitSingleton INSTANCE = new RetrofitSingleton();
    }

    public RetrofitSingleton() {
        init();
    }

    private void init() {
        initOKHttp();
//        initRetrofit();
    }

    private Retrofit initRetrofit(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    private void initOKHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }

//        File cacheFile = new File(MainApplication.getAppCacheDir(), "NetCache");
//        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);

        builder.cache(new CacheProvide().provideCache())
                .addInterceptor(new CacheInterceptor())
                .addNetworkInterceptor(new CacheInterceptor());
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);//超时时间15S
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);//连接失败后是否重新连接
        okHttpClient = builder.build();
    }


    /**
     * 处理异常信息
     *
     * @param t
     */
    public static void disposeFailureInfo(Throwable t) {

    }

    /**
     * 获取api
     *
     * @return
     */
    public ApiInterface getApiService() {
        if (apiInterface == null) {
            Retrofit retrofit = initRetrofit(Constants.url.BASE_URL_SSO);
            apiInterface = retrofit.create(ApiInterface.class);
        }
        return apiInterface;
    }


}
