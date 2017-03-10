package com.entertainment.project.modules.humor.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.actionsheet.ActionSheet;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.entertainment.project.R;
import com.entertainment.project.base.BaseRecyclerViewAdapter;
import com.entertainment.project.modules.humor.domain.HumorModel;
import com.entertainment.project.common.utils.ScreenUtils;
import com.entertainment.project.common.utils.Util;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sick on 2017/2/16.
 */
public class HumorAdapter extends BaseRecyclerViewAdapter<HumorModel> {


    public HumorAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getViewType(int position) {
        return 0;
    }

    @Override
    protected RecyclerView.ViewHolder CreateVH(ViewGroup parent, int viewType) {
        return new VHHumor(inflater.inflate(R.layout.item_humor, parent, false));
    }

    @Override
    protected void fillData(RecyclerView.ViewHolder holder, HumorModel model, int position) {
        if (holder instanceof VHHumor) {
            if (!TextUtils.isEmpty(model.getHumorUserModel().getUserName())) {
                ((VHHumor) holder).mTvUserName.setText(model.getHumorUserModel().getUserName());
            } else {
                ((VHHumor) holder).mTvUserName.setText("匿名用户");
            }
            if (!TextUtils.isEmpty(model.getHumorUserModel().getUserImg())) {
                Glide.with(((VHHumor) holder).mIvUserImg.getContext())
                        .load(model.getHumorUserModel().getUserImg()).
                        into(((VHHumor) holder).mIvUserImg);
            } else {
                ((VHHumor) holder).mIvUserImg.setImageBitmap(BitmapFactory.decodeResource(((VHHumor) holder).mIvUserImg.getResources(), R.drawable.humor_user_empty));
            }
            ((VHHumor) holder).mTvContent.setText(model.getContent().trim());
            if (!TextUtils.isEmpty(model.getImg())) {
                ((VHHumor) holder).mIvImg.setVisibility(View.VISIBLE);
                Glide.with(((VHHumor) holder).mIvImg.getContext())
                        .load(model.getImg())
                        .override(ScreenUtils.getScreenWidth(((VHHumor) holder).mIvImg.getContext()), ScreenUtils.getScreenHeight(((VHHumor) holder).mIvImg.getContext()))
                        .fitCenter()
                        .into(((VHHumor) holder).mIvImg);

            } else {
                ((VHHumor) holder).mIvImg.setVisibility(View.GONE);
            }

            ((VHHumor) holder).mTvPariseNum.setText(model.getPraiseNum());
            ((VHHumor) holder).mTvCommentNum.setText(model.getCommentNum());
            if (!TextUtils.isEmpty(model.getVisitor()) && !TextUtils.isEmpty(model.getComment())) {
                ((VHHumor) holder).mLlComment.setVisibility(View.VISIBLE);
                ((VHHumor) holder).mTvComment.setText(model.getVisitor() + model.getComment());
            } else {
                ((VHHumor) holder).mLlComment.setVisibility(View.GONE);
            }

            ((VHHumor) holder).mIvImg.setOnClickListener(v -> {
                if (vhClickListener != null) {
                    vhClickListener.onItemClick(position, v, model, holder);
                }
            });

            ((VHHumor) holder).mIvImg.setOnLongClickListener(v -> {
                ActionSheet.createBuilder(mContext, ((AppCompatActivity) mContext).getSupportFragmentManager())
                        .setCancelButtonTitle("取消")
                        .setOtherButtonTitles("保存图片")
                        .setTag("tax")
                        .setCancelableOnTouchOutside(true).setListener(new HummorSaveImgSheetListener(model)).show();
                return true;
            });

            ((VHHumor) holder).mTvContent.setOnClickListener(v -> {
                if (vhClickListener != null)
                    vhClickListener.onItemClick(position, v, model, holder);
            });

            ((VHHumor) holder).mTvContent.setOnLongClickListener(v -> {
                if (vhClickListener != null)
                    vhClickListener.onItemLongClick(position, v, model, holder);

                return true;
            });


        }
    }

    public class VHHumor extends BaseViewHolder {
        @Bind(R.id.iv_user_img)
        CircleImageView mIvUserImg;
        @Bind(R.id.tv_user_name)
        TextView mTvUserName;
        @Bind(R.id.tv_content)
        TextView mTvContent;
        @Bind(R.id.iv_img)
        ImageView mIvImg;
        @Bind(R.id.tv_parise_num)
        TextView mTvPariseNum;
        @Bind(R.id.tv_comment_num)
        TextView mTvCommentNum;
        @Bind(R.id.tv_comment)
        TextView mTvComment;
        @Bind(R.id.ll_comment)
        LinearLayout mLlComment;

        public VHHumor(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(HumorModel t,int position) {

        }
    }


    private class HummorSaveImgSheetListener implements ActionSheet.ActionSheetListener {

        private HumorModel model;

        public HummorSaveImgSheetListener(HumorModel models) {
            model = models;
        }

        @Override
        public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

        }

        @Override
        public void onOtherButtonClick(ActionSheet actionSheet, int index) {
            if (index == 0) {

                Glide.with(mContext)
                        .load(model.getImg())
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
