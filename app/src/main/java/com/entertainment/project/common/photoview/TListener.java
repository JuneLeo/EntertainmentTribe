package com.entertainment.project.common.photoview;

/**
 * Created by Sick on 2017/2/17.
 * T实体类的监听    返回图片url和图片name
 */
public interface TListener<T> {
    /**
     * @param t 图片url
     * @return
     */
    String getImageUrl(T t);

    /**
     * @param t 图片名
     * @return
     */
    String getImageName(T t);
}
