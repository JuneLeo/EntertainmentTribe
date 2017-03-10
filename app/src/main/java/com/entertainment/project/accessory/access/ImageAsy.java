package com.entertainment.project.accessory.access;

import android.content.Context;

import com.entertainment.project.accessory.Conf;
import com.entertainment.project.common.utils.Util;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Sick on 2016/11/15.
 */
public class ImageAsy extends Accessory {

    public ImageAsy(HashMap hashMap, int accessoryType, Context context, Conf.BillType billType) {
        super(hashMap, accessoryType, context, billType);
    }

    public ImageAsy(File file, int type, Context context, Conf.BillType billType) {
        super(file, type, context, billType);
    }

    @Override
    public void functionFromService() {
        super.functionFromService();
        if (fileName == null) {
            if (hashMap.containsKey("image")) {
                fileName = Util.parseString(hashMap.get("image"));
            }
        }
    }

    @Override
    public String getAccessoryProgress() {
        if (accessoryState == Conf.accssState.UNDOWN_LOAD) {
            return "";
        } else {
            return super.getAccessoryProgress();
        }
    }

    /**
     * 需要上传的   id为空     未上传   上传失败
     *
     * @return
     */
    public boolean isImgUpload() {
        if (id == null && (accessoryState == Conf.accssState.UNUP_LOAD ||
                accessoryState == Conf.accssState.UP_LOADED_FAILURE)) {
            return true;
        }
        return false;
    }

    /**
     * 可以读取的     下载成功   上传成功  已下载  未下载    id不为空
     *
     * @return
     */
    public boolean isImgRead() {
        if (id != null && (accessoryState == Conf.accssState.DOWN_LOAD_SUCCESS ||
                accessoryState == Conf.accssState.UP_LOADED_SUCCESS ||
                accessoryState == Conf.accssState.DOWN_LOADED ||
                accessoryState == Conf.accssState.UNDOWN_LOAD)) {
            return true;
        }
        return false;
    }
}
