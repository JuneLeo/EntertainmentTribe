package com.entertainment.project.modules.alipay.domain;

import com.entertainment.project.base.BaseBean;

/**
 * Created by Sick on 2016/11/2.
 */
public class DataValue extends BaseBean {
    private String name;
    private int img;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public DataValue() {
        sort = SORT_2;
        type = TYPE_CONTENT_2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
