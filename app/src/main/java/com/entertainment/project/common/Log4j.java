package com.entertainment.project.common;

import org.apache.log4j.Logger;

/**
 * Created by wanghongliang on 15/7/2.
 */
public class Log4j {

    public static void debug(Class<?> clazz, Object obj) {
        if(Logger.getLogger(clazz) != null)
        Logger.getLogger(clazz).debug(obj);
    }
    public static void info(Class<?> clazz, Object obj) {
        if(Logger.getLogger(clazz) != null)
        Logger.getLogger(clazz).info(obj);
    }
    public static void warn(Class<?> clazz, Object obj) {
        if(Logger.getLogger(clazz) != null)
        Logger.getLogger(clazz).warn(obj);
    }
    public static void error(Class<?> clazz, Object obj) {
        if(Logger.getLogger(clazz) != null)
        Logger.getLogger(clazz).error(obj);
    }
    public static void fatal(Class<?> clazz, Object obj) {
        if(Logger.getLogger(clazz) != null)
        Logger.getLogger(clazz).fatal(obj);
    }
    public static void debug(String logName, Object obj) {
        if(Logger.getLogger(logName) != null)
        Logger.getLogger(logName).debug(obj);
    }
    public static void info(String logName, Object obj) {
        if(Logger.getLogger(logName) != null)
        Logger.getLogger(logName).info(obj);
    }
    public static void warn(String logName, Object obj) {
        if(Logger.getLogger(logName) != null)
        Logger.getLogger(logName).warn(obj);
    }
    public static void error(String logName, Object obj) {
        if(Logger.getLogger(logName) != null)
        Logger.getLogger(logName).error(obj);
    }
    public static void fatal(String logName, Object obj) {
        if(Logger.getLogger(logName) != null)
        Logger.getLogger(logName).fatal(obj);
    }
    public static void debug(String logName,Object obj, Throwable r) {
        if(Logger.getLogger(logName) != null)
        Logger.getLogger(logName).debug(obj,r);
    }
    public static void info(String logName,Object obj,  Throwable r) {
        if(Logger.getLogger(logName) != null)
        Logger.getLogger(logName).info(obj,r);
    }
    public static void warn(String logName, Object obj, Throwable r) {
        if(Logger.getLogger(logName) != null)
        Logger.getLogger(logName).warn(obj,r);
    }
    public static void error(String logName,Object obj,  Throwable r) {
        if(Logger.getLogger(logName) != null)
        Logger.getLogger(logName).error(obj,r);
    }
    public static void fatal(String logName, Object obj, Throwable r) {
        if(Logger.getLogger(logName) != null)
        Logger.getLogger(logName).fatal(obj,r);
    }
}
