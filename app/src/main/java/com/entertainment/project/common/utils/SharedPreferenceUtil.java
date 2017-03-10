package com.entertainment.project.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.entertainment.project.application.MainApplication;

/**
 * Created by Sick on 2017/1/18.
 */
public class SharedPreferenceUtil {
    //定义常量键值  key

    public static String SP_KEY_CITY_NAME = "city_name";

    private String SP_NAME = "entertainment_tribe";

    private SharedPreferences mPrefs;

    public static SharedPreferenceUtil getInstance() {
        return SPHolder.mInstance;
    }

    private static class SPHolder {
        public static SharedPreferenceUtil mInstance = new SharedPreferenceUtil();
    }

    public SharedPreferenceUtil() {
        mPrefs = MainApplication.getApp().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public SharedPreferenceUtil putInt(String key, int value) {
        mPrefs.edit().putInt(key, value).apply();
        return this;
    }

    public int getInt(String key, int defValue) {
        return mPrefs.getInt(key, defValue);
    }

    public SharedPreferenceUtil putString(String key, String value) {
        mPrefs.edit().putString(key, value).apply();
        return this;
    }

    public String getString(String key, String defValue) {
        return mPrefs.getString(key, defValue);
    }

    public SharedPreferenceUtil putBoolean(String key, boolean value) {
        mPrefs.edit().putBoolean(key, value).apply();
        return this;
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPrefs.getBoolean(key, defValue);
    }

    //也可以根据一些具体的业务信息来存储 eg=================================================
    public void setCityName(String cityName) {
        mPrefs.edit().putString(SP_KEY_CITY_NAME, cityName);
    }

    public String getCityName() {
        return mPrefs.getString(SP_KEY_CITY_NAME, "北京");
    }
    //===================================================================================

}
