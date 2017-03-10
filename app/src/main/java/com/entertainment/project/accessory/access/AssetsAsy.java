package com.entertainment.project.accessory.access;

import android.content.Context;
import android.view.View;


import com.entertainment.project.accessory.Conf;

import java.io.File;

/**
 * Created by Sick on 2016/11/17.
 */
public class AssetsAsy extends Accessory {
    private View view;
    private int layoutID;
    private String title;
    private boolean isVisible = false;

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public AssetsAsy(File file, int accessoryType, Context context, Conf.BillType billType) {
        super(file, accessoryType, context, billType);
    }

    public AssetsAsy() {
    }

    public AssetsAsy(View view, int accessoryType) {
        this.view = view;
        this.accessoryType = accessoryType;
    }

    public AssetsAsy(int layoutID, int accessoryType) {
        this.layoutID = layoutID;
        this.accessoryType = accessoryType;
    }

    public AssetsAsy(int accessoryType, String title) {
        this.accessoryType = accessoryType;
        this.title = title;
    }


    public View getView() {
        return view;
    }

    public int getLayoutID() {
        return layoutID;
    }

}
