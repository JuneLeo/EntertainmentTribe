package com.entertainment.project.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;

import butterknife.ButterKnife;

/**
 * Created by Sick on 2017/2/23.
 */
public abstract class BaseAppActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() == 0) {
            throw new IllegalArgumentException("Must override getLayout method!!");
        }
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        if (null != getIntent()) {
            handleIntent(getIntent());
        }

        findViews();

        bindData();

    }

    /**
     * 获取布局文件id
     *
     * @return
     */
    @NonNull
    protected abstract int getLayoutId();


    /**
     * 获取Intent
     */
    protected abstract void handleIntent(Intent intent);

    /**
     * 初始化控件
     */
    protected abstract void findViews();

    /**
     * 绑定数据
     */
    protected abstract void bindData();


    /**
     * 布局中Fragment的id
     */
    @NonNull
    protected int getFragmentContentId() {
        return 0;
    }

    /**
     * 添加fragment 调用该方法会重新刷新数据
     * <p>
     * 需重写getFragmentContentId()方法
     *
     * @param fragment
     */
    protected void addFragment(BaseFragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    /**
     * 切换fragment 调用该方法不会重新刷新数据
     * <p>
     * 需重写getFragmentContentId()方法
     *
     * @param from 当前显示的fragment
     * @param to   将要切换显示的fragment
     */
    protected void switchFragment(BaseFragment from, BaseFragment to) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (from == null) {
            transaction.add(getFragmentContentId(), to).commit();
        } else {
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(getFragmentContentId(), to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    /**
     * 移除fragment
     */
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
