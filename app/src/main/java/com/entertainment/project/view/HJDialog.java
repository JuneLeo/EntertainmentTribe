package com.entertainment.project.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.entertainment.project.R;

/**
 * Created by Sick on 2016/6/28.
 */
public class HJDialog extends Dialog {

    public HJDialog(Context context) {
        this(context, 0);
    }

    public HJDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    public static class Builder {
        //上下文
        private Context context;

        //三个按钮
        private CharSequence mBtnOneText, mBtnTwoText, mBtnThreeText = null;
        private int mBtnOneColor,mBtnTwoColor,mBtnThreeColor = 0;
        private int mBtnSize = 0;
        //三个监听
        private OnButtonOneListener mOnButtonOneListener = null;
        private OnButtonTwoListener mOnButtonTwoListener = null;
        private OnButtonThreeListener mOnButtonThreeListener = null;

        private OnButtonListener mOnButtonListener = null;


        //设置头
        private CharSequence mHeaderText = null;
        private int mHeaderTextSize = 0;
        private int mHeaderTextColor = 0;
        //设置内容
        private CharSequence mainContent = null;
        private int mainContentSize = 0;
        private CharSequence titleContent = null;
        private int titleContentSize = 0;
        private CharSequence assistContent = null;
        private int assistContentSize = 0;
        //组件
        private TextView tvHeader;
        private TextView tvTitleContent;
        private TextView tvMainContent;
        private TextView tvAssistContent;
        private Button btnOne, btnTwo, btnThree;
        private LinearLayout llContent;
        //三条线
        private View vBtnTopLine;
        private View vBtnLeftLine;
        private View vBtnRightLine;

        private int gravity = Gravity.LEFT;// 文字方向



        public Builder(Context context) {
            this.context = context;
        }

        //设置header默认不显示
        public Builder setHeader(CharSequence mHeaderText) {
            this.setHeader(mHeaderText, 0);
            return this;
        }

        public Builder setHeader(CharSequence mHeaderText, int mHeaderTextSize) {
            this.setHeader(mHeaderText,mHeaderTextSize,0);
            return this;
        }
        public Builder setHeader(CharSequence mHeaderText, int mHeaderTextSize,int mHeaderTextColor) {
            this.mHeaderTextColor = mHeaderTextColor;
            this.mHeaderText = mHeaderText;
            this.mHeaderTextSize = mHeaderTextSize;
            return this;
        }

        //======================================旧方法==========================================

        //第一个按钮
        public Builder setButtonOneListener(CharSequence mBtnText, OnButtonOneListener mOnButtonOneListener) {
            this.setButtonOneListener(mBtnText,0,mOnButtonOneListener);
            return this;
        }
        public Builder setButtonOneListener(CharSequence mBtnText,int mBtnColor, OnButtonOneListener mOnButtonOneListener) {
            this.mBtnOneColor = mBtnColor;
            this.mOnButtonOneListener = mOnButtonOneListener;
            this.mBtnOneText = mBtnText;
            return this;
        }


        //第二个按钮
        public Builder setButtonTwoListener(CharSequence mBtnText, OnButtonTwoListener mOnButtonTwoListener) {
            this.setButtonTwoListener(mBtnText,0,mOnButtonTwoListener);
            return this;
        }
        public Builder setButtonTwoListener(CharSequence mBtnText,int mBtnColor, OnButtonTwoListener mOnButtonTwoListener) {
            this.mBtnTwoColor = mBtnColor;
            this.mOnButtonTwoListener = mOnButtonTwoListener;
            this.mBtnTwoText = mBtnText;
            return this;
        }


        //第三个按钮
        public Builder setButtonThreeListener(CharSequence mBtnText, OnButtonThreeListener mOnButtonThreeListener) {
            this.setButtonThreeListener(mBtnText,0,mOnButtonThreeListener);
            return this;
        }
        public Builder setButtonThreeListener(CharSequence mBtnText,int mBtnColor, OnButtonThreeListener mOnButtonThreeListener) {
            this.mBtnThreeColor = mBtnColor;
            this.mOnButtonThreeListener = mOnButtonThreeListener;
            this.mBtnThreeText = mBtnText;
            return this;
        }
        //======================================旧方法==========================================

        //======================================新方法==========================================

        //第一个按钮
        public Builder setButtonOne(CharSequence mBtnText) {
            this.setButtonOne(mBtnText,0);
            return this;
        }
        public Builder setButtonOne(CharSequence mBtnText,int mBtnColor) {
            this.mBtnOneColor = mBtnColor;
            this.mBtnOneText = mBtnText;
            return this;
        }


        //第二个按钮
        public Builder setButtonTwo(CharSequence mBtnText) {
            this.setButtonTwo(mBtnText,0);
            return this;
        }
        public Builder setButtonTwo(CharSequence mBtnText,int mBtnColor) {
            this.mBtnTwoColor = mBtnColor;
            this.mBtnTwoText = mBtnText;
            return this;
        }


        //第三个按钮
        public Builder setButtonThree(CharSequence mBtnText) {
            this.setButtonThree(mBtnText,0);
            return this;
        }
        public Builder setButtonThree(CharSequence mBtnText,int mBtnColor) {
            this.mBtnThreeColor = mBtnColor;
            this.mBtnThreeText = mBtnText;
            return this;
        }

        /**
         * 单独提取出来设置监听
         * @param mOnButtonListener
         */
        public void setmOnButtonListener(OnButtonListener mOnButtonListener) {
            this.mOnButtonListener = mOnButtonListener;
        }

        //======================================新方法==========================================


        //设置按钮字体尺寸
        public Builder setButtonSize(int mBtnSize) {
            this.mBtnSize = mBtnSize;
            return this;
        }

        public Builder setMainContent(CharSequence mainContent, int mainContentSize) {
            this.mainContent = mainContent;
            this.mainContentSize = mainContentSize;
            return this;
        }

        public Builder setMainContent(CharSequence mainContent) {
            this.setMainContent(mainContent, 0);
            return this;
        }


        public Builder setTitleContent(CharSequence titleContent, int titleContentSize) {
            this.titleContent = titleContent;
            this.titleContentSize = titleContentSize;
            return this;
        }

        public Builder setTitleContent(CharSequence titleContent) {
            this.setTitleContent(titleContent, 0);
            return this;
        }


        public Builder setAssistContent(CharSequence assistContent, int assistContentSize) {
            this.assistContent = assistContent;
            this.assistContentSize = assistContentSize;
            return this;
        }
        public Builder setMainContentGravity(int gravity){
            this.gravity = gravity;
            return this;
        }

        public Builder setAssistContent(CharSequence assistContent) {
            this.setAssistContent(assistContent, 0);
            return this;
        }



        public HJDialog onCreate() {
            final HJDialog dialog = new HJDialog(context, R.style.Dialog);
            //初始化字体
//            initSize();
//            initColor();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogLayoutView = inflater.inflate(R.layout.dialog_new,
                    null);

            initView(dialogLayoutView);

            //=========================设置头和尾形状----开始===============================
            if (mHeaderText==null&&(mBtnOneText != null || mBtnTwoText != null|| mBtnThreeText != null)){
                llContent.setBackgroundResource(R.drawable.bg_dialog_content_top);
            }else if (mHeaderText!=null && (mBtnOneText == null && mBtnTwoText == null&& mBtnThreeText == null)){
                llContent.setBackgroundResource(R.drawable.bg_dialog_content_bottom);
            }else if (mHeaderText==null&&mBtnOneText == null && mBtnTwoText == null&& mBtnThreeText == null){
                llContent.setBackgroundResource(R.drawable.bg_dialog_content);
            }

            //=========================设置头和尾形状----结束===============================


            /**
             * 设置header内容
             */
            if (mHeaderText == null) {
                tvHeader.setVisibility(View.GONE);
            } else {
                tvHeader.setVisibility(View.VISIBLE);
                tvHeader.setText(mHeaderText);
            }

            /**
             * 设置单击关闭
             */
            if (mBtnOneText == null && mBtnTwoText == null&& mBtnThreeText == null) {
                dialog.setCanceledOnTouchOutside(true);
                vBtnTopLine.setVisibility(View.GONE);
            } else {
                dialog.setCanceledOnTouchOutside(false);
                vBtnTopLine.setVisibility(View.VISIBLE);
            }

            /**
             * 设置线条
             */
            if (mBtnOneText!=null&&mBtnTwoText!=null){
                vBtnLeftLine.setVisibility(View.VISIBLE);
            }
            if (mBtnTwoText!=null&&mBtnThreeText!=null){
                vBtnRightLine.setVisibility(View.VISIBLE);
            }
            if (mBtnOneText!=null&&mBtnThreeText!=null){
                vBtnLeftLine.setVisibility(View.VISIBLE);
            }

            /**
             * 设置main内容和assist内容  还有标题
             */
            if (mainContent == null) {
                tvMainContent.setVisibility(View.GONE);
            } else {
                tvMainContent.setVisibility(View.VISIBLE);
                tvMainContent.setText(mainContent);
            }
            if (assistContent == null) {
                tvAssistContent.setVisibility(View.GONE);
            } else {
                tvAssistContent.setVisibility(View.VISIBLE);
                tvAssistContent.setText(assistContent);
            }
            if (titleContent == null) {
                tvTitleContent.setVisibility(View.GONE);
            } else {
                tvTitleContent.setVisibility(View.VISIBLE);
                tvTitleContent.setText(titleContent);
            }


            tvMainContent.setGravity(gravity);

            /**
             * 设置按钮文字
             */
            if (mBtnOneText!=null){
                btnOne.setText(mBtnOneText);
                btnOne.setVisibility(View.VISIBLE);
            }else{
                btnOne.setVisibility(View.GONE);
            }

            if (mBtnTwoText!=null){
                btnTwo.setText(mBtnTwoText);
                btnTwo.setVisibility(View.VISIBLE);
            }else{
                btnTwo.setVisibility(View.GONE);
            }

            if (mBtnThreeText!=null){
                btnThree.setText(mBtnThreeText);
                btnThree.setVisibility(View.VISIBLE);
            }else{
                btnThree.setVisibility(View.GONE);
            }



            //========================================旧方法========================================================
            /**
             * 设置one按钮
             */
            if (mBtnOneText != null && mOnButtonOneListener != null) {
                btnOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                        mOnButtonOneListener.OnClick();
                    }
                });
            }
            /**
             * 设置two按钮
             */
            if (mBtnTwoText != null&&mOnButtonTwoListener!=null) {
                btnTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                        mOnButtonTwoListener.OnClick();
                    }
                });
            }
            /**
             * 设置three按钮
             */
            if (mBtnThreeText != null && mOnButtonThreeListener != null) {
                btnThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                        mOnButtonThreeListener.OnClick();
                    }
                });
            }
            //========================================旧方法========================================================


            //=========================================新方法=======================================================
            /**
             * 设置one按钮
             */
            if (mBtnOneText != null && mOnButtonListener != null) {
                btnOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        mOnButtonListener.onOneClick();
                    }
                });
            }
            /**
             * 设置two按钮
             */
            if (mBtnTwoText != null && mOnButtonListener != null) {
                btnTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        mOnButtonListener.onTwoClick();
                    }
                });
            }
            /**
             * 设置three按钮
             */
            if (mBtnThreeText != null &&mOnButtonListener != null) {
                btnThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                        mOnButtonListener.onThreeClick();
                    }
                });
            }

            //=========================================新方法=======================================================



           initSizeAndColor();

            dialog.setContentView(dialogLayoutView);
            return dialog;
        }

        /**
         * 初始化控件
         * @param dialogLayoutView
         */
        private void initView(View dialogLayoutView) {
            tvHeader = (TextView) dialogLayoutView.findViewById(R.id.tv_dialog_header);
            tvTitleContent = (TextView) dialogLayoutView.findViewById(R.id.tv_dialog_title_content);
            tvMainContent = (TextView) dialogLayoutView.findViewById(R.id.tv_dialog_main_content);
            tvAssistContent = (TextView) dialogLayoutView.findViewById(R.id.tv_dialog_assist_content);
            btnOne = (Button) dialogLayoutView.findViewById(R.id.btn_dialog_one);
            btnTwo = (Button) dialogLayoutView.findViewById(R.id.btn_dialog_two);
            btnThree = (Button) dialogLayoutView.findViewById(R.id.btn_dialog_three);

            llContent = (LinearLayout) dialogLayoutView.findViewById(R.id.ll_dialog_content);

            vBtnTopLine = dialogLayoutView.findViewById(R.id.v_btn_top_line);
            vBtnLeftLine = dialogLayoutView.findViewById(R.id.v_btn_left_line);
            vBtnRightLine = dialogLayoutView.findViewById(R.id.v_btn_right_line);

        }

        /**
         * 设置字体和颜色
         */
        private void initSizeAndColor() {
            if (mHeaderTextSize!=0){
                tvHeader.setTextSize(mHeaderTextSize);
            }
            if (mHeaderTextColor!=0){
                tvHeader.setTextColor(mHeaderTextColor);
            }
            if (mainContentSize!=0){
                tvMainContent.setTextSize(mainContentSize);
            }
            if (assistContentSize!=0){
                tvAssistContent.setTextSize(assistContentSize);
            }
            if (mBtnSize!=0){
                btnOne.setTextSize(mBtnSize);
                btnTwo.setTextSize(mBtnSize);
                btnThree.setTextSize(mBtnSize);
            }
            if (mBtnOneColor!=0){
                btnOne.setTextColor(mBtnOneColor);
            }
            if (mBtnTwoColor!=0){
                btnTwo.setTextColor(mBtnTwoColor);
            }
            if (mBtnThreeColor!=0){
                btnThree.setTextColor(mBtnThreeColor);
            }
        }




