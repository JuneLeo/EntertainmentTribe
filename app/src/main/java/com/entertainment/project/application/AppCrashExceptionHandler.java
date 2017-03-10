package com.entertainment.project.application;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.entertainment.project.BuildConfig;
import com.entertainment.project.common.Constants;
import com.entertainment.project.common.Log;
import com.entertainment.project.common.utils.SDCardUtil;
import com.entertainment.project.common.utils.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.TreeSet;


/**
 * Created by huangcy on 15/9/15.
 */

/**
 *
 *
 * AppCrashExceptionHandler：线程未捕获异常控制器是用来处理未捕获异常的。
 *                           如果程序出现了未捕获异常默认情况下则会出现强行关闭对话框
 *                           实现该接口并注册为程序中的默认未捕获异常处理
 *                           这样当未捕获异常发生时，就可以做些异常处理操作
 *                           例如：收集异常信息，发送错误报告 等。
 *
 * UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 */
public class AppCrashExceptionHandler implements Thread.UncaughtExceptionHandler {
    /**
     * Debug Log Tag
     */
    public static final String TAG = "AppCrashExceptionHandler";

    /**
     * CrashHandler实例
     */
    private static AppCrashExceptionHandler INSTANCE;
    /**
     * 程序的Context对象
     */
    private Context mContext;

    /**
     * 发送结束后是否关闭应用;
     */
    private boolean bKillAppAfterPost=false;
    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    /**
     * 使用Properties来保存设备的信息和错误堆栈信息
     */

    //private java.util.Properties mDeviceCrashInfo = new Properties();
    private static final String VERSION_NAME = "APP_VERSION_NAME";
    private static final String VERSION_CODE = "APP_VERSION_CODE";
    private static final String ANDROID_SDK_VER = "ANDROID_SDK_VERSION";
    private static final String ANDROID_OS_VER = "ANDROID_OS_VERSION";
    private static final String STACK_TRACE = "STACK_TRACE";
    private static final String APP_USER_ID="APP_USER_ID";
    private static final String APP_USER_NAME="APP_USER_NAME";
    private static final String APP_USER_MOBILENO="APP_USER_MOBILENO";
    private static final String CRASH_TIME="CRASH_TIME";
    private static final String MODEL="MODEL";
    private static final String MANUFACTURER="MANUFACTURER";
    private static final String SD_TOTAL_SIZE="SD_TOTAL_SIZE";
    private static final String SD_AVAILABLE_SIZE="SD_AVAILABLE_SIZE";
    private static final String MEM_TOTAL_SIZE="MEM_TOTAL_SIZE";
    private static final String MEM_AVAILABLE_SIZE="MEM_AVAILABLE_SIZE";

    private static final String DEVICE_INFO="DEVICE_INFO";

    /**
     * 错误报告文件的扩展名
     */
    private static final String CRASH_REPORTER_EXTENSION = ".txt";

