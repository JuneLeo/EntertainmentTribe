package com.entertainment.project.react;

import com.entertainment.project.BuildConfig;
import com.entertainment.project.application.MainApplication;
import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.shell.MainReactPackage;


/**
 * Created by Sick on 2017/2/23.
 */
public class ReactManager {
    private static ReactInstanceManager mReactInstanceManager = null;

    public static ReactInstanceManager getINstance() {
        if (mReactInstanceManager == null) {
            mReactInstanceManager = ReactInstanceManager.builder()
                    .setApplication(MainApplication.getApp())
                    .setBundleAssetName("index.android.bundle")
                    .setJSMainModuleName("index.android")
                    .addPackage(new MainReactPackage())
                    .setUseDeveloperSupport(BuildConfig.DEBUG)
                    .setInitialLifecycleState(LifecycleState.RESUMED)
                    .build();

        }
        return mReactInstanceManager;
    }
}
