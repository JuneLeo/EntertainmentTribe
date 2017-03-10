package com.entertainment.project.common;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by Sick on 2016/10/27.
 */
public class Constants {
    //数据库名
    public static final String dbName = "EntertainmentTribe";
    //倒计时
    public static int codeDownTime = 60;
    //公钥
    public static String publicKey;
    //是否回到主界面
    public static boolean isHomeed = false;

    /**
     * 程序启动是否完成
     */
    public static boolean isStartupCompleted = false;


    /**
     * 发布版本   开发版 或者 发布版
     */
    public static String releaseType = ReleaseType.Test;

    /**
     * 版本类型
     */
    public static class ReleaseType {
        public static final String Product = "Product";
        public static final String Test = "Test";
    }

    /**
     * 处理消息handler
     */
    public static final class notifcationHandleCode {
        public static final int receive = 0;
        public static final int open = 1;
        public static final int examine_cancel = 3;
        public static final int message_cancel = 4;
        public static final int message_spread_enable = 5;
        public static final int message_spread_unenable = 6;
        public static final int down_time = 7;
        public static final int code_down_time = 8;
        public static final int upload_image = 9;
        public static final int upload_loop = 10;
        public static final int change_company_name = 11;

        public static final int start_animation = 99;
    }


    public static final class format {
        public static final SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日", Locale.CHINA);

        /**
         * yyyy_MM_dd_week : yyyy年MM月dd日 E
         */
        public static final SimpleDateFormat yyyy_MM_dd_week = new SimpleDateFormat("yyyy年MM月dd日 E", Locale.CHINA);
        public static final SimpleDateFormat yyyy_MM_dd_week2 = new SimpleDateFormat("yyyy-MM-dd E", Locale.CHINA);
        public static final SimpleDateFormat MM_dd_week = new SimpleDateFormat("MM-dd E", Locale.CHINA);
        public static final SimpleDateFormat yyyy_MM_dd_week3 = new SimpleDateFormat("yyyy-MM-dd EEEE", Locale.CHINA);
        public static final SimpleDateFormat yyyy_MM_dd_week4 = new SimpleDateFormat("yyyy年MM月dd日 EEEE", Locale.CHINA);

        /**
         * yyyy_MM_dd : yyyy_MM_dd
         */
        public static final SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        public static final SimpleDateFormat yyyy_MM_dd2 = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);