    /**
     * 保证只有一个CrashHandler实例
     */
    private AppCrashExceptionHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static AppCrashExceptionHandler getInstance() {
        if (INSTANCE == null)
            INSTANCE = new AppCrashExceptionHandler();
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx
     */
    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            // Sleep一会后结束程序
            // 来让线程停止一会是为了显示Toast信息给用户，然后Kill程序
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "Error : ", e);
            }
            //mDefaultHandler.uncaughtException(thread, ex);
            //android.os.Process.killProcess(android.os.Process.myPid());
            //System.exit(0);

            try {
                //如果主界面启动已完成,再重启应用,防止未启动完成出异常后导致不断重启
                if (Constants.isStartupCompleted) {
//                    Intent intent = new Intent(mContext, LoadingActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    mContext.startActivity(intent);
                }

                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

            }catch (Throwable e){
                Log.e(TAG, "Error : ", e);
            }
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        this.bKillAppAfterPost=false;

        if (ex == null) {
            return true;
        }
        Log.e(TAG, "发生闪退,异常信息:" + ex.getLocalizedMessage(), ex);

        try {
            // 使用Toast来显示异常信息
            new Thread() {
                @Override
                public void run() {
                    // Toast 显示需要出现在一个线程的消息队列中
                    Looper.prepare();
                    if (Constants.isStartupCompleted) {
                        Toast.makeText(mContext, "娱乐部落发生未知错误,将自动重新启动,请稍后.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mContext, "娱乐部落发生未知错误,将关闭.", Toast.LENGTH_LONG).show();
                    }
                    Looper.loop();
                }
            }.start();

            Properties data=new Properties();
            // 收集设备信息
            collectCrashDeviceInfo(mContext, data);
            // 保存错误报告文件
            String crashFileName = saveCrashInfoToFile(ex, data);
            // 发送错误报告到服务器
        /*
        if (!TextUtils.isEmpty(crashFileName))
            postReport(new File(crashFileName),mContext);
        */
            return true;
        }catch (Throwable e){
            Log.e(TAG,e);
        }
        return true;
    }

    /**
     * 收集程序崩溃的设备信息
     *
     * @param ctx
     */
    private void collectCrashDeviceInfo(Context ctx,Properties data) {

        Log.d(TAG, "开始记录异常错误的设备信息.");

        try{
            //用户的id
//            data.put(APP_USER_ID, String.valueOf(Constants.getCurrentUser(ctx).getUserId()));
        }catch (Throwable e){
            e.printStackTrace();
            Log.e(TAG, "", e);
        }
        data.put(CRASH_TIME,Constants.format.yyyy_MM_dd_HH_mm_ss.format(new Date()));
        data.put(ANDROID_SDK_VER,String.valueOf(Build.VERSION.SDK_INT));
        data.put(ANDROID_OS_VER,Build.VERSION.RELEASE);

        try {
            // Class for retrieving various kinds of information related to the
            // application packages that are currently installed on the device.
            // You can find this class through getPackageManager().
            PackageManager pm = ctx.getPackageManager();
            // getPackageInfo(String packageName, int flags)
            // Retrieve overall information about an application package that is installed on the system.
            // public static final int GET_ACTIVITIES
            // Since: API Level 1 PackageInfo flag: return information about activities in the package in activities.
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                // public String versionName The version name of this package,
                // as specified by the <manifest> tag's versionName attribute.
                data.put(VERSION_NAME, Util.parseString(pi.versionName));
                // public int versionCode The version number of this package,
                // as specified by the <manifest> tag's versionCode attribute.
                data.put(VERSION_CODE,Util.parseString(pi.versionCode));
            }
        } catch (Throwable e) {
            Log.e(TAG, "Error while collect package info", e);
        }

        data.put(MODEL,Build.MODEL);
        data.put(MANUFACTURER, Build.MANUFACTURER);

        // 使用反射来收集设备信息.在Build类中包含各种设备信息,
        // 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
        // 返回 Field 对象的一个数组，这些对象反映此 Class 对象所表示的类或接口所声明的所有字段

        Properties deviceinfo=new Properties();

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                // setAccessible(boolean flag)
                // 将此对象的 accessible 标志设置为指示的布尔值。
                // 通过设置Accessible属性为true,才能对私有变量进行访问，不然会得到一个IllegalAccessException的异常
                field.setAccessible(true);

                deviceinfo.put(field.getName(), Util.parseString(field.get(null)));

                Log.d(TAG, field.getName() + " : " + Util.parseString(field.get(null)));

            } catch (Throwable e) {
                Log.e(TAG, "Error while collect crash info", e);
            }
        }

        //增加用户名\SD空间\内存情况:
        try{
            //非生产环境或者调试版收集用户的信息
            if (!Constants.ReleaseType.Product.equalsIgnoreCase(Constants.releaseType) || BuildConfig.DEBUG){
                //储存用户的信息   名字和手机号
//                deviceinfo.put(APP_USER_NAME, Constants.getCurrentUser(ctx).getName());
//                deviceinfo.put(APP_USER_MOBILENO,Constants.getCurrentUser(ctx).getStaff().getMobile());
            }

            deviceinfo.put(SD_TOTAL_SIZE, SDCardUtil.getSDTotalSize(ctx));
            deviceinfo.put(SD_AVAILABLE_SIZE,SDCardUtil.getSDAvailableSize(ctx));
            deviceinfo.put(MEM_TOTAL_SIZE,SDCardUtil.getRomTotalSize(ctx));
            deviceinfo.put(MEM_AVAILABLE_SIZE,SDCardUtil.getRomAvailableSize(ctx));

        }catch (Throwable e){
            e.printStackTrace();
            Log.e(TAG, "", e);
        }

        data.put(DEVICE_INFO,deviceinfo.toString());


        Log.d(TAG, "完成记录异常错误的设备信息...");

    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return
     */
    private String saveCrashInfoToFile(Throwable ex,Properties data) {


        Log.d(TAG, "开始存储异常错误信息到文件...");

        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        // printStackTrace(PrintWriter s)
        // 将此 throwable 及其追踪输出到指定的 PrintWriter
        ex.printStackTrace(printWriter);


        Log.e(TAG, "存储异常信息:" + ex.getLocalizedMessage(), ex);

        // getCause() 返回此 throwable 的 cause；如果 cause 不存在或未知，则返回 null。
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        // toString() 以字符串的形式返回该缓冲区的当前值。
        String result = info.toString();
        printWriter.close();
        data.put(STACK_TRACE, result);

        try {
            long timestamp = System.currentTimeMillis();
            String fileName = "crash-"+Constants.format.yyyyMMddHHmmss.format(new Date())  + CRASH_REPORTER_EXTENSION;
            // 保存文件
            File dir = new File(Environment.getExternalStorageDirectory() + "/" + Constants.LocalFileDIR.LOCAL_CRASH_LOG_DIR+ "/");
            if (!dir.exists()){
                dir.mkdirs();
            }

            FileOutputStream trace = new FileOutputStream(dir.getAbsolutePath()+"/"+fileName,true);
            data.store(trace, "");

            //trace.write(("\r\n" + STACK_TRACE + "\r\n").getBytes());
            //trace.write(result.getBytes());
            trace.flush();
            trace.close();

            Log.d(TAG, "完成存储异常错误信息到文件...");

            return dir.getAbsolutePath()+"/"+fileName;
        } catch (Throwable e) {
            Log.e(TAG, "an error occured while writing report file...", e);
        }

        Log.d(TAG, "完成存储异常错误信息到文件...");

        return null;
    }


    /**
     * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
     */

    public synchronized void sendPreviousReportsToServer(Context context) {
        bKillAppAfterPost=false;
        try {
            sendCrashReportsToServer(context);
        }catch (Throwable e){
            Log.e(TAG,e);
        }
    }

    /**
     * 把错误报告发送给服务器,包含新产生的和以前没发送的.
     *
     * @param ctx
     */

    private void sendCrashReportsToServer(Context ctx) {
        String[] crFiles = getCrashReportFiles(ctx);
        if (crFiles != null && crFiles.length > 0) {
            TreeSet<String> sortedFiles = new TreeSet<String>();
            sortedFiles.addAll(Arrays.asList(crFiles));

            for (String fileName : sortedFiles) {
                File cr = new File(Environment.getExternalStorageDirectory() + "/" + Constants.LocalFileDIR.LOCAL_CRASH_LOG_DIR, fileName);
                postReport(cr,ctx);
                //cr.delete();// 删除已发送的报告

            }
        }
    }


    /**
     * 获取错误报告文件名
     *
     * @param ctx
     * @return
     */
    private String[] getCrashReportFiles(Context ctx) {
        File filesDir = new File(Environment.getExternalStorageDirectory() + "/" + Constants.LocalFileDIR.LOCAL_CRASH_LOG_DIR+ "/");
        // 实现FilenameFilter接口的类实例可用于过滤器文件名
        FilenameFilter filter = new FilenameFilter() {
            // accept(File dir, String name)
            // 测试指定文件是否应该包含在某一文件列表中。
            public boolean accept(File dir, String name) {
                return name.endsWith(CRASH_REPORTER_EXTENSION);
            }
        };
        // list(FilenameFilter filter)
        // 返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中满足指定过滤器的文件和目录
        return filesDir.list(filter);
    }



    //发送文件,并在发送结束后结束进程
    private void postReport(File file,Context context) {

        Properties data = new Properties();
        try {
            FileInputStream stream = new FileInputStream(file);
            data.load(stream);
            stream.close();
        }catch (IOException e){
            e.printStackTrace();
            Log.e(TAG, "", e);
        }
        if (data.size()>0){
            CrashReport report =new CrashReport();
//            if (data.containsKey(APP_USER_ID)) {
//                report.setAppUserId(Long.valueOf(data.getProperty(APP_USER_ID,"0")));
//            }
            report.setCrashTime(data.getProperty(CRASH_TIME));
            report.setAndroidOSVer(data.getProperty(ANDROID_OS_VER));
            report.setAndroidSdkVer(data.getProperty(ANDROID_SDK_VER));
            report.setAppVersionCode(data.getProperty(VERSION_CODE));
            report.setAppVersionName(data.getProperty(VERSION_NAME));
            report.setManufacturer(data.getProperty(MANUFACTURER));
            report.setModel(data.getProperty(MODEL));
            report.setDeviceInfo(data.getProperty(DEVICE_INFO));
            report.setCrashTrace(data.getProperty(STACK_TRACE));
            //发送错误信息给服务器
//            ByteArrayEntity entity = null;
//            try {
//                byte[] bytes = JsonService.CrashReport2json(report);
//
//                entity = new ByteArrayEntity(bytes);
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.e(TAG, "", e);
//            }
//            if (entity != null) {
//
//                CrashReportPostHandler handler = new CrashReportPostHandler(file);
//                //handler.setUrl(Constants.url.TRAVEL_ADDRESSES, entity, httpUtil, Constants.requestType.POST2);
//                HttpUtil httpUtil=HttpUtil.getInstance();
//                httpUtil.post(context, Constants.url.ADDCRASHLOG_URL, entity, handler);
//
//            }
        }
    }

    /**
     * 发送请求回调
     */
