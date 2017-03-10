package com.entertainment.project.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by spf on 16/10/27.
 * <p>
 * Activity管理器  onResume中将activityset了进去
 */
public class ActivityManager {

    private static ActivityManager instance;
    private Application application;
    private WeakReference<Activity> mCurrentActivity;


    public static ActivityManager getInstance() {
        if (instance == null) {
            synchronized (ActivityManager.class) {
                if (instance == null) {
                    instance = new ActivityManager();
                }
            }
        }
        return instance;
    }

    public void initial(Application app) {
        application = app;
        registerManager();
    }

    private void registerManager() {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                setCurrentActivity(activity);
                Log.d("current activity is ", activity.getComponentName().getClassName());
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    /**
     * 获取当前Activity实例
     *
     * @return
     */
    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (mCurrentActivity != null) {
            currentActivity = mCurrentActivity.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        mCurrentActivity = new WeakReference<Activity>(activity);
    }


}
