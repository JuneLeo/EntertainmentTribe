package com.entertainment.project.view;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.entertainment.project.R;


/**
 * Created by Sick on 2016/6/27.
 */
public class HJToast extends Toast {

    public HJToast(Context context, String message, int timeLong) {

        super(context);
        if (context == null) {
            return;
        }
        //设置外层容器

        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout,null);


        //给字体设置动画效果
        TranslateAnimation alphaAnimation2 = new TranslateAnimation(-20f, 0, 0, 0);
        alphaAnimation2.setDuration(90);
        alphaAnimation2.setRepeatCount(9);
        alphaAnimation2.setRepeatMode(Animation.REVERSE);

        TextView tvToastText = (TextView) view.findViewById(R.id.tv_toast_text);
        tvToastText.setText(message);
//        tvToastText.setAnimation(alphaAnimation2);
        setDuration(timeLong);
        setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, dip2px(context, 80));
        setView(view);

    }

    public HJToast(Context context) {
        super(context);
        if (context == null) {
            return;
        }
    }

    public void shows() {
        //把Toast.show添加到消息队列中并显示
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                show();
            }
            //显示延迟时间
        }, 0);

    }



    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



    public static HJToast makeText(Context context, String message, int timeLong) {
        HJToast mHJToast = new HJToast(context);

        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout,null);
        //给字体设置动画效果
        TranslateAnimation alphaAnimation2 = new TranslateAnimation(-20f, 0, 0, 0);
        alphaAnimation2.setDuration(90);
        alphaAnimation2.setRepeatCount(9);
        alphaAnimation2.setRepeatMode(Animation.REVERSE);

        TextView tvToastText = (TextView) view.findViewById(R.id.tv_toast_text);
        tvToastText.setText(message);
//        tvToastText.setAnimation(alphaAnimation2);
        mHJToast.setDuration(timeLong);
        mHJToast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, dip2px(context, 80));
        mHJToast.setView(view);

        return mHJToast;


    }



}
