package com.entertainment.project.common.utils;

import android.widget.Toast;

import com.entertainment.project.application.MainApplication;
import com.entertainment.project.view.HJToast;

/**
 * Created by HugoXie on 16/5/23.
 *
 * Email: Hugo3641@gamil.com
 * GitHub: https://github.com/xcc3641
 * Info:
 */
public class ToastUtil {

    public static void showShort(String msg) {
        HJToast.makeText(MainApplication.getApp(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg) {
        HJToast.makeText(MainApplication.getApp(), msg, Toast.LENGTH_LONG).show();
    }
}
