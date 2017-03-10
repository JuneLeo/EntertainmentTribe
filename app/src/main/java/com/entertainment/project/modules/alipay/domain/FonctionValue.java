package com.entertainment.project.modules.alipay.domain;

import com.entertainment.project.base.BaseBean;

/**
 * Created by Sick on 2016/11/2.
 */
public class FonctionValue extends BaseBean {

    private String name;
    private String content;
    private int img;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public FonctionValue() {
        sort = SORT_4;
        type = TYPE_CONTENT_4;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
