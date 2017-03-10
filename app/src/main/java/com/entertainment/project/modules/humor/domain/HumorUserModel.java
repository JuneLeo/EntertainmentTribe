package com.entertainment.project.modules.humor.domain;

import com.entertainment.project.base.BaseModel;

import java.io.Serializable;

/**
 * Created by Sick on 2017/2/16.
 */
public class HumorUserModel extends BaseModel implements Serializable {
    private String userImg;
    private String userName;
    private String userLevel;
    private String content;
    private Long userId;
    private String contentGoodNum;
    private String position;
    private boolean isFirst = false;

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContentGoodNum() {
        return contentGoodNum;
    }

    public void setContentGoodNum(String contentGoodNum) {
        this.contentGoodNum = contentGoodNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }
}
