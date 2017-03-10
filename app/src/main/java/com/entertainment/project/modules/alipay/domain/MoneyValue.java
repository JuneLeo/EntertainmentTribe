package com.entertainment.project.modules.alipay.domain;

import com.entertainment.project.base.BaseBean;

/**
 * Created by Sick on 2016/11/2.
 */
public class MoneyValue extends BaseBean {
    private String money;

    public MoneyValue() {
        sort = SORT_3;
        type = TYPE_CONTENT_3;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
