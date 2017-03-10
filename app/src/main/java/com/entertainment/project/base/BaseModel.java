package com.entertainment.project.base;

/**
 * Created by Sick on 2017/2/17.
 */
public abstract class BaseModel implements Comparable<BaseModel> {

    private int modelType = -1;

    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

    @Override
    public int compareTo(BaseModel another) {
        return modelType - another.modelType;
    }
}
