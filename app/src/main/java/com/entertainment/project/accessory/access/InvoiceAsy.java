package com.entertainment.project.accessory.access;

import android.content.Context;

import com.entertainment.project.accessory.Conf;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Sick on 2016/11/15.
 */
public class InvoiceAsy extends Accessory {


    public InvoiceAsy(HashMap hashMap, int accessoryType, Context context, Conf.BillType billType) {
        super(hashMap, accessoryType, context,billType);
    }

    public InvoiceAsy(File file, int typeInvoice, Context context, Conf.BillType billType) {
        super(file, typeInvoice, context, billType);
    }


    /**
     * id为空   上传失败  未上传  无法识别
     *
     * @return
     */
    public boolean isInvoiceUpload() {
        if (id == null ||
                accessoryState == Conf.accssState.UP_LOADED_FAILURE ||
                accessoryState == Conf.accssState.UNUP_LOAD ||
                accessoryState == Conf.accssState.UP_LOADED_FAILURE_NO_IDENTIFY) {
            return true;
        }
        return false;
    }

    /**
     * 需要下载的      id不为空并且未下载   或者  id不为空且下载失败
     *
     * @return
     */
    public boolean isInvoiceDownload() {
        if (id != null &&
                (accessoryState == Conf.accssState.UNDOWN_LOAD ||
                        accessoryState == Conf.accssState.DOWN_LOADED_FAILURE)) {
            return true;
        }
        return false;
    }

    /**
     * 可以读取的    下载成功，上传成功，已下载 ,已经使用
     *
     * @return
     */
    public boolean isInvoiceRead() {
        if (accessoryState == Conf.accssState.DOWN_LOADED ||
                accessoryState == Conf.accssState.UP_LOADED_SUCCESS ||
                accessoryState == Conf.accssState.DOWN_LOAD_SUCCESS ||
                accessoryState == Conf.accssState.UP_LOADED_FAILURE_IS_USED) {
            return true;
        }
        return false;
    }

    public boolean isInvoiceDownLoading() {
        if (accessoryState == Conf.accssState.DOWN_LOADED) {
            return true;
        }
        return false;
    }

    public boolean isInvoiceUpLoading() {
        if (accessoryState == Conf.accssState.UP_LOADING) {
            return true;
        }
        return false;
    }

}
