package com.entertainment.project.accessory.access;

import android.content.Context;
import android.view.View;

import com.entertainment.project.accessory.Conf;
import com.entertainment.project.common.utils.Util;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Sick on 2016/11/15.
 */
public abstract class Accessory implements Serializable, Comparable<Accessory> {

    public HashMap hashMap;

    public File file;

//    public UserService userService;

    public Long id;  //附件id

    public String fileName;  //附件名   不为空

    public String fileNameAbs;  //绝对路径

    public String url;      //路径

    public Long size;     //大小

    public int accessoryType;   //1头部 2数据view 3 图片 4 Word 5 XlS 6 PDF  7 电子发票  8 二维码

    public int accessoryFormat;// 有三类  1图片 2文档，3电子发票，4二维码

    public int serviceType; //后台分类

    public Double accessoryProgress;   //进度

    public int accessoryState = 0;    //上传下载状态

    public Conf.BillType accessoryBillType;  // 单据类型

    public Accessory(HashMap hashMap, int accessoryType, Context context, Conf.BillType billType) {
//        userService = new UserService(context);
        this.hashMap = hashMap;
        this.accessoryType = accessoryType;
        this.accessoryBillType = billType;
        functionFromService();
    }

    public Accessory(File file, int accessoryType, Context context, Conf.BillType billType) {
        this.file = file;
        this.accessoryType = accessoryType;
        this.accessoryBillType = billType;
        functionFromLocal();
    }

    public Accessory(View view, int accessoryType) {
    }

    public Accessory() {
    }

    /**
     * 创建实体类以后转化   internet
     */
    public void functionFromService() {


        if (hashMap.containsKey("fileName")) {
            fileName = Util.parseString(hashMap.get("fileName"));
        }
        if (hashMap.containsKey("id") && null != hashMap.get("id")) {
            id = Util.parseLong(hashMap.get("id"), 0L);
//            url = userService.getBaseUrl() + "/expense/getimg/" + id + "/";
        }
        if (hashMap.containsKey("size") && null != hashMap.get("size")) {
            size = Util.parseLong(hashMap.get("size"), 0L);
        }
        if (hashMap.containsKey("invoiceType") && null != hashMap.get("invoiceType")) {
            serviceType = Util.parseInt(hashMap.get("invoiceType"), 0);
        }


        String name = null;
//        if (accessoryBillType == Conf.BillType.MISSION) {
//            name = Util.getOptionsKey(Util.parseString(id), fileName);
//        } else if (accessoryBillType == Conf.BillType.EXPENSE) {
//            name = Util.getExpenseOptionsKey(Util.parseString(id), fileName);
//        }
//        if (!TextUtils.isEmpty(name) && FileLoader.getInstance().CheckCache(name)) {
//            accessoryState = Conf.accssState.DOWN_LOADED;
//        } else {
//            accessoryState = Conf.accssState.UNDOWN_LOAD;//网络下载的第一次为未下载
//        }

//        if (accessoryType == Conf.accessType.TYPE_PICTURE && id != null) {
//            accessoryState = Conf.accssState.DOWN_LOADED;
//        }
    }

    /**
     * 创建实体类以后转化  local
     */
    public void functionFromLocal() {


        if (file != null) {
            fileName = file.getName();
            fileNameAbs = file.getAbsolutePath();
            size = file.length();
        }

        accessoryState = Conf.accssState.UNUP_LOAD;//本地构建的第一次都为未上传
    }

    /**
     * 获取上传下载的显示状态
     *
     * @return
     */
    public String getAccessoryProgress() {
        if (accessoryState == Conf.accssState.UNUP_LOAD) {
            return Conf.accssStateCH.UNUP_LOAD;  //未上传
        }
        if (accessoryState == Conf.accssState.UNDOWN_LOAD) {
            String name = null;
//            if (accessoryBillType == Conf.BillType.MISSION) {
//                name = Util.getOptionsKey(Util.parseString(id), fileName);
//            } else if (accessoryBillType == Conf.BillType.EXPENSE) {
//                name = Util.getExpenseOptionsKey(Util.parseString(id), fileName);
//            }
//            if (!TextUtils.isEmpty(name) && FileLoader.getInstance().CheckCache(name)) {
//                accessoryState = Conf.accssState.DOWN_LOADED;
//                return Conf.accssStateCH.NO_DISPLAY;
//            }
            return Conf.accssStateCH.UNDOWN_LOAD; //未下载
        }

        if (accessoryState == Conf.accssState.DOWN_LOADED) {
            return Conf.accssStateCH.NO_DISPLAY;
        }

        if (accessoryState == Conf.accssState.UP_LOADED_FAILURE) {
            return Conf.accssStateCH.UP_LOADED_FAILURE; //上传失败重新上传
        }
        if (accessoryState == Conf.accssState.DOWN_LOADED_FAILURE) {
            return Conf.accssStateCH.DOWN_LOADED_FAILURE; //下载失败重新上传
        }
        if (accessoryState == Conf.accssState.UP_LOADED_FAILURE_IS_USED) {
            return Conf.accssStateCH.UP_LOADED_FAILURE_IS_USED; //已使用
        }
        if (accessoryState == Conf.accssState.UP_LOADED_FAILURE_NO_IDENTIFY) {
            return Conf.accssStateCH.UP_LOADED_FAILURE_NO_IDENTIFY; //无法识别
        }

        if (accessoryProgress != null) {
            if (accessoryState == Conf.accssState.UP_LOADING || accessoryState == Conf.accssState.DOWN_LOADING) {
                if (accessoryType == Conf.accessType.TYPE_INVOICE && accessoryState == Conf.accssState.UP_LOADING && accessoryProgress.equals(1d)) {
                    return Conf.accssStateCH.UP_LOADING_IS_IDENTIFY;
                }
                return Conf.formatProgress(accessoryProgress);  //正在上传中显示百分比
            }
            if (accessoryState == Conf.accssState.UP_LOADED_SUCCESS && accessoryProgress.equals(1d)) {
                if (accessoryType == Conf.accessType.TYPE_INVOICE) {
                    return Conf.accssStateCH.UP_LOADED_IDENTIFY_SUCCESS;
                }
                return Conf.accssStateCH.UP_LOADED_SUCCESS;    //上传成功
            }
            if (accessoryState == Conf.accssState.DOWN_LOAD_SUCCESS && accessoryProgress.equals(1d)) {
                return Conf.accssStateCH.DOWN_LOAD_SUCCESS;    //下载成功
            }
        }

        return null;
    }

