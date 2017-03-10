package com.entertainment.project.accessory.alg;

import com.entertainment.project.accessory.Conf;
import com.entertainment.project.accessory.access.Accessory;
import com.entertainment.project.accessory.access.AssetsAsy;
import com.entertainment.project.accessory.factory.AccessoryFactory;
import com.entertainment.project.common.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Sick on 2016/11/16.
 */
public class AccessoryList {

    private List<Long> ids;
    private ArrayList<Accessory> accessories;
    private List<Integer> type;
    private Accessory accessoryAttach;
    private Accessory accessoryInvoice;

    public AccessoryList() {
        ids = new ArrayList<Long>();
        type = new ArrayList<Integer>();
        accessories = new ArrayList<Accessory>();
        accessoryAttach = AccessoryFactory.createAccessoryType("附件", Conf.accessType.TYPE_TITLE_ATTACHMENT);
        accessoryInvoice = AccessoryFactory.createAccessoryType("电子发票", Conf.accessType.TYPE_TITLE_INVOICE);
        accessories.add(accessoryAttach);
        accessories.add(accessoryInvoice);
    }

    /**
     * 去重
     *
     * @param accessory
     */
    public void add(Accessory accessory) {
        //有id则 判断 是否重复 ，没有id 则直接添加
        if (accessory.getId() != null && !ids.contains(accessory.getId())) {
            ids.add(accessory.getId());
            accessories.add(accessory);
            type.add(Util.parseInt(accessory.accessoryType, 0));
        } else if (accessory.getId() == null) {
            accessories.add(accessory);
            type.add(Util.parseInt(accessory.accessoryType, 0));
        } else if (accessory.getId() != null && ids.contains(accessory.getId())) {
            //有id 且包含这条数据，不进行操作，不可改变
        }
    }

    /**
     * 移除
     *
     * @param pos
     * @return
     */
    public Accessory remove(int pos) {
        if (accessories.get(pos).getId() != null && ids.contains(accessories.get(pos).getId())) {
            ids.remove(accessories.get(pos).getId());
        }
        if (type.contains(accessories.get(pos).getAccessoryType())) {
            type.remove(Util.parseInt(accessories.get(pos).getAccessoryType(), 0));
        }
        isCanTitle();
        return accessories.remove(pos);
    }

    /**
     * 移除
     *
     * @param accessory
     * @return
     */
    public boolean remove(Accessory accessory) {
        if (accessory.getId() != null && ids.contains(accessory.getId())) {
            ids.remove(accessory.getId());
        }
        if (type.contains(accessory.getAccessoryType())) {
            type.remove(Util.parseInt(accessory.getAccessoryType(), 0));
        }
        boolean isRemove = accessories.remove(accessory);
        isCanTitle();
        return isRemove;
    }

    /**
     * 添加所有的数据
     *
     * @param accessoryList
     */
    public void addAll(List<Accessory> accessoryList) {
        accessories.addAll(accessoryList);
        isCanTitle();
    }

    /**
     * 清空,切记不清空含View的
     */
    public void clear() {
        ids.clear();
        type.clear();
        Iterator<Accessory> iterator = accessories.iterator();
        while (iterator.hasNext()) {
            Accessory accessory = iterator.next();
            if (!(accessory instanceof AssetsAsy)) {
                iterator.remove();
            }
        }
    }

    /**
     * 获取list
     *
     * @return
     */
    public ArrayList<Accessory> getAccessoryList() {
        return accessories;
    }

    public void sort() {
        isCanTitle();
        Collections.sort(accessories);
    }

    public void isCanTitle() {
        ((AssetsAsy) accessoryAttach).setVisible(false);
        ((AssetsAsy) accessoryInvoice).setVisible(false);
        for (Integer i : type) {
            if (i == Conf.accessType.TYPE_PICTURE ||
                    i == Conf.accessType.TYPE_WORD ||
                    i == Conf.accessType.TYPE_XLS ||
                    i == Conf.accessType.TYPE_PDF) {
                ((AssetsAsy) accessoryAttach).setVisible(true);
            } else if (i == Conf.accessType.TYPE_INVOICE ||
                    i == Conf.accessType.TYPE_INVOICE) {
                ((AssetsAsy) accessoryInvoice).setVisible(true);
            }
        }
    }

    public void updateID(Long id) {
        if (!ids.contains(id)) {
            ids.add(id);
        }
    }
}
