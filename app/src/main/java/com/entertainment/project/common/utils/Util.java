package com.entertainment.project.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.entertainment.project.R;
import com.entertainment.project.application.MainApplication;
import com.entertainment.project.common.ActivityManager;
import com.entertainment.project.common.Constants;
import com.entertainment.project.common.assist.RSAEncrypt;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 配置类
 * Created by Sick on 2016/10/27.
 */
public class Util {

    /**
     * 字符非空
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return str != null && !"".equals(str);
    }

    /**
     * 空字符
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return str == null || "".equals(str);
    }

    /**
     * 钱数值非空
     *
     * @param str
     * @return
     */
    public static boolean isNotBlankCost(String str) {
        return str != null && !"".equals(str) && !".".equals(str);
    }

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            Log.d("两次点击的时间间隔", String.valueOf(time - lastClickTime));
            return true;
        }
        lastClickTime = time;
        return false;
    }


    /**
     * toast指定位置显示
     *
     * @param str
     * @return
     */
    public static void showToast(Activity context, String str, int Location, int time) {

        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) context.findViewById(R.id.ll_toast));
       /* ImageView image = (ImageView) layout
                .findViewById(R.id.tv_image);
        image.setImageResource(R.drawable.ic_check_error);
        TextView title = (TextView) layout.findViewById(R.id.tv_title);
        title.setText("Attention");*/
        TextView text = (TextView) layout.findViewById(R.id.tv_toast);
        text.setText(str);
        Toast toast = new Toast(context);
        toast.setGravity(Location, 0, 0);
        toast.setDuration(time);
        toast.setView(layout);
        toast.show();
    }

    /**
     * toast顶部显示 在title下方
     *
     * @param str
     * @return
     */
    public static void showToastTop(Activity context, String str, int time) {

        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                null);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout relativeLayout = (LinearLayout) layout.findViewById(R.id.ll_toast);
        relativeLayout.setLayoutParams(layoutParams);
        if (!TextUtils.isEmpty(str)) {
            TextView text = (TextView) layout.findViewById(R.id.tv_toast);
            text.setText(str);
        }
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP, 0, ScreenUtils.dip2px(context, 138 - 100));
        toast.setDuration(time);
        toast.setView(layout);
        toast.show();
    }

    /**
     * 竖直方向动画移动
     *
     * @param p1   动画开始时相对组件y轴偏移量
     * @param p2   动画结束时相对组件y轴偏移量
     * @param view 动画组件
     */
    public static void slideview(float p1, float p2, final View view) {
        view.bringToFront();
        TranslateAnimation animation = new TranslateAnimation(0, 0, p1, p2);
        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(1000);
        animation.setStartOffset(0);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d("util", "动画结束");
                view.clearAnimation();
            }
        });
        view.startAnimation(animation);
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 去小数点
     */
    public static String removePoint(Object o) {
        if (o == null) {
            return "0";
        } else {
            String str = o.toString();
            int b = str.indexOf(".");
            if (b > -1) {
                str = str.substring(0, b);
            }
            return str;
        }
    }

    /**
     * 公匙加密
     *
     * @param context
     * @param encryptStr
     * @return
     */
    public static String encrypt(Context context, String encryptStr, int raw) {
        RSAEncrypt rsaEncrypt = new RSAEncrypt();
        String str = "";
        //加载公钥
        try {
            if (TextUtils.isEmpty(Constants.publicKey)) {
                InputStream ois = context.getResources().openRawResource(raw);
                rsaEncrypt.loadPublicKey(ois);
            } else {
                rsaEncrypt.loadPublicKey(Constants.publicKey);
            }
            System.out.println("加载公钥成功");
            str = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), encryptStr.getBytes());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return str;
    }


    /**
     * 设置资源图片颜色
     */
    //提取图像Alpha位图
    public static Bitmap getAlphaBitmap(Bitmap mBitmap, int mColor) {
//          BitmapDrawable mBitmapDrawable = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.enemy_infantry_ninja);
//          Bitmap mBitmap = mBitmapDrawable.getBitmap();

        //BitmapDrawable的getIntrinsicWidth（）方法，Bitmap的getWidth（）方法
        //注意这两个方法的区别
        //Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmapDrawable.getIntrinsicWidth(), mBitmapDrawable.getIntrinsicHeight(), Config.ARGB_8888);
        Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas mCanvas = new Canvas(mAlphaBitmap);
        Paint mPaint = new Paint();

        mPaint.setColor(mColor);
        //从原位图中提取只包含alpha的位图
        Bitmap alphaBitmap = mBitmap.extractAlpha();
        //在画布上（mAlphaBitmap）绘制alpha位图
        mCanvas.drawBitmap(alphaBitmap, 0, 0, mPaint);

        return mAlphaBitmap;
    }


    /**
     * Layout动画
     *
     * @return
     */
    public static LayoutAnimationController getAnimationController(Context context) {
        int duration = 1500;
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(duration);
        set.addAnimation(animation);
        Animation animation1 = AnimationUtils.loadAnimation(
                context, R.anim.popup_enter);
        set.addAnimation(animation1);

        LayoutAnimationController controller = new LayoutAnimationController(set, 0.1f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        return controller;
    }

    /**
     * Layout动画
     *
     * @return
     */
    public static void startLayoutAnimation(final Context context, final ArrayList<View> arrayList, final int i, final Handler handler) {
        if (!(i < arrayList.size())) {
            return;
        }
        final int duration = 800;
        final AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(duration / 16);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                arrayList.get(i).setAlpha(1.0f);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        set.addAnimation(animation);
//        final Animation animation1 = AnimationUtils.loadAnimation(
//                context, R.anim.popup_enter);
        final Animation animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 2f,
                Animation.RELATIVE_TO_SELF, 0f);
        animation1.setInterpolator(new DecelerateInterpolator());
        animation1.setDuration(duration / 10);
        set.addAnimation(animation1);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startLayoutAnimation(context, arrayList, i + 1, handler);
                Log.d("onAnimationEnd", "onAnimationEnd");
                float endY = arrayList.get(i).getY();
                float height = arrayList.get(i).getHeight();
                ValueAnimator makeBallLow = ObjectAnimator.ofFloat(arrayList.get(i), "scaleY",
                        1, 0.75f);
                makeBallLow.setDuration(duration / 4);
                makeBallLow.setRepeatCount(1);
                makeBallLow.setInterpolator(new DecelerateInterpolator());
                makeBallLow.setRepeatMode(ValueAnimator.REVERSE);
                ValueAnimator yWhenBallisPlan = ObjectAnimator.ofFloat(arrayList.get(i), "y", endY, endY - 0.3f * height * 0.5f);
                yWhenBallisPlan.setDuration(duration / 6);
                yWhenBallisPlan.setRepeatCount(1);
                yWhenBallisPlan.setInterpolator(new DecelerateInterpolator());
                yWhenBallisPlan.setRepeatMode(ValueAnimator.REVERSE);

                ValueAnimator yWhenBallisPlan2 = ObjectAnimator.ofFloat(arrayList.get(i), "y", endY, endY - 0.1f * height * 0.5f);
                yWhenBallisPlan2.setDuration(duration / 9);
                yWhenBallisPlan2.setRepeatCount(1);
                yWhenBallisPlan2.setInterpolator(new DecelerateInterpolator());
                yWhenBallisPlan2.setRepeatMode(ValueAnimator.REVERSE);

//                ValueAnimator yWhenBallisPlan = ObjectAnimator.ofFloat(arrayList.get(i), "y", endY, endY - 0.25f * height * 0.5f);
//                yWhenBallisPlan.setDuration(duration / 4);
//                yWhenBallisPlan.setRepeatCount(1);
//                yWhenBallisPlan.setInterpolator(new DecelerateInterpolator());
//                yWhenBallisPlan.setRepeatMode(ValueAnimator.REVERSE);
//
//                ValueAnimator makeBallLow3 = ObjectAnimator.ofFloat(arrayList.get(i), "scaleY",
//                        1, 0.5f);
//                makeBallLow3.setDuration(duration / 6);
//                makeBallLow3.setRepeatCount(1);
//                makeBallLow3.setInterpolator(new DecelerateInterpolator());
//                makeBallLow3.setRepeatMode(ValueAnimator.REVERSE);
//
//                ValueAnimator yWhenBallisPlan3 = ObjectAnimator.ofFloat(arrayList.get(i), "y", endY, endY - 0.5f * height * 0.5f);
//                yWhenBallisPlan3.setDuration(duration / 6);
//                yWhenBallisPlan3.setRepeatCount(1);
//                yWhenBallisPlan3.setInterpolator(new DecelerateInterpolator());
//                yWhenBallisPlan3.setRepeatMode(ValueAnimator.REVERSE);
//
//                ValueAnimator makeBallLow2 = ObjectAnimator.ofFloat(arrayList.get(i), "y", endY, endY + 0.75f * height * 0.5f);
//                makeBallLow2.setDuration(duration / 5);
//                makeBallLow2.setRepeatCount(1);
//                makeBallLow2.setInterpolator(new DecelerateInterpolator());
//                makeBallLow2.setRepeatMode(ValueAnimator.REVERSE);

                AnimatorSet animatorSet = new AnimatorSet();
//                AnimatorSet animatorSet1 = new AnimatorSet();
//                AnimatorSet animatorSet2 = new AnimatorSet();
//                animatorSet1.playTogether(yWhenBallisPlan3,makeBallLow3);
//                animatorSet2.playTogether(yWhenBallisPlan,makeBallLow);
//                animatorSet.play(animatorSet2).after(makeBallLow2);
//                animatorSet.play(animatorSet1).before(makeBallLow2);
                animatorSet.play(yWhenBallisPlan2).after(yWhenBallisPlan);
                animatorSet.start();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Message message = handler.obtainMessage();
        message.what = Constants.notifcationHandleCode.start_animation;
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("arrayList", arrayList);
        hashMap.put("i", i);
        hashMap.put("animation", set);
        message.obj = hashMap;
        // arrayList.get(i).startAnimation(set);
        handler.sendMessage(message);

    }

    /**
     * 我也不知道这是什么鬼
     */
    private static HashMap<String, TimeCount> transTimeCounts = new HashMap<String, TimeCount>();

    private static class TimeCount {
        private long startTime;
        private long endTime;

        public long getEndTime() {
            return endTime;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public TimeCount(long startTime) {
            this.startTime = startTime;
        }

        public long getCount() {
            return (this.endTime - this.startTime);
        }

    }

    public static void startTimeCount(String transaction) {
        TimeCount t = new TimeCount(new Date().getTime());

        synchronized (transTimeCounts) {
            if (!TextUtils.isEmpty(transaction)) {
                transTimeCounts.put(transaction, t);
            }
        }


    }

    public static long endTimeCount(String transaction) {
        long endTime = new Date().getTime();

        if (!TextUtils.isEmpty(transaction)) {
            TimeCount t = transTimeCounts.get(transaction);
            t.setEndTime(endTime);
            return t.getCount();
        }

        return 0;

    }

    public static void clearTimeCount() {
        synchronized (transTimeCounts) {
            transTimeCounts.clear();
        }
    }

    public static void printTimeCount() {

        Iterator<String> i = transTimeCounts.keySet().iterator();
        while (i.hasNext()) {
            String tran = i.next();
            Log.i(tran, "耗时:" + transTimeCounts.get(tran).getCount() + "ms");
        }

        clearTimeCount();

    }


    /**
     * 转换为Long类型,如果转换失败或者参数为空,则返回0
     *
     * @param o
     * @return
     */
    public static long parseLong(Object o) {
        return parseLong(o, 0l);
    }

    /**
     * 转换为Long类型,如果转换失败或者参数为空,则返回defaultValue
     *
     * @param o
     * @param defaultValue
     * @return
     */
    public static Long parseLong(Object o, Long defaultValue) {
        Long ret = defaultValue;

        try {
            if (o != null) {
                if (o instanceof Long) {
                    ret = (Long) o;
                } else if (o instanceof Integer) {
                    ret = ((Integer) o).longValue();
                } else if (o instanceof Double) {
                    ret = ((Double) o).longValue();
                } else if (o instanceof Float) {
                    ret = ((Float) o).longValue();
                } else if (o instanceof String) {
                    ret = Long.parseLong((String) o);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 转换为Integer类型,如果转换失败或者参数为空,则返回0
     *
     * @param o
     * @return
     */
    public static int parseInt(Object o) {
        return parseInt(o, 0);
    }

    /**
     * 转换为Integer类型,如果转换失败或者参数为空,则返回defaultInt
     *
     * @param o
     * @param defaultInt
     * @return
     */
    public static Integer parseInt(Object o, Integer defaultInt) {
        Integer ret = defaultInt;

        try {
            if (o != null) {
                if (o instanceof Long) {
                    ret = ((Long) o).intValue();
                } else if (o instanceof Integer) {
                    ret = (Integer) o;
                } else if (o instanceof Double) {
                    ret = ((Double) o).intValue();
                } else if (o instanceof Float) {
                    ret = ((Float) o).intValue();
                } else if (o instanceof String) {
                    ret = Integer.parseInt((String) o);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 转换为Double类型,如果转换失败或者参数为空,则返回0
     *
     * @param o
     * @return
     */
    public static double parseDouble(Object o) {
        return parseDouble(o, 0.0);
    }

    /**
     * 转换为Double类型,如果转换失败或者参数为空,则返回defaultValue
     *
     * @param o
     * @param defaultValue
     * @return
     */
    public static Double parseDouble(Object o, Double defaultValue) {
        Double ret = defaultValue;

        try {
            if (o != null) {
                if (o instanceof Double) {
                    ret = (Double) o;
                } else if (o instanceof Float) {
                    ret = ((Float) o).doubleValue();
                } else if (o instanceof Integer) {
                    ret = ((Integer) o).doubleValue();
                } else if (o instanceof Long) {
                    ret = ((Long) o).doubleValue();
                } else if (o instanceof String) {
                    ret = Double.parseDouble((String) o);
                } else {
                    ret = Double.parseDouble(parseString(o));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 转换为Float类型,如果转换失败或者参数为空,则返回0
     *
     * @param o
     * @return
     */
    public static float parseFloat(Object o) {
        return parseFloat(o, 0.0f);
    }

    /**
     * 转换为Float类型,如果转换失败或者参数为空,则返回defaultValue
     *
     * @param o
     * @param defaultValue
     * @return
     */
    public static Float parseFloat(Object o, Float defaultValue) {
        Float ret = defaultValue;

        try {
            if (o != null) {
                if (o instanceof Double) {
                    ret = ((Double) o).floatValue();
                } else if (o instanceof Float) {
                    ret = (Float) o;
                } else if (o instanceof Integer) {
                    ret = ((Integer) o).floatValue();
                } else if (o instanceof Long) {
                    ret = ((Long) o).floatValue();
                } else if (o instanceof String) {
                    ret = Float.parseFloat((String) o);
                } else {
                    ret = Float.parseFloat(parseString(o));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }


    public static boolean parseBoolean(Object o) {
        boolean ret = false;
        try {
            if (o instanceof Boolean) {
                ret = (Boolean) o;
            } else ret = Boolean.valueOf(parseString(o));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 转换为String类型,如果转换失败或者参数为空,则返回""
     *
     * @param o
     * @return
     */
    public static String parseString(Object o) {
        return parseString(o, "");
    }

    /**
     * 转换为String类型,如果转换失败或者参数为空,则返回defaultValue
     *
     * @param o
     * @param defaultValue
     * @return
     */
    public static String parseString(Object o, String defaultValue) {
        String ret = defaultValue;

        try {
            if (o != null) {
                if (o instanceof String) {
                    ret = (String) o;
                } else {
                    ret = String.valueOf(o);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 按照UTF-8格式转换字节流为字符串,转换失败返回""
     *
     * @param data
     * @return
     */
    public static String parseString(byte[] data) {
        return parseString(data, "UTF-8");
    }

    /**
     * 按照给定字符集格式转换字节流为字符串,转换失败返回""
     *
     * @param data
     * @param charset
     * @return
     */
    public static String parseString(byte[] data, String charset) {

        final String UTF8_BOM = "\uFEFF";

        if (TextUtils.isEmpty(charset)) charset = "UTF-8";

        try {
            String toReturn = (data == null) ? null : new String(data, charset);
            if (toReturn != null && toReturn.startsWith(UTF8_BOM)) {
                return toReturn.substring(1);
            }
            return toReturn;
        } catch (UnsupportedEncodingException e) {
            Log.e("Util", "Encoding response into string failed", e);
            return "";
        }
    }

    /**
     * 如果传入format则按format格式化,格式化失败返回空;
     * 未传入format,则按照精度由细到粗尝试格式化,格式化失败返回空
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date parseDate(String dateStr, SimpleDateFormat format) {
        Date date = null;
        if (isBlank(dateStr)) return null;

        if (format != null) {
            try {
                date = format.parse(dateStr);
                return date;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Util", dateStr + "日期转换错误.", e);
            }

        } else {
            try {
                date = Constants.format.yyyy_MM_dd_HH_mm_ss_SSSSSS.parse(dateStr);
                return date;
            } catch (Exception e) {
                //e.printStackTrace();
            }

            try {
                date = Constants.format.yyyy_MM_dd_T_HH_mm_ss_SSS.parse(dateStr);
                return date;
            } catch (Exception e) {
                //e.printStackTrace();
            }

            try {
                date = Constants.format.yyyy_MM_dd_HH_mm_ss.parse(dateStr);
                return date;
            } catch (Exception e) {
                //e.printStackTrace();
            }

            try {
                date = Constants.format.yyyy_MM_dd_T_HH_mm_ss.parse(dateStr);
                return date;
            } catch (Exception e) {
                //e.printStackTrace();

            }

            try {
                date = Constants.format.yyyy_MM_dd.parse(dateStr);
                return date;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Util", dateStr + "日期转换错误.", e);
            }

        }
        return null;
    }

    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, null);
    }

    /**
     * 金额转化为0.00
     *
     * @param value
     * @return
     */
    public static String formatMoney(Double value) {
        if (value != null) {
            if (value.doubleValue() != 0.00) {
                java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
                return df.format(value.doubleValue());
            } else {
                return "0.00";
            }
        }
        return "";
    }

    /**
     * 给金三位加一个逗号      eg:5,000.00
     *
     * @param data
     * @return
     */
    public static String formatToSepara(float data) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(data);
    }


    /**
     * 唤醒主线程
     */
    public static void notifyMainThread() {
        synchronized (Looper.getMainLooper().getThread()) {
            Looper.getMainLooper().getThread().notify();
        }
    }

    /**
     * 阻塞主线程
     */
    public static void waitMainThread(long time) {
        synchronized (Looper.getMainLooper().getThread()) {
            try {
                Looper.getMainLooper().getThread().wait(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 当前线程是否为主线程
     *
     * @return
     */
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /**
     * 运行在ui线程
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        ActivityManager.getInstance().getCurrentActivity().runOnUiThread(runnable);
    }

    /**
     * 保存图片到本地相册
     *
     * @param bitmap
     * @param activity
     */
    public static void saveLocalImage(final Bitmap bitmap, AppCompatActivity activity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(Environment.getExternalStorageDirectory() + "/");
                if (!file.exists()) {
                    file.mkdirs();
                }

                File imageFile = new File(file.getAbsolutePath(), new Date().getTime() + ".jpg");
                FileOutputStream outStream = null;
                try {
                    outStream = new FileOutputStream(imageFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                    ActivityManager.getInstance().getCurrentActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + imageFile.getAbsolutePath())));

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showShort(MainApplication.getApp().getString(R.string.save_picture_success));
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.gc();
                }
            }
        }).start();
    }

    public static final int duration = 800;

    public static LayoutAnimationController getTypeLayoutAnimationController() {
        AnimationSet animationSet = new AnimationSet(false);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        TranslateAnimation translateAnimation1 =
                new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 2f,
                        Animation.RELATIVE_TO_SELF, 0f);
        TranslateAnimation translateAnimation2 =
                new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, -0.15f);
        TranslateAnimation translateAnimation3 =
                new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, -0.05f);
        TranslateAnimation translateAnimation4 =
                new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0.15f);
        TranslateAnimation translateAnimation5 =
                new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0.05f);
//            ScaleAnimation scaleAnimation = new ScaleAnimation(0f,0f,0f,0.75f);

        alpha.setDuration(duration / 16);
        translateAnimation1.setDuration(duration / 10);
        translateAnimation1.setInterpolator(new DecelerateInterpolator());
//            scaleAnimation.setDuration(duration / 4);
//            scaleAnimation.setStartOffset(duration / 10);
//            scaleAnimation.setInterpolator(new DecelerateInterpolator());
//            scaleAnimation.setRepeatMode(Animation.REVERSE);
        translateAnimation2.setDuration(duration / 6);
        translateAnimation2.setStartOffset(duration / 10);
//            translateAnimation2.setRepeatCount(1);
        translateAnimation2.setInterpolator(new DecelerateInterpolator());
//            translateAnimation2.setRepeatMode(Animation.REVERSE);
        translateAnimation4.setDuration(duration / 6);
        translateAnimation4.setStartOffset(duration / 10 + duration / 6);
        translateAnimation4.setInterpolator(new DecelerateInterpolator());
        translateAnimation3.setDuration(duration / 9);
        translateAnimation3.setStartOffset(duration / 10 + duration / 3);
//            translateAnimation3.setRepeatCount(1);
        translateAnimation3.setInterpolator(new DecelerateInterpolator());
//            translateAnimation3.setRepeatMode(Animation.REVERSE);
        translateAnimation5.setDuration(duration / 9);
        translateAnimation5.setStartOffset(duration / 10 + duration / 3 + duration / 9);
//            translateAnimation3.setRepeatCount(1);
        translateAnimation5.setInterpolator(new DecelerateInterpolator());
//            translateAnimation3.setRepeatMode(Animation.REVERSE);
//            translateAnimation4.setDuration(25);

//            translateAnimation3.setStartOffset(300);
//            translateAnimation4.setStartOffset(350);
        animationSet.addAnimation(alpha);
        animationSet.addAnimation(translateAnimation1);
        animationSet.addAnimation(translateAnimation2);
        animationSet.addAnimation(translateAnimation3);
        animationSet.addAnimation(translateAnimation4);
        animationSet.addAnimation(translateAnimation5);
//            animationSet.addAnimation(translateAnimation4);
        LayoutAnimationController lac = new LayoutAnimationController(animationSet);
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        return lac;
    }

    public static boolean isGeneralFile(String type) {

//        if (type.equalsIgnoreCase("jpg") || type.equalsIgnoreCase("png") || type.equalsIgnoreCase("bmp") || type.equalsIgnoreCase("jpeg")) {
//            return true;
//        } else
        if (type.equalsIgnoreCase("doc") || type.equalsIgnoreCase("docx") || type.equalsIgnoreCase("xls") || type.equalsIgnoreCase("xlsx") || type.equalsIgnoreCase("pdf")) {
            return true;
        }
        return false;

    }

    public static int getFileType(String type) {
        if (type.equalsIgnoreCase("doc") || type.equalsIgnoreCase("docx")) {
            return Constants.AsyType.TYPE_WORD;
        } else if (type.equalsIgnoreCase("xls") || type.equalsIgnoreCase("xlsx")) {
            return Constants.AsyType.TYPE_XLS;
        } else if (type.equalsIgnoreCase("pdf")) {
            return Constants.AsyType.TYPE_PDF;
        }
        return 0;
    }


}
