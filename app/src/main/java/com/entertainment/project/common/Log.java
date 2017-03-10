package com.entertainment.project.common;


import com.entertainment.project.BuildConfig;
import com.entertainment.project.common.log.LogManager;

public class Log {

    private static final boolean DEBUG = BuildConfig.DEBUG;

    public static void v(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.v(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.d(tag, msg);
        } else {
            Log4j.debug(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.d(tag, msg, tr);
        } else {
            Log4j.debug(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.i(tag, msg);
        } else {
            Log4j.info(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.i(tag, msg, tr);
        } else {
            Log4j.info(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.w(tag, msg);
        } else {
            Log4j.warn(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.w(tag, msg, tr);
        } else {
            Log4j.warn(tag, msg, tr);
        }
    }

    public static void w(String tag, Throwable tr) {
        if (DEBUG) {
            android.util.Log.w(tag, tr);
        } else {
            Log4j.warn(tag, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.e(tag, msg);
        } else {
            Log4j.error(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.e(tag, msg, tr);
        } else {
            Log4j.error(tag, msg, tr);
        }
    }

    public static void e(String tag,Throwable tr) {
        if (DEBUG) {
            android.util.Log.e(tag, "", tr);
        } else {
            Log4j.error(tag, "", tr);
        }
    }

    /**
     * 自定义log通用
     * @param fileName
     * @param content
     */
    public static void logCommon(String fileName, String content) {
        if (DEBUG) {
            android.util.Log.d(fileName, content);
            LogManager.getInstance().log(fileName, content);
        } else {
            LogManager.getInstance().log(fileName, content);
        }
    }

    /**
     * 性能日志
     * @param content
     */
    public static void logPerformance(String content,long l) {
        if (DEBUG) {
//            android.util.Log.d(fileName, content);
            LogManager.getInstance().logPerformance(content, l);
        } else {
            LogManager.getInstance().logPerformance(content, l);
        }
    }

    /**
     * 行为日志
     * @param action
     */
    public static void logBehavior(String action) {
        if (DEBUG) {
//            android.util.Log.d(fileName, content);
            LogManager.getInstance().logBehavior(action);
        } else {
            LogManager.getInstance().logBehavior(action);
        }
    }


}