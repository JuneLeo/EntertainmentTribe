package com.entertainment.project.common.retrofit;
import android.util.Log;

import com.entertainment.project.application.MainApplication;

import java.io.File;

import okhttp3.Cache;

public class CacheProvide {

    private static final String TAG = CacheProvide.class.getSimpleName();
    File cacheFile;

    public CacheProvide() {
        cacheFile = new File(MainApplication.getAppCacheDir(), "NetCache");
        Log.d(TAG,cacheFile.getAbsolutePath());
    }

    public Cache provideCache() {//使用应用缓存文件路径，缓存大小为10MB
        return new Cache(cacheFile, 1024 * 1024 * 10);
    }
}