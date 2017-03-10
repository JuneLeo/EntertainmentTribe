package com.entertainment.project.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.entertainment.project.R;
import com.trello.rxlifecycle.components.support.RxDialogFragment;

/**
 * Created by Sick on 2017/2/17.
 */
public abstract class BaseDialogFragment extends RxDialogFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.base_dialog_fragment);
    }

    /**
     * 显示dialog
     */
    public void show(Context mContext,String tag) {
        FragmentManager manager = ((AppCompatActivity) mContext).getSupportFragmentManager();
        if (manager.findFragmentByTag(tag) == null) {
            super.show(manager, tag);
        }
    }
}
