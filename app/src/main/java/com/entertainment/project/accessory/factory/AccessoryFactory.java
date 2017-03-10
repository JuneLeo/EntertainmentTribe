package com.entertainment.project.accessory.factory;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;


import com.entertainment.project.accessory.Conf;
import com.entertainment.project.accessory.access.Accessory;
import com.entertainment.project.accessory.access.AssetsAsy;
import com.entertainment.project.accessory.access.FileAsy;
import com.entertainment.project.accessory.access.ImageAsy;
import com.entertainment.project.accessory.access.InvoiceAsy;
import com.entertainment.project.accessory.access.QRCodeInvoiceAsy;
import com.entertainment.project.common.utils.Util;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Sick on 2016/11/15.
 */
public class AccessoryFactory {
    /**
     * 从网络接收后创建一个实体
     *
     * @param hashMap
     * @return
     */
    public static Accessory createAccessoryFromService(Conf.BillType billType, HashMap hashMap, Context context) {
        int type = Util.parseInt(hashMap.get("invoiceType"));
        //invoiceType 1 文档和图片  2 电子发票  3 二维码
        //type 6 图片 7word 8 xls  9pdf    11 电子发票   12 二维码
        switch (type) {
            case 1:
                String fileName = Util.parseString(hashMap.get("fileName"));
                if (!TextUtils.isEmpty(fileName)) {
                    fileName.substring(fileName.lastIndexOf(".") + 1);
                } else {
                    fileName = Util.parseString(hashMap.get("image"));
                    fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
                }
                type = Conf.getAccessoryType(fileName);
                if (type == Conf.accessType.TYPE_PICTURE) {
                    return new ImageAsy(hashMap, type, context, billType);
                } else {
                    return new FileAsy(hashMap, type, context, billType);
                }
            case 2:
                return new InvoiceAsy(hashMap, Conf.accessType.TYPE_INVOICE, context, billType);
            case 3:
                return new QRCodeInvoiceAsy(hashMap, Conf.accessType.TYPE_QRCODE_INVOICE, context, billType);
        }
        return null;
    }

    /**
     * 从本地获取文档后构建一个实体
     *
     * @param file
     * @param type
     * @return
     */
    public static Accessory createAccessoryFromLocal(Conf.BillType billType, File file, int type, Context context) {
        switch (type) {
            case Conf.accessType.TYPE_WORD_AND_IMG:
                String fileName = file.getAbsolutePath();
                fileName.substring(fileName.lastIndexOf(".") + 1);
                type = Conf.getAccessoryType(fileName);
                if (type == Conf.accessType.TYPE_PICTURE) {
                    return new ImageAsy(file, type, context, billType);
                } else {
                    return new FileAsy(file, type, context, billType);
                }
            case Conf.accessType.TYPE_INVOICE:
                return new InvoiceAsy(file, Conf.accessType.TYPE_INVOICE, context, billType);
            case Conf.accessType.TYPE_QRCODE_INVOICE:
                return new QRCodeInvoiceAsy(file, Conf.accessType.TYPE_QRCODE_INVOICE, context, billType);
        }
        return null;
    }

    /**
     * 创建一个对象  里面含view
     *
     * @param view
     * @param type
     * @return
     */
    public static Accessory createAccessoryView(View view, int type) {
        return new AssetsAsy(view, type);
    }

    /**
     * 创建一个对象  里面含布局id
     *
     * @param layoutID
     * @param type
     * @return
     */
    public static Accessory createAccessoryLayoutID(int layoutID, int type) {
        return new AssetsAsy(layoutID, type);
    }


    /**
     * 创建一个对象  主要用于  标题 作为一个item
     *
     * @param type
     * @return
     */
    public static Accessory createAccessoryType(String title, int type) {
        return new AssetsAsy(type,title);
    }

}
