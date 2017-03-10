package com.entertainment.project.base;

import android.support.v4.app.Fragment;

import com.trello.rxlifecycle.components.support.RxFragment;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Sick on 2017/2/10.
 */
public abstract class BaseFragment extends RxFragment {
//    protected Subscription subscription;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unsubscribe();
    }

//    /**
//     * 观察者取消订阅
//     */
//    public void unsubscribe() {
//        if (subscription != null && !subscription.isUnsubscribed()) {
//            subscription.unsubscribe();
//        }
//    }
}
