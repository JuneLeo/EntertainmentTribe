package com.entertainment.project.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import com.entertainment.project.common.assist.Devices;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Sick on 2016/10/27.
 */
public class SDCardUtil {

    /**
     * 获得SD卡总大小
     *
     * @return
     */
    public static String getSDTotalSize(Context context) {
        try {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            return Formatter.formatFileSize(context, blockSize * totalBlocks);
        } catch (Throwable e) {
            return Formatter.formatFileSize(context, 0);
        }
    }

    /**
     * 获得sd卡剩余容量，即可用大小
     *
     * @return
     */
    public static String getSDAvailableSize(Context context) {
        try {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return Formatter.formatFileSize(context, blockSize * availableBlocks);
        } catch (Throwable e) {
            return Formatter.formatFileSize(context, 0);
        }
    }

    /**
     * 获得机身内存总大小
     *
     * @return
     */
    public static String getRomTotalSize(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;

        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }

            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();

        } catch (IOException e) {
        } catch (Throwable e) {

        }
        return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }

    /**
     * 获得机身可用内存
     *
     * @return
     */
    public static String getRomAvailableSize(Context context) {
        ActivityManager activityManager = (ActivityManager) (context.getSystemService(Activity.ACTIVITY_SERVICE));
        //获得MemoryInfo对象
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        //获得系统可用内存，保存在MemoryInfo对象上
        activityManager.getMemoryInfo(memoryInfo);
        long memSize = memoryInfo.availMem;
        //字符类型转换
        return Formatter.formatFileSize(context, memSize);
    }

    public static Devices getDevice(Context context) {
        Devices devices = new Devices();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        devices.setDeviceID(tm.getDeviceId());
        devices.setDeviceInfo("");
        devices.setDevType(Build.MODEL);
        devices.setManufacturer(Build.MANUFACTURER);
        devices.setOsType("android");
        devices.setOsVersion(Build.VERSION.RELEASE);
        devices.setRegisterID("");
        return devices;
    }
}
