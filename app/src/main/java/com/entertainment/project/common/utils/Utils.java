package com.entertainment.project.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.entertainment.project.common.Constants;
import com.entertainment.project.common.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Sick on 2016/10/27.
 */
public class Utils {
    /**
     * 网络请求中  new String（responseBody）对这个数据进行处理
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);

                        switch (aChar) {

                            case '0':

                            case '1':

                            case '2':

                            case '3':

                            case '4':

                            case '5':

                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed \\uxxxx encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';

                    else if (aChar == 'n')

                        aChar = '\n';

                    else if (aChar == 'f')

                        aChar = '\f';

                    outBuffer.append(aChar);

                }

            } else

                outBuffer.append(aChar);

        }

        return outBuffer.toString();

    }


    public static boolean isNetworkConnected(Context context){
        boolean isWifiConnected = false;
        boolean isMobileConnected = false;

        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(networkInfo != null)
            isWifiConnected = networkInfo.isConnected();

        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if(networkInfo != null)
            isMobileConnected = networkInfo.isConnected();

        if(isWifiConnected){
            Constants.netStatus = Constants.NetStatus.WIFI;
        }else if(isMobileConnected){
            Constants.netStatus = Constants.NetStatus.MOBILE;
        }else{
            Constants.netStatus = Constants.NetStatus.NONE;
        }

        Log.d("network status", "wifi == " + isWifiConnected + " and mobile == " + isMobileConnected);
        return isMobileConnected || isWifiConnected;
    }



    /**
     * 设置系统字体，在基本的activity中、基本的fragment中、基本的Dialog中
     */
    private static String typeName;
    private static Typeface typeface;
    public static void applyFont(final Context context, final View root, final String fontName) {
//        if(TextUtils.isEmpty(fontName)){
//            typeName = fontName;
//        }

        if(typeName == null || !typeName.equals(fontName)) {
            typeName = fontName;
            typeface = Typeface.createFromAsset(context.getAssets(), fontName);
        }
        try {
            if (root instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) root;
                for (int i = 0; i < viewGroup.getChildCount(); i++)
                    applyFont(context, viewGroup.getChildAt(i), fontName);
            } else if (root instanceof TextView)
                ((TextView) root).setTypeface(typeface);
        } catch (Exception e) {
            Log.e("applyFont", String.format("Error occured when trying to apply %s font for %s view", fontName, root));
            e.printStackTrace();
        }
    }

    /**
     * 深度克隆对象,对象及其属性必须实现序列化接口
     * @param srcObj
     * @return
     */
    public static Object deepClone(Object srcObj){
        ByteArrayInputStream inputStream=null;
        ByteArrayOutputStream outputStream=null;
        ObjectInputStream objectInputStream=null;
        ObjectOutputStream objectOutputStream=null;
        Object targetObj=null;
        try {
            outputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(srcObj);

            inputStream=new ByteArrayInputStream(outputStream.toByteArray());
            objectInputStream=new ObjectInputStream(inputStream);
            targetObj=objectInputStream.readObject();

        }catch (IOException e){
            throw new RuntimeException(e);
        }catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }finally {
            try {
                if (objectInputStream != null) objectInputStream.close();
                if (objectOutputStream != null) objectOutputStream.close();
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            }catch (IOException e){

            }catch (Exception e){

            }
        }

        return targetObj;
    }

    public static  void reCreateMap(HashMap<String, Object> map) {
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            Object val = entry.getValue();
            Log.d("key", key);
            if (val instanceof HashMap) {
                HashMap<String, Object> m = (HashMap<String, Object>) val;
                Object idObj = m.get("id");
//                Integer id = (Inteer) m.get("id");
                Log.d("key", key);
                if (idObj != null) {
                    Log.d("id", idObj + "");
                    map.put(key, idObj);
                } else {
                    Log.d("code", m.get("code") + "");
                    map.put(key, m.get("code"));
                }

            } else if (val instanceof List) {
                List list = (List) val;
                int count = list.size();
                for (int i=0;i<count;i++) {
                    Object o = list.get(i);
                    if (o instanceof HashMap) {
                        HashMap<String ,Object> map1 = (HashMap<String, Object>) o;
                        reCreateMap(map1);
                    }

                }
            }
        }
    }



    // 检测启动意图是否存在
    public static boolean isIntentExisting(Context context,String action){
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(action);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
        if(resolveInfos.size() > 0) return true;

        return false;
    }

    /**
     * 计算飞机到达日期 (前提条件 不超过24h)
     * @param depart 出发时间
     * @param arrive 到达时间
     * @param startDate 出发日期
     * @return 到达日期
     */
    public static String parseDate(String depart,String arrive,String startDate){
        try {
            Date startTime = Constants.format.HH_mm.parse(depart);
            Date endTime = Constants.format.HH_mm.parse(arrive);

            long startTimeLong = startTime.getTime();
            long endTimeLong = endTime.getTime();

            long timeLong = endTimeLong - startTimeLong;
            if (timeLong < 0) {
                Date endDate = null;

                endDate = Constants.format.yyyy_MM_dd.parse(startDate);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(endDate);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);

                return Constants.format.yyyy_MM_dd.format(calendar.getTime());
            } else {
                return startDate;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取两个日期之间的间隔天数
     * @return
     */
    public static int getDayCount(String startDate, String endDate, SimpleDateFormat format){
        try {
            return getGapCount(format.parse(startDate),format.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
    /**
     * 获取两个日期之间的间隔天数
     * @return
     */
    public static int getDayCount(Date startDate, String endDate, SimpleDateFormat format){
        try {
            return getGapCount(startDate,format.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取两个日期之间的间隔天数
     * @return
     */
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }


    /**
     * 方法2：检查表中某列是否存在
     * @param db
     * @param tableName 表名
     * @param columnName 列名
     * @return
     */
    public static boolean checkColumnExists(SQLiteDatabase db, String tableName
            , String columnName) {
        boolean result = false ;
        Cursor cursor = null ;

        try{
            cursor = db.rawQuery( "select * from sqlite_master where name = ? and sql like ?"
                    , new String[]{tableName , "%" + columnName + "%"} );
            result = null != cursor && cursor.moveToFirst() ;
        }catch (Exception e){
            Log.e("Utils","checkColumnExists2..." + e.getMessage()) ;
        }finally{
            if(null != cursor && !cursor.isClosed()){
                cursor.close() ;
            }
        }

        return result ;
    }

    public static List<String> getTagsList(String originalText) {
        if (originalText == null || originalText.equals("")) {
            return null;
        }
        List<String> tags = new ArrayList<String>();
        int indexOfComma = originalText.indexOf(',');
        String tag;
        while (indexOfComma != -1) {
            tag = originalText.substring(0, indexOfComma);
            tags.add(tag);

            originalText = originalText.substring(indexOfComma + 1);
            indexOfComma = originalText.indexOf(',');
        }

        tags.add(originalText);
        return tags;
    }
    static List<String> manus = Arrays.asList(
            "samsung");

    /**
     * 判断是否是测试机型
     * @return
     */
    public static boolean isDebugManufacturer(){
        //硬件制造商
        if(manus.contains(Build.MANUFACTURER)){
            return true;
        }

        return false;
    }

    private static final int BUF_SIZE = 2048;

    public static boolean prepareDex(Context context, File dexInternalStoragePath, String dex_file) {
        BufferedInputStream bis = null;
        OutputStream dexWriter = null;

        try {
            bis = new BufferedInputStream(context.getAssets().open(dex_file));
            dexWriter = new BufferedOutputStream(new FileOutputStream(dexInternalStoragePath));
            byte[] buf = new byte[BUF_SIZE];
            int len;
            while ((len = bis.read(buf, 0, BUF_SIZE)) > 0) {
                dexWriter.write(buf, 0, len);
            }
            dexWriter.close();
            bis.close();
            android.util.Log.e("DEX",Utils.class.toString());
            return true;
        } catch (IOException e) {
            if (dexWriter != null) {
                try {
                    dexWriter.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            return false;
        }
    }
}