        public static final SimpleDateFormat HH_mm = new SimpleDateFormat("HH:mm", Locale.CHINA);
        public static final SimpleDateFormat mm_ss = new SimpleDateFormat("mm:ss", Locale.CHINA);
        public static final SimpleDateFormat yyyy_MM_dd_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        public static final SimpleDateFormat yyyy_MM_dd_HH_mm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        public static final SimpleDateFormat yyyy_MM_dd_HH_mm_ss_SSSSSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.CHINA);
        public static final SimpleDateFormat yyyyMMddHHmmssSSSSSS = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS", Locale.CHINA);
        public static final SimpleDateFormat yyyy_MM_dd_T_HH_mm_ss_SSS = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.CHINA);
        public static final SimpleDateFormat yyyy_MM_dd_T_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CHINA);
        public static final SimpleDateFormat yyyy_MM_dd_HH_mm_a = new SimpleDateFormat("yyyy-MM-dd  hh:mma", Locale.CHINA);
        public static final SimpleDateFormat MM_dd_HH_mm_a = new SimpleDateFormat("MM-dd  hh:mma", Locale.CHINA);
        public static final SimpleDateFormat MM_dd_HH_mm_a2 = new SimpleDateFormat("MM月dd日 hh:mm a", Locale.CHINA);
        public static final SimpleDateFormat MM_dd = new SimpleDateFormat("MM月dd日", Locale.CHINA);
        public static final SimpleDateFormat mm_dd = new SimpleDateFormat("MM-dd", Locale.CHINA);
        public static final SimpleDateFormat MM = new SimpleDateFormat("MM月", Locale.CHINA);
        public static final SimpleDateFormat dd = new SimpleDateFormat("dd日", Locale.CHINA);
        public static final SimpleDateFormat MM_dd_hh_mm = new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);
        public static final SimpleDateFormat MM_dd_hh_mm_a = new SimpleDateFormat("MM-dd  HH:mm", Locale.US);
        public static final SimpleDateFormat a = new SimpleDateFormat("a", Locale.ENGLISH);
        public static final SimpleDateFormat MM_dd_w = new SimpleDateFormat("MM月dd日 EEEE", Locale.CHINA);
        public static final SimpleDateFormat w = new SimpleDateFormat("EEEE", Locale.CHINA);
        public static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        public static final SimpleDateFormat yyyy_MM_dd__HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss", Locale.CHINA);


        public static final DecimalFormat df_0 = new DecimalFormat("0");
        public static final DecimalFormat df_01 = new DecimalFormat("0.0");
        public static final DecimalFormat df_00 = new DecimalFormat("0.00");
        public static final DecimalFormat df_03 = new DecimalFormat("0.000");
        public static final DecimalFormat df_04 = new DecimalFormat("0.0000");

    }

    /**
     * request code
     */
    public static final class requestCode {

    }

    /**
     * result code
     */
    public static final class resultCode {

    }

    /**
     *
     */
    public static class url {
        public static String BASE_HUMOR_URL = "http://www.qiushibaike.com/";
        //客户端url
        public static String BASE_URL_SSO = "";
        //交易端url
        public static String BASE_URL_TRAVEL = "";
        //更新版本url
        public static String VERSION_UPDATE_URL = "";

        public static void initUrl(String sso, String travel, String travel_paycallback, String version_update_url, String update_url, String base_update_url) {
            BASE_URL_SSO = TextUtils.isEmpty(sso) ? "http://testsso.wecaiwu.com/sso" : "http://" + sso + "/sso";
            BASE_URL_TRAVEL = TextUtils.isEmpty(travel) ? "http://testtrade.wecaiwu.com" : "http://" + travel;
            VERSION_UPDATE_URL = TextUtils.isEmpty(version_update_url) ? "http://test.wecaiwu.com" + "/checkversion/" : version_update_url;


            initUrl();

        }

        public static void initUrl(String releaseType) {
            if (ReleaseType.Product.equalsIgnoreCase(releaseType)) {
                BASE_URL_SSO = "https://sso.wecaiwu.com/sso/";
                BASE_URL_TRAVEL = "";
                VERSION_UPDATE_URL = "";

            } else if (ReleaseType.Test.equalsIgnoreCase(releaseType)) {
                BASE_URL_SSO = "https://sso.wecaiwu.com/sso/";
                BASE_URL_TRAVEL = "";
                VERSION_UPDATE_URL = "";

            }
            initUrl();
        }

        private static void initUrl() {
            USE_LOGIN_URL = BASE_URL_SSO + "login";
        }

        /**
         * 用户登录url
         */
        public static String USE_LOGIN_URL = "";


    }

    /**
     * 校验
     */
    public static class pattern {
        //mobile
        public static Pattern patternMobile = Pattern.compile("^1[34758][0-9]\\d{8}$");
        // 86 mobile
        public static Pattern patternMobile86 = Pattern.compile("^((\\+?86)|(\\(\\+86\\)))?1\\d{10}$");
        // email
        public static Pattern patternEmail = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        // phone
        public static Pattern patternPhone = Pattern.compile("^\\d{3,4}-\\d{7,8}$|^\\d{10,12}$|^\\d{7,8}$");//[\d{3}-|\d{4}-]*\d{8}

        public static Pattern patternIdentification = Pattern.compile("(^\\d{17}[0-9X]$)");
        //    public static Pattern patternIdentification = Pattern.compile("(^\\d{14}[0-9X]$)|(^\\d{17}[0-9X]$)");
        //15到19位数字
        public static Pattern patternBankCard = Pattern.compile("(^\\d{14,19}$)");
        //6位数字
        public static Pattern patternCode = Pattern.compile("(^\\d{4}$)");
        //6位数字
        public static Pattern patternNum = Pattern.compile("^[0-9]*$");
        //3位数字
        public static Pattern patternCVN2 = Pattern.compile("(^\\d{3}$)");
        //汉字和字母
        public static Pattern patternName = Pattern.compile("^[a-zA-Z\\u4e00-\\u9fa5]{1,20}$");
//    public static Pattern patternPhone = Pattern.compile("\\d{3}-\\d{8}|\\d{4}-\\d{8}|\\d{8}");//[\d{3}-|\d{4}-]*\d{8}
    }

    /**
     * 本地文件夹
     */
    public static class LocalFileDIR {

        public static final String BASE_PHONE_DIR = Environment.getExternalStorageDirectory() + File.separator;

        //常量
        public static final String BASE_LOCAL_DIR = "Entertainment";

        public static final String LOCAL_CRASH_LOG_DIR = "Entertainment" + "/" + "Crash";  //error file
        public static final String LOCAL_LOG_DIR = "Entertainment" + "/" + "Logs";   //log file
        public static final String LOCAL_FILE_DIR = "Entertainment" + "/" + "File";  //attachement file
        public static final String LOCAL_PICTURE_DIR = "Entertainment" + "/" + "picture"; // Picture file
        public static final String LOCAL_TEMP_DIR = "Entertainment" + "/" + "temp";                     // temp file
        public static final String LOCAL_TEMP_PICTURE_DIR = "Entertainment.jpg";


    }

    /**
     * 网络连接状态
     */
    public static String netStatus = NetStatus.NONE;

    public static final class NetStatus {
        public static final String WIFI = "wifi";
        public static final String MOBILE = "mobile";
        public static final String NONE = "none";
    }

    /**
     * 新版本
     */
    public static final class NewVersion {
        public static final String VERSION_COMMENT = "version_comment";
        public static final String VERSION_URL = "version_url";
        public static final String VERSION_NEW_VERSION = "version_new_version";
        public static final String VERSION_NEED_UPDATE = "version_need_update";
        public static final String VERSION_FORCE_UPDATE = "version_force_update";
    }

    public static final class AsyType {
        public static final int TYPE_PICTURE = 1; //图片
        public static final int TYPE_WORD = 2;    //文档
        public static final int TYPE_XLS = 3;
        public static final int TYPE_PDF = 4;
    }

    /**
     * 风和天气key
     */
    public static final String WEATHER_KEY = "5c93c2b76d8342558f1a66a637fb4077";

    /**
     * 风和天气状态码
     */
    public static class WeatherStatus {
        public static final String NORMAL_STATUS = "ok";
        public static final String NO_MORE_REQUESTS_STATUS = "no more requests";
        public static final String UNKNOWN_CITY_STATUS = "unknown city";
    }

    /**
     * modelType
     * 特别复杂时可以再次自定义
     */
    public static class ModelType {
        //第一种
        public static final int TYPE_MODEL_HEADER = 1;
        public static final int TYPE_MODEL_DATA_1 = 2;
        public static final int TYPE_MODEL_DATA_2 = 3;
        //第二种
        public static final int TYPE_MODEL_ONE = 1;
        public static final int TYPE_MODEL_TWO = 2;
        public static final int TYPE_MODEL_THREE = 3;

    }

}
