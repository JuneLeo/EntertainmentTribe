package com.entertainment.project.base;

/**
 * Created by Sick on 2016/8/23.
 */
public abstract class BaseBean implements Comparable<BaseBean> {
    public int sort = -1;
    public int type = -1;

    public int SORT_1 = 1;
    public int SORT_2 = 2;
    public int SORT_3 = 3;
    public int SORT_4 = 4;
    public int SORT_5 = 5;
    public int SORT_6 = 6;
    public int SORT_7 = 7;


    public static int TYPE_CONTENT_HEADER = 0;
    public static int TYPE_CONTENT_1 = 1;
    public static int TYPE_CONTENT_2 = 2;
    public static int TYPE_CONTENT_3 = 3;
    public static int TYPE_CONTENT_4 = 4;
    public static int TYPE_CONTENT_5 = 5;
    public static int TYPE_CONTENT_6 = 6;
    public static int TYPE_CONTENT_7 = 7;


    @Override
    public int compareTo(BaseBean another) {
        return sort - another.sort;
    }
}