//    private class CrashReportPostHandler extends AsyncHttpResponseHandler{
//
//        File file;
//
//        public CrashReportPostHandler(File f){this.file=f;
//        }
//
//        @Override
//        public void onFinish() {
//            super.onFinish();
//            //如果发送错误报告后退出程序,则退出;
//            if (bKillAppAfterPost) android.os.Process.killProcess(android.os.Process.myPid());
//        }
//
//        @Override
//        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//            try {
//                error.printStackTrace();
//
//                Log.d(TAG, "发送错误信息文件失败:" + file.getAbsolutePath());
//                Log.e(TAG, new String(responseBody), error);
//
//            }catch (Exception e){
//                e.printStackTrace();
//                Log.e(TAG, "", e);
//            }
//
//
//
//        }
//
//        @Override
//        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//            try {
//
//                Log.d(TAG, "发送错误信息文件成功:"+file.getAbsolutePath());
//
//                file.delete();
//
//
//
//            }catch (Exception e){
//                e.printStackTrace();
//                Log.e(TAG, "", e);
//            }
//        }
//
//
//
//    }


    /**
     * 记录影响流程的较大的业务或者系统层面错误,并发送到服务端,关闭系统
     */
    public synchronized void saveAndPostExceptionToServer(Throwable ex,Context ctx,boolean bKillAppAfterPost){

        this.bKillAppAfterPost=bKillAppAfterPost;

        try {
            String fileName=saveException(ex,ctx);
            if (!TextUtils.isEmpty(fileName)) {
                File file = new File(fileName);
                postReport(file, ctx);
            }
        }catch (Throwable e){
            e.printStackTrace();
            Log.e(TAG, "", e);
        }
    }

    /**
     * 记录影响流程的较大的业务或系统层面错误,不发送错误,也不关闭系统
     */
    public String saveException(Throwable ex,Context ctx){
        try {
            Properties data = new Properties();
            collectCrashDeviceInfo(ctx, data);
            return saveCrashInfoToFile(ex, data);
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "", e);
        }
        return null;
    }

}