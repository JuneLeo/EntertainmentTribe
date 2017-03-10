package com.entertainment.project.application;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.entertainment.project.common.ActivityManager;
import com.entertainment.project.common.Constants;
import com.entertainment.project.common.Log4j;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;

import java.util.List;

/**
 * Created by Sick on 2016/10/17.
 */
public class MainApplication extends Application implements ReactApplication {

    private static MainApplication context = null;
    private static String sCacheDir;
//    private static DaoMaster.DevOpenHelper helper;
//    private static DaoMaster daoMaster;
//    private static DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);

        ActivityManager.getInstance().initial(this);

//        AppCrashExceptionHandler crashHandler = AppCrashExceptionHandler.getInstance();
//        // 注册crashHandler
//        crashHandler.init(getApplicationContext());
//        //发送异常到服务器
//        crashHandler.sendPreviousReportsToServer(this);

//        BlockCanary.install(this, new AppBlockCanaryContext()).start();
//        LeakCanary.install(this);


        Constants.releaseType = getReleaseType();
        Constants.url.initUrl(Constants.releaseType);
        //greenDao

//        getDaoMaster();

        initHasSDCard();

    }

    /**
     * 系统缓存路径
     */
    private void initHasSDCard() {
        if (getApplicationContext().getExternalCacheDir() != null && ExistSDCard()) {
            sCacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            sCacheDir = getApplicationContext().getCacheDir().toString();
        }
    }

    private boolean ExistSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String getAppCacheDir() {
        return sCacheDir;
    }


    private String getReleaseType() {
        ApplicationInfo appInfo = null;
        try {
            appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log4j.error(MainApplication.class, e);
        }
        if (appInfo != null) {
            String releaseType = appInfo.metaData.getString("ReleaseType");
            if (Constants.ReleaseType.Product.equalsIgnoreCase(releaseType)) {
                return Constants.ReleaseType.Product;
            } else {
                return Constants.ReleaseType.Test;
            }
        } else {
            return Constants.ReleaseType.Test;
        }
    }

    /**
     * 获取到Appliction
     *
     * @return
     */
    public static MainApplication getApp() {
        return context;
    }

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        protected boolean getUseDeveloperSupport() {
            return false;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return null;
        }
    };


//    /**
//     * 获取daoMaster
//     *
//     * @return
//     */
//    public static DaoMaster getDaoMaster() {
//        if (daoMaster == null) {
//            helper = new DaoMaster.DevOpenHelper(getApp(), Constants.dbName, null);
//            SQLiteDatabase db = helper.getWritableDatabase();
//            daoMaster = new DaoMaster(db);
//        }
//        return daoMaster;
//    }

//    /**
//     * 获取daoSession
//     *
//     * @return
//     */
//    public static DaoSession getDaoSession() {
//        if (daoSession == null) {
//            if (daoMaster == null) {
//                daoMaster = getDaoMaster();
//            }
//            daoSession = daoMaster.newSession();
//        }
//        return daoSession;
//    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }




}
