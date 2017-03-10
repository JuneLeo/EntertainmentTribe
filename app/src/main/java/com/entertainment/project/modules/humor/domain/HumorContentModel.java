package com.entertainment.project.modules.humor.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sick on 2017/3/2.
 */
public class HumorContentModel implements Serializable{
    private String content;
    private ArrayList<HumorUserModel> commentators;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<HumorUserModel> getCommentators() {
        return commentators;
    }

    public void setCommentators(ArrayList<HumorUserModel> commentators) {
        this.commentators = commentators;
    }
}
