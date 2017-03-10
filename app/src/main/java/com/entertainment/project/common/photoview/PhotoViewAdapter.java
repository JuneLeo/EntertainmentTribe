package com.entertainment.project.common.photoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.actionsheet.ActionSheet;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.entertainment.project.common.utils.ScreenUtils;
import com.entertainment.project.common.utils.Util;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Sick on 2017/2/17.
 */
public class PhotoViewAdapter<T> extends PagerAdapter {
    private ArrayList<T> tList;
    public TListener<T> listener;
    private Context mContext;

    public PhotoViewAdapter(TListener<T> listener, ArrayList<T> tList, Context mContext) {
        this.listener = listener;
        this.tList = tList;
        this.mContext = mContext;
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                if (vhClickListener != null) {
                    vhClickListener.onPhotoTap(view, x, y);
                }
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });


        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

//                ActionSheet.createBuilder(mContext, ((AppCompatActivity) mContext).getSupportFragmentManager())
//                        .setCancelButtonTitle("取消")
//                        .setOtherButtonTitles("保存图片")
//                        .setTag("tax")
//                        .setCancelableOnTouchOutside(true).setListener(new HummorSaveImgSheetListener(listener.getImageUrl(tList.get(position)))).show();


                return true;
            }
        });


        if (listener != null && !TextUtils.isEmpty(listener.getImageUrl(tList.get(position)))) {
            Glide.with(photoView.getContext())
                    .load(listener.getImageUrl(tList.get(position)))
                    .fitCenter()
                    .override(ScreenUtils.getScreenWidth(photoView.getContext()), ScreenUtils.getScreenHeight(photoView.getContext()))
                    .into(photoView);
            container.addView(photoView);
        }

        return photoView;
    }

    @Override
    public int getCount() {
        return tList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface VHClickListener {

        void onPhotoTap(View view, float x, float y);

    }

    public VHClickListener vhClickListener;

    public void setItemClickListener(VHClickListener vhClickListener) {
        this.vhClickListener = vhClickListener;
    }


    private class HummorSaveImgSheetListener implements ActionSheet.ActionSheetListener {
        private String url;

        public HummorSaveImgSheetListener(String urls) {
            url = urls;
        }

        @Override
        public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

        }

        @Override
        public void onOtherButtonClick(ActionSheet actionSheet, int index) {
            if (index == 0) {

                Glide.with(mContext)
                        .load(url)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                if (resource != null)
                                    Util.saveLocalImage(resource, (AppCompatActivity) mContext);
                            }
                        });
            }
        }
    }

}
