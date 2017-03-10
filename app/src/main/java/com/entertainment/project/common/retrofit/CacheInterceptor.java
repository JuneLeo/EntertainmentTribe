package com.entertainment.project.common.retrofit;

import com.entertainment.project.application.MainApplication;
import com.entertainment.project.common.utils.Utils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Sick on 2017/2/13.
 * 缓存拦截器
 */
public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!Utils.isNetworkConnected(MainApplication.getApp())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        Response.Builder newBuilder = response.newBuilder();
        if (Utils.isNetworkConnected(MainApplication.getApp())) {
            int maxAge = 0;//设置时间，例如  设置60  即为60秒内从缓存里获取，超过60秒重新网络获取
            newBuilder.removeHeader("Pragma");
            newBuilder.header("Cache-Control", "public, max-age=" + maxAge);
        } else {
            // 无网络时，设置超时为4周
            int maxStale = 60 * 60 * 24 * 28;
            newBuilder.removeHeader("Pragma");
            newBuilder.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
        }
        return newBuilder.build();
    }
}
