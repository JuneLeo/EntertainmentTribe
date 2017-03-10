package com.entertainment.project.common.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.entertainment.project.R;
import com.entertainment.project.base.BaseActivity;
import com.entertainment.project.common.assist.LoadingDialogFragment;

import java.util.ArrayList;

/**
 * Created by Sick on 2016/10/27.
 */
public class LoadingUtil {

    public static final String synchronizeds = "synchronized";

    private static ArrayList<Dialog> dialogArrayList = new ArrayList<Dialog>();

    private static int startWaitingCount=0;
    private static boolean isWaitingHold=false;

    private static LoadingDialogFragment loadingDialogFragment;

    private static Dialog waitingDialog;

    public synchronized static void startWaitingDialog(Context context) {

//        synchronized (Utils.synchronizeds) {
//            // if(waitingDialog==null){
//            waitingDialog = new Dialog(context, R.style.waiting);
//            dialogArrayList.add(waitingDialog);
//            // dialog.setCanceledOnTouchOutside(true);
//            waitingDialog.setContentView(R.layout.waiting);
//            waitingDialog.setCanceledOnTouchOutside(false);
//            if (!waitingDialog.isShowing()) {
//                // dialog.setCancelable(false);
//                waitingDialog.show();
//                // }else{
//                // if(waitingDialog!=null&&!waitingDialog.isShowing()){
//                // waitingDialog.show();
//                // }
//                //
//                // }
//            }
//        }



        //******20150915  去除重复弹出等待对话框  hcyang
        startWaitingCount++;
        if (startWaitingCount==1) {
            if (!isWaitingHold) {
                if (context!=null && context instanceof BaseActivity) {
                    BaseActivity baseActivity = (BaseActivity) context;
                    startLoadingDialog(baseActivity);
                }
            }
        }

        isWaitingHold=false;



    }

    //******20150915  去除重复弹出等待对话框  hcyang
    //是否强制等待下一个startWaitingDialog,在下一个startWaitingDialog之前不关闭等待对话框
    public synchronized static void setIsWaitingHold(boolean hold){
        isWaitingHold=hold;
    }

    //******20150915  去除重复弹出等待对话框  hcyang
    //支持重复调用
    public synchronized static void startLoadingDialog(BaseActivity context) {
        try {
            if (loadingDialogFragment == null) {
                loadingDialogFragment = new LoadingDialogFragment();
                loadingDialogFragment.setStyle(R.style.waiting, R.style.waiting);
            }else{
                if (loadingDialogFragment.isAdded()){
                    //如果不是当前界面打开的等待对话框,则先关闭

                    if (!context.equals(loadingDialogFragment.getActivity())) {
                        closeLoadingDialog();
                    }

                }

            }


            //如果已经显示等待对话框,则不重复显示
            if (!loadingDialogFragment.isAdded() && !loadingDialogFragment.isVisible() ) {
                loadingDialogFragment.show(context.getSupportFragmentManager(), "loading");
                context.getSupportFragmentManager().executePendingTransactions();
            }
            //}
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public synchronized static void closeLoadingDialog() {
        try {
            if (loadingDialogFragment != null && !loadingDialogFragment.isHidden() ) {
                loadingDialogFragment.dismiss();


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized static void closeWaitingDialog() {
//        synchronized (Utils.synchronizeds) {
//
//            for (Dialog dialog :dialogArrayList) {
//                if (dialog != null && dialog.isShowing()) {
//                    dialog.dismiss();
//                }
//            }
//            dialogArrayList.clear();
//        }

        //******20150915  去除重复弹出等待对话框  hcyang
        startWaitingCount--;
        if (startWaitingCount<0){
            startWaitingCount=0;
        }
        if (!isWaitingHold) {
            if (startWaitingCount == 0) {
                closeLoadingDialog();
            }
        }
    }

    public synchronized static void resetWaitingDialog(){
        startWaitingCount=0;
        isWaitingHold=false;
        closeLoadingDialog();
    }

    public static void closeLoginWaitingDialog() {
        synchronized (LoadingUtil.synchronizeds) {
            try {

                for (Dialog dialog : dialogArrayList) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                dialogArrayList.clear();
            }

        }
    }
    public static void startLoginWaitingDialog(Context context) {
        synchronized (LoadingUtil.synchronizeds) {
            try {

                // if(waitingDialog==null){
                waitingDialog = new Dialog(context, R.style.waiting);
                dialogArrayList.add(waitingDialog);
                // dialog.setCanceledOnTouchOutside(true);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.waiting,null);
                View imageView = view.findViewById(R.id.progress);
                RotateAnimation animation =new RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF,
                        0.5f, Animation.RELATIVE_TO_SELF,0.5f);
                animation.setDuration(2000);
                animation.setRepeatCount(-1);
                animation.setRepeatMode(Animation.REVERSE);
                imageView.setAnimation(animation);
                waitingDialog.setContentView(view);

                waitingDialog.setCanceledOnTouchOutside(false);
                if (!waitingDialog.isShowing()) {
                    // dialog.setCancelable(false);
                    waitingDialog.show();
                    // }else{
                    // if(waitingDialog!=null&&!waitingDialog.isShowing()){
                    // waitingDialog.show();
                    // }
                    //
                    // }
                }
            }catch (Exception e){
                e.printStackTrace();

            }
        }
    }


    private synchronized  static Dialog getInstance(Context context) {
        if (waitingDialog == null) {
            waitingDialog = new Dialog(context, R.style.waiting);
        }
        return waitingDialog;
    }
}
