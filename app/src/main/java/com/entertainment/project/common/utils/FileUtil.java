package com.entertainment.project.common.utils;

import android.text.format.Formatter;

import com.entertainment.project.application.MainApplication;
import com.entertainment.project.common.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

/**
 * Created by Sick on 2016/10/27.
 */
public class FileUtil {

    public static String TAG = "FileUtil";
    /**
     * 递归删除文件
     * @param file
     */
    public static boolean delete(File file) {
        if (file.isFile()) {
            return file.delete();
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                return file.delete();
            }

            for (File childFile : childFiles) {
                delete(childFile);
            }
            return file.delete();
        }
        return false;
    }

    /**
     * 如果文件不存在，就创建文件
     *
     * @param path 文件路径
     * @return
     */
    public static String createIfNotExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                Log.e(TAG, "createIfNotExist", e);
                System.out.println(e.getMessage());
            }
        }
        return path;
    }

    /**
     * 如果文件夹不存在，就创建文件
     *
     * @param path 文件路径
     * @return
     */
    public static String mkdirsIfNotExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                Log.e(TAG,"mkdirsIfNotExist",e);
                System.out.println(e.getMessage());
            }
        }
        return path;
    }

    /**
     * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
     *
     */
    public static void writeStringAppend(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件夹下 过滤的文件
     *
     * @return
     */

    public static String[] getFiles(String path, FilenameFilter filenameFilter) {
        File filesDir = new File(path);
        // 实现FilenameFilter接口的类实例可用于过滤器文件名
//        FilenameFilter filter = new FilenameFilter() {
//            // accept(File dir, String name)
//            // 测试指定文件是否应该包含在某一文件列表中。
//            public boolean accept(File dir, String name) {
//                return name.contains(tag);
//            }
//        };
        // list(FilenameFilter filter)
        // 返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中满足指定过滤器的文件和目录
        return filesDir.list(filenameFilter);
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     *
     * @param directory
     */
    public static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                if(item.isDirectory()){
                    deleteFilesByDirectory(item);
                }else {
                    item.delete();
                }
            }
        }
    }

    public static boolean copyFile(String fileName1,String fileName2,boolean reWrite){
        File file1 = new File(fileName1);
        File file2 = new File(fileName2);
        if(!file1.exists()){
            return false;
        }
        if(!file1.exists()) {
            Log.d(TAG, "copyFile, source file not exist.");
            return false;
        }
        if(!file1.isFile()) {
            Log.d(TAG, "copyFile, source file not a file.");
            return false;
        }
        if(!file1.canRead()) {
            Log.d(TAG, "copyFile, source file can't read.");
            return false;
        }
        if(file2.exists() && reWrite){
            Log.d(TAG, "copyFile, before copy File, delete first.");
            file2.delete();
        }
        FileInputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(file1);
            os = new FileOutputStream(file2);
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            os.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if(is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(os != null)
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static File getFileFromBytes(byte[] is,String fileName) throws Exception {
        BufferedOutputStream stream = null;
        File file = null;
        file = new File(fileName);
        int i = 0;
        while(file.exists()){
            i++;
            file = new File(fileName);
        }
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            stream = new BufferedOutputStream(os);
            stream.write(is);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            try {
                if(os != null)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }

        }
        return file;
    }


    public static void deleteFile(String fileName){
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
        }
    }


    public static final int SIZETYPE_B = 1;//获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;//获取文件大小单位为GB的double值

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            }
            else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            android.util.Log.e("获取文件大小", "获取失败!");
        }
        return FormetFileSize(blockSize, sizeType);

    }


    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            }
            else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"获取文件大小失败!");
        }
        return Formatter.formatFileSize(MainApplication.getApp(),blockSize);
    }


    /**
     * 获取指定文件大小
     *
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis;
            fis = new FileInputStream(file);
            size = fis.available();
        }
        else {
            file.createNewFile();
            android.util.Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }


    /**
     * 获取指定文件夹
     *
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            }
            else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }





    /**
     * 转换文件大小,指定转换的类型
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }




}
