package com.entertainment.project.modules.alipay.domain;

import com.entertainment.project.base.BaseBean;

/**
 * Created by Sick on 2016/11/2.
 */
public class UserValue extends BaseBean {
    private String name;
    private String logoUrl;
    private String userUrl;

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public UserValue() {
        sort = SORT_1;
        type = TYPE_CONTENT_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }


}
