package com.entertainment.project.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.entertainment.project.react.ReactManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;

/**
 * Created by Sick on 2017/2/23.
 */
public abstract class BaseReactActivity extends BaseAppActivity implements DefaultHardwareBackBtnHandler{
    public ReactRootView mReactRootView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initReact();
    }

    protected abstract void initReact();

    @Override
    protected void onPause() {
        super.onPause();
        if (ReactManager.getINstance() != null) {
            ReactManager.getINstance().onHostPause(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ReactManager.getINstance() != null) {
            ReactManager.getINstance().onHostResume(this,this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ReactManager.getINstance() != null) {
            ReactManager.getINstance().onHostDestroy();
        }
    }

    @Override
    public void onBackPressed() {
        if (ReactManager.getINstance() != null) {
            ReactManager.getINstance().onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && ReactManager.getINstance() != null) {
            ReactManager.getINstance().showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }


    @Override
    public void invokeDefaultOnBackPressed() {

    }
}
