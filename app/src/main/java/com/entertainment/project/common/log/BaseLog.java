package com.entertainment.project.common.log;

import android.os.Environment;

import com.entertainment.project.common.Constants;
import com.entertainment.project.common.utils.FileUtil;

import java.io.File;
import java.util.Date;

/**
 * Created by lc on 16/4/1.
 */
public class BaseLog {

    /**
     * 文件的扩展名
     */
    private static final String TXT = ".txt";
    private static final String LOG = ".log";

    private String fileName;

    public String baseUrl;

    public BaseLog() {
        init();
    }

    public void init(){
        baseUrl = Environment.getExternalStorageDirectory() + File.separator + Constants.LocalFileDIR.LOCAL_LOG_DIR+ File.separator;

    }

    /**
     * @param content  内容
     */
    public void writeToFile(LogEvent content){
        FileUtil.writeStringAppend(this.fileName, content.toString());
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String tag) {
        this.fileName = creatFileName(baseUrl+tag);
        FileUtil.createIfNotExist(this.fileName);
    }

    public static String creatFileName(String name){
        return name +"-"+Constants.format.yyyyMMdd.format(new Date()) + LOG;
    }
}
