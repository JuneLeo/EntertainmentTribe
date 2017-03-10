package com.entertainment.project.accessory.access;

import android.content.Context;

import com.entertainment.project.accessory.Conf;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Sick on 2016/11/15.
 */
public class FileAsy extends Accessory {


    public FileAsy(HashMap hashMap, int accessoryType, Context context, Conf.BillType billType) {
        super(hashMap, accessoryType, context, billType);
    }


    public FileAsy(File file, int type, Context context, Conf.BillType billType) {
        super(file, type, context, billType);
    }

    /**
     * id为空   上传失败  未上传  需要上传
     *
     * @return
     */
    public boolean isGeneralFileUpload() {
        if (id == null ||
                accessoryState == Conf.accssState.UP_LOADED_FAILURE ||
                accessoryState == Conf.accssState.UNUP_LOAD) {
            return true;
        }
        return false;
    }

    /**
     * 需要下载的      id不为空并且未下载   或者  id不为空且下载失败
     *
     * @return
     */
    public boolean isGeneralFileDownload() {
        if (id != null &&
                (accessoryState == Conf.accssState.UNDOWN_LOAD ||
                        accessoryState == Conf.accssState.DOWN_LOADED_FAILURE)) {
            return true;
        }
        return false;
    }

    /**
     * 可以读取的    下载成功，上传成功，已下载
     *
     * @return
     */
    public boolean isGeneralFileRead() {
        if (accessoryState == Conf.accssState.DOWN_LOADED ||
                accessoryState == Conf.accssState.UP_LOADED_SUCCESS ||
                accessoryState == Conf.accssState.DOWN_LOAD_SUCCESS) {
            return true;
        }
        return false;
    }

    public boolean isGeneralFileDownLoading() {
        if (accessoryState == Conf.accssState.DOWN_LOADED) {
            return true;
        }
        return false;
    }

    public boolean isGeneralFileUpLoading() {
        if (accessoryState == Conf.accssState.UP_LOADING) {
            return true;
        }
        return false;
    }

}
