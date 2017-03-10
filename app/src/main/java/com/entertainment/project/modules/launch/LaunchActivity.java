package com.entertainment.project.modules.launch;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.entertainment.project.R;
import com.entertainment.project.base.BaseActivity;
import com.entertainment.project.modules.humor.ui.HumorActivity;

import java.lang.ref.WeakReference;

/**
 * Created by Sick on 2017/3/3.
 */
public class LaunchActivity extends BaseActivity {

    private static final String TAG = LaunchActivity.class.getSimpleName();
    private SwitchHandler mHandler = new SwitchHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //activity切换的淡入淡出效果
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        mHandler.sendEmptyMessageDelayed(1, 2000);

    }

    private static class SwitchHandler extends Handler {
        private WeakReference<LaunchActivity> mWeakReference;

        SwitchHandler(LaunchActivity activity) {
            mWeakReference = new WeakReference<LaunchActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LaunchActivity activity = mWeakReference.get();
            if (activity != null) {
                HumorActivity.launch(activity);
                activity.finish();
            }
        }
    }
}