    public void setAccessoryProgress(Double accessoryProgress) {
        if (accessoryProgress != null) {
            if (accessoryProgress < 1d) {
                this.accessoryProgress = accessoryProgress;
            } else {
                this.accessoryProgress = 1d;
            }
        }
    }


    public HashMap getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap hashMap) {
        this.hashMap = hashMap;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNameAbs() {
        return fileNameAbs;
    }

    public void setFileNameAbs(String fileNameAbs) {
        this.fileNameAbs = fileNameAbs;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public int getAccessoryType() {
        return accessoryType;
    }

    public void setAccessoryType(int accessoryType) {
        this.accessoryType = accessoryType;
    }

    public int getAccessoryFormat() {
        return accessoryFormat;
    }

    public void setAccessoryFormat(int accessoryFormat) {
        this.accessoryFormat = accessoryFormat;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }


    public int getAccessoryState() {
        return accessoryState;
    }

    public void setAccessoryState(int accessoryState) {
        this.accessoryState = accessoryState;
    }

    public Conf.BillType getAccessoryBillType() {
        return accessoryBillType;
    }

    public void setAccessoryBillType(Conf.BillType accessoryBillType) {
        this.accessoryBillType = accessoryBillType;
    }

    /**
     * 上传需要检测需要上传的文件  二维码电子发票不考虑
     *
     * @return
     */
    public boolean isCheckUpload() {
        if (accessoryType == Conf.accessType.TYPE_PICTURE ||
                accessoryType == Conf.accessType.TYPE_WORD ||
                accessoryType == Conf.accessType.TYPE_PDF ||
                accessoryType == Conf.accessType.TYPE_XLS ||
                accessoryType == Conf.accessType.TYPE_INVOICE) {
            return (id != null && id != 0);
        } else {
            return true;
        }
    }

    public boolean isCheckFile() {
        if (accessoryType == Conf.accessType.TYPE_WORD ||
                accessoryType == Conf.accessType.TYPE_PDF ||
                accessoryType == Conf.accessType.TYPE_XLS ||
                accessoryType == Conf.accessType.TYPE_INVOICE) {
            return true;
        }
        return false;
    }

    public boolean isGeneralFile() {
        if (accessoryType == Conf.accessType.TYPE_WORD ||
                accessoryType == Conf.accessType.TYPE_PDF ||
                accessoryType == Conf.accessType.TYPE_XLS ||
                accessoryType == Conf.accessType.TYPE_PICTURE) {
            return true;
        }
        return false;
    }

    public boolean isInvoiceFile() {
        if (accessoryType == Conf.accessType.TYPE_INVOICE) {
            return true;
        }
        return false;
    }

    public boolean isAccessory() {
        if (accessoryType == Conf.accessType.TYPE_WORD ||
                accessoryType == Conf.accessType.TYPE_PDF ||
                accessoryType == Conf.accessType.TYPE_XLS ||
                accessoryType == Conf.accessType.TYPE_PICTURE ||
                accessoryType == Conf.accessType.TYPE_INVOICE ||
                accessoryType == Conf.accessType.TYPE_QRCODE_INVOICE
                ) {
            return true;
        }
        return false;
    }

    public boolean isInvoiceAll() {
        if (accessoryType == Conf.accessType.TYPE_INVOICE ||
                accessoryType == Conf.accessType.TYPE_QRCODE_INVOICE) {
            return true;
        }
        return false;
    }


    public boolean isContainsId() {
        return (id != null && id != 0);
    }


    @Override
    public int compareTo(Accessory another) {
        return accessoryType - another.accessoryType;
    }

}