//        /**
//         * 初始化颜色
//         */
//        private void initColor() {
//            if(mHeaderTextColor==0){
//                mHeaderTextColor = Color.parseColor("#000000");
//            }
//            if (mBtnOneColor==0){
//                mBtnOneColor = Color.parseColor("#000000");
//            }
//            if (mBtnTwoColor==0){
//                mBtnOneColor = Color.parseColor("#000000");
//            }
//            if (mBtnThreeColor==0){
//                mBtnOneColor = Color.parseColor("#000000");
//            }
//        }
//
//        /**
//         * 初始化头部字体
//         */
//        private void initSize() {
//
//            if (mHeaderTextSize == 0) {
//                mHeaderTextSize = 13;
//            }
//            if (mBtnSize == 0) {
//                mBtnSize = 13;
//            }
//            if (mainContentSize == 0) {
//                mainContentSize = 13;
//            }
//            if (assistContentSize == 0) {
//                assistContentSize = 13;
//            }
//
//        }


        public interface OnButtonTwoListener {
            void OnClick();
        }

        public interface OnButtonThreeListener {
            void OnClick();
        }

        public interface OnButtonOneListener {
            void OnClick();
        }

        public interface OnButtonListener{
            void onOneClick();
            void onTwoClick();
            void onThreeClick();
        }

    }
    public static Builder createBuilder(Context context){
        return new Builder(context);
    }
}
