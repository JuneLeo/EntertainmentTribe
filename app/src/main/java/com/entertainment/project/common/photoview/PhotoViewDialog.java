package com.entertainment.project.common.photoview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.entertainment.project.R;
import com.entertainment.project.base.BaseDialogFragment;
import com.entertainment.project.common.utils.ToastUtil;
import com.entertainment.project.common.utils.Util;
import com.entertainment.project.common.retrofit.RxUtils;
import java.util.ArrayList;
import java.util.Iterator;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Sick on 2017/2/17.
 * <p>
 * 十分强大的图片查看器，直接传入实体类对象就可以，对象中的url在Tlistener中设置
 * <p>
 * 图片查看器直接传入实体类Entity
 * 必须实现的方法，setData 1.两个参数（ArrayList<T>,T）2.一个参数（T）
 * setTListener   此方法必须实现，返回T中的图片url和图片name
 * <p>
 * <p>
 */
public class PhotoViewDialog<T> extends BaseDialogFragment {

    @Bind(R.id.id_vp)
    HackyViewPager mIdVp;
    @Bind(R.id.tv_image_name)
    TextView mTvImageName;
    @Bind(R.id.tv_image_num)
    TextView mTvImageNum;
    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.iv_download)
    ImageView mIvDownload;

    private ArrayList<T> tList;
    private T t;
    private PhotoViewAdapter adapter;
    public TListener<T> listener;
    private String TAG = PhotoViewDialog.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTheme(com.baoyz.actionsheet.R.style.ActionSheetStyleWeCaiWu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_photo_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mIdVp.setOnPageChangeListener(new PhotoViewOnPagerChangeListener());
        adapter = new PhotoViewAdapter(listener, tList, getActivity());
        adapter.setItemClickListener(new PhotoViewAdapter.VHClickListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                getDialog().dismiss();
            }
        });
        getImageData(tList).subscribe(new Subscriber<ArrayList<T>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showLong("图片查看器发生异常");
            }

            @Override
            public void onNext(ArrayList<T> ts) {


                mIdVp.setAdapter(adapter);

                if (t != null) {
                    mIdVp.setCurrentItem(tList.indexOf(t));
                    updateUI(tList.indexOf(t));
                } else {
                    mIdVp.setCurrentItem(0);
                    updateUI(0);
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.iv_back, R.id.iv_download})
    public void onClick(View view) {
        if (Util.isFastDoubleClick()) {
            return;
        }

        switch (view.getId()) {
            case R.id.iv_back:
                getDialog().dismiss();
                break;
            case R.id.iv_download:
                Glide.with(getActivity())
                        .load(listener.getImageUrl(t))
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                if (resource != null)
                                    Util.saveLocalImage(resource, (AppCompatActivity) getActivity());
                            }
                        });
                break;
        }
    }

    /**
     * 刷新UI
     *
     * @param position
     */
    private void updateUI(int position) {
        this.t = tList.get(position);
        mTvImageNum.setText(position + 1 + "/" + tList.size());
        if (listener != null) {
            mTvImageName.setText(listener.getImageName(tList.get(position)));
        }
    }


    /**
     * @param tList list集合
     * @param t     选中的list集合中的某个对象
     * @return
     */
    public PhotoViewDialog setData(ArrayList<T> tList, T t) {
        this.tList = new ArrayList<>();
        this.tList.addAll(tList);
        this.t = t;
        return this;
    }

    /**
     * @param t 含有图片的实体类
     * @return
     */
    public PhotoViewDialog setData(T t) {
        this.tList = new ArrayList<>();
        this.tList.add(t);
        return this;
    }

    /**
     * @param listener 实体类中的
     * @return
     */
    public PhotoViewDialog setTListener(TListener<T> listener) {
        this.listener = listener;
        return this;
    }

    /**
     * viewpager监听
     */
    private class PhotoViewOnPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            updateUI(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * Rxjava    过滤数据
     *
     * @param tList
     * @return
     */
    public Observable<ArrayList<T>> getImageData(ArrayList<T> tList) {
        return Observable.just(tList).map(new Func1<ArrayList<T>, ArrayList<T>>() {
            @Override
            public ArrayList<T> call(ArrayList<T> humorModels) {
                Iterator<T> iterator = humorModels.iterator();
                while (iterator.hasNext()) {
                    T model = iterator.next();
                    if (listener != null && TextUtils.isEmpty(listener.getImageUrl(model))) {
                        iterator.remove();
                    }
                }
                return tList;
            }
        }).compose(RxUtils.rxSchedulerHelper())
                .compose(this.bindToLifecycle());
    }


}
