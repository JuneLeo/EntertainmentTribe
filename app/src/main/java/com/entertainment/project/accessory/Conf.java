package com.entertainment.project.accessory;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

/**
 * Created by Sick on 2016/11/15.
 */
public class Conf {

    public static class accessType {
        public static final int TYPE_HEADER = 1;
        public static final int TYPE_DATA = 2;

        public static final int TYPE_TITLE_ATTACHMENT = 3;  // 电子发票标题

        public static final int TYPE_WORD_ALL = 4;  //word xls pdf
        public static final int TYPE_WORD_AND_IMG = 5;// word xls pdf img
        public static final int TYPE_PICTURE = 6; //图片
        public static final int TYPE_WORD = 7;    //文档
        public static final int TYPE_XLS = 8;
        public static final int TYPE_PDF = 9;

        public static final int TYPE_TITLE_INVOICE = 10;  //invoice qrcodeinvoice
        public static final int TYPE_INVOICE = 11;
        public static final int TYPE_QRCODE_INVOICE = 12;

        public static final int TYPE_TITLE_ALL = 13;

    }

    public enum BillType {
        MISSION, EXPENSE
    }


    public static class accssState {

        public static final int UNUP_LOAD = 1;         //未上传
        public static final int UP_LOADING = 2;        //正在上传
        public static final int UP_LOADED_SUCCESS = 3; //上传成功
        public static final int UP_LOADED_FAILURE = 4; //上传失败

        public static final int DOWN_LOADED = 11; //已下载

        public static final int UNDOWN_LOAD = 5;        //未下载
        public static final int DOWN_LOADING = 6;        //正在下载
        public static final int DOWN_LOAD_SUCCESS = 7;   //下载成功
        public static final int DOWN_LOADED_FAILURE = 8;   //下载失败

        public static final int UP_LOADED_FAILURE_IS_USED = 9;  //已使用
        public static final int UP_LOADED_FAILURE_NO_IDENTIFY = 10; //无法识别
    }

    public static class accssStateCH {
        public static final String UNUP_LOAD = "未上传";         //未上传
        public static final String UP_LOADING = "正在上传";        //正在上传
        public static final String UP_LOADED_SUCCESS = "上传成功"; //上传成功
        public static final String UP_LOADED_FAILURE = "重新上传"; //上传失败

        public static final String UNDOWN_LOAD = "未下载";        //未下载
        public static final String DOWN_LOADING = "正在下载";        //正在下载
        public static final String DOWN_LOAD_SUCCESS = "下载成功";   //下载成功
        public static final String DOWN_LOADED_FAILURE = "重新下载";   //下载失败

        public static final String UP_LOADED_FAILURE_IS_USED = "已使用";  //已使用
        public static final String UP_LOADED_FAILURE_NO_IDENTIFY = "无法识别"; //无法识别
        public static final String NO_DISPLAY = "";//已下载
        public static final String UP_LOADING_IS_IDENTIFY = "正在识别中";//正在识别中
        public static final String UP_LOADED_IDENTIFY_SUCCESS = "识别成功";//识别
    }

    /**
     * 通过文件名 获取文件类型
     *
     * @param fileName
     * @return
     */
    public static int getAccessoryType(String fileName) {
        if (fileName.lastIndexOf(".") != -1) {
            String fileTypes = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (fileTypes.equalsIgnoreCase("jpg") || fileTypes.equalsIgnoreCase("bmp") || fileTypes.equalsIgnoreCase("png") || fileTypes.equalsIgnoreCase("jpeg")) {
                return accessType.TYPE_PICTURE;
            } else if (fileTypes.equalsIgnoreCase("DOC") || fileTypes.equalsIgnoreCase("DOCX")) {
                return accessType.TYPE_WORD;
            } else if (fileTypes.equalsIgnoreCase("XLS") || fileTypes.equalsIgnoreCase("XLSX")) {
                return accessType.TYPE_XLS;
            } else if (fileTypes.equalsIgnoreCase("PDF")) {
                return accessType.TYPE_PDF;
            }
        }
        return 0;
    }

    /**
     * 转化文件大小
     *
     * @param size
     * @return
     */
    public static String formatFileSize(long size) {//转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String result = "";
        if (size < 1024) {
            result = df.format((double) size) + "B";
        } else if (size < 1048576) {
            result = df.format((double) size / 1024) + "K";
        } else if (size < 1073741824) {
            result = df.format((double) size / 1048576) + "M";
        } else {
            result = df.format((double) size / 1073741824) + "G";
        }
        return result;
    }

    /**
     * 校验为空和是否含有键值
     *
     * @param hashMap
     * @param key
     * @return
     */
    public static boolean isContainsKey(HashMap hashMap, String key) {
        if (hashMap.containsKey(key) && null != hashMap.get(key)) {
            return true;
        }
        return false;
    }

    public static String formatProgress(Double progress) {
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(0);
        return nt.format(progress);
    }

    public static class state {
        public static final int EXPENSE_SAVE_CLICK = 1;
        public static final int EXPENSE_AGAIN_CLICK = 2;
        public static final int EXPENSE_INVOICE_CLICK = 3;

        public static final int EXPENSE_SUCCESS = 1;
        public static final int EXPENSE_FAILURE = 2;
    }
}
