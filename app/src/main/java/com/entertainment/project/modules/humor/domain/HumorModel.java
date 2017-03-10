package com.entertainment.project.modules.humor.domain;

import java.io.Serializable;

/**
 * Created by Sick on 2017/2/16.
 */
public class HumorModel implements Serializable{
    public HumorUserModel humorUserModel;
    public String content;
    public String img;
    public String praiseNum;
    public String commentNum;
    public String visitor;
    public String comment;
    public Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(String praiseNum) {
        this.praiseNum = praiseNum;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getVisitor() {
        return visitor;
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public HumorUserModel getHumorUserModel() {
        return humorUserModel;
    }

    public void setHumorUserModel(HumorUserModel humorUserModel) {
        this.humorUserModel = humorUserModel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
