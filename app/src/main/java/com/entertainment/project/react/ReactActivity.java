package com.entertainment.project.react;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.entertainment.project.R;
import com.entertainment.project.base.BaseReactActivity;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;

/**
 * Created by Sick on 2017/2/23.
 */
public class ReactActivity extends BaseReactActivity {



    @NonNull
    @Override
    protected int getLayoutId() {
        return R.layout.activity_react;
    }

    @Override
    protected void handleIntent(Intent intent) {

    }

    @Override
    protected void findViews() {
        mReactRootView = (ReactRootView) findViewById(R.id.id_reactrootview);
    }

    @Override
    protected void bindData() {

    }

    @Override
    protected void initReact() {
        mReactRootView.startReactApplication(ReactManager.getINstance(),"BIGHRRO",null);
    }
}
