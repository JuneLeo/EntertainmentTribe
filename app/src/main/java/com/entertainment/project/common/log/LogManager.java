package com.entertainment.project.common.log;

import android.content.Context;


import com.entertainment.project.common.Constants;
import com.entertainment.project.common.utils.Utils;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by lc on 16/4/1.
 */
public class LogManager {

    private Context context;
    /**
     * 是否只在wifi情况下上传
     */
    private static boolean isOnlyWifiUpload = true;

    private HashMap<String,BaseLog> logs = new HashMap<String, BaseLog>();
    /**
     * LogManager实例
     */
    private static LogManager INSTANCE;
    /**
     * 保证只有一个LogManager实例
     */
    private LogManager() {
    }

    /**
     * 获取LogManager实例 ,单例模式
     */
    public static LogManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new LogManager();
        return INSTANCE;
    }

    public void init(Context context){
        this.context = context;
    }

    /**
     * 设置文件名
     * @param tag
     * @return
     */
    private BaseLog setFileName(String tag){
        BaseLog baseLog = new BaseLog();
        baseLog.setFileName(tag);
        logs.put(tag, baseLog);
        return baseLog;
    }

    /**
     * 初始化event
     * @param logEvent
     */
    public void valueEvent(LogEvent logEvent){
        logEvent.setTime(Constants.format.yyyy_MM_dd_HH_mm_ss_SSSSSS.format(new Date()));
//        logEvent.setUser_id(Constants.getCurrentUser(context).getUserId());
        logEvent.setOs("Android");
//        logEvent.setVersion(ExampleUtil.GetVersion(context));
    }

    /**
     * 日志记录通用
     * @param tag
     * @param content
     */
    public void log(String tag,String content){
        LogEvent logEvent = new LogEvent();
        valueEvent(logEvent);
        logEvent.setContent(content);
        logEvent(tag, logEvent);
    }

    /**
     * 性能日志记录
     * @param action
     * @param ms
     */
    public void logPerformance(String action,long ms){
        PerformanceLogEvent performanceLogEvent = new PerformanceLogEvent();
        valueEvent(performanceLogEvent);
        performanceLogEvent.setAction(action);
        performanceLogEvent.setMs(ms);
        logEvent(PerformanceLog.fileName, performanceLogEvent);
    }

    /**
     * 行为日志记录
     * @param behavior
     */
    public void logBehavior(String behavior){
        BehaviorLogEvent behaviorLogEvent = new BehaviorLogEvent();
        valueEvent((behaviorLogEvent));
        behaviorLogEvent.setBehavior(behavior);
        logEvent(BehaviorLog.fileName,behaviorLogEvent);
    }

    /**
     * 写入文件
     * @param tag
     * @param logEvent
     */
    private void logEvent(String tag,LogEvent logEvent){
        if(logs.containsKey(tag)) {
            logs.get(tag).writeToFile(logEvent);
        }else{
            setFileName(tag).writeToFile(logEvent);
        }
    }

    /**
     * 上传行为日志
     * @param context
     */
    public static void uploadBehaviorLog(Context context){
        uploadLogs(context, BehaviorLog.fileName);
    }

    /**
     * 上传性能日志
     * @param context
     */
    public static void uploadPerformanceLog(Context context){
        uploadLogs(context, PerformanceLog.fileName);
    }

    /**
     * 上传错误日志
     * @param context
     */
    public static void uploadErrorLog(Context context){
        uploadLogs(context, "error");
    }


    public static void uploadLogs(final Context context, final String tag){
        if(!isOnlyWifiUpload || Utils.isNetworkConnected(context) && Constants.NetStatus.WIFI.equals(Constants.netStatus)) {
//            FileUploadUtil.uploadFilesByContains(context, Environment.getExternalStorageDirectory() + File.separator + Constants.localLogDir, tag, Constants.url.UPLOAD_LOG, Constants.format.yyyyMMdd.format(new Date()),true);
        }
    }
}
