package com.entertainment.project.modules.humor.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.entertainment.project.R;
import com.entertainment.project.base.BaseRecyclerViewAdapter;
import com.entertainment.project.common.Constants;
import com.entertainment.project.modules.humor.domain.HumorUserModel;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sick on 2017/3/3.
 */
public class HumorCommentAdapter extends BaseRecyclerViewAdapter<HumorUserModel> {


    @Bind(R.id.tv_humor_comment_position)
    TextView mTvHumorCommentPosition;
    private boolean isCommentFirst = true;
    private boolean isCommentGoodFirst = true;

    public HumorCommentAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getViewType(int position) {
        return mData.get(position).getModelType();
    }

    @Override
    protected RecyclerView.ViewHolder CreateVH(ViewGroup parent, int viewType) {
        if (viewType == Constants.ModelType.TYPE_MODEL_HEADER) {
            return new VHContent(inflater.inflate(R.layout.item_humor_content, parent, false));
        } else if (viewType == Constants.ModelType.TYPE_MODEL_DATA_1) {
            return new VHGoodComment(inflater.inflate(R.layout.item_humor_comment, parent, false));
        } else if (viewType == Constants.ModelType.TYPE_MODEL_DATA_2) {
            return new VHComment(inflater.inflate(R.layout.item_humor_comment, parent, false));
        }
        return null;
    }

    @Override
    protected void fillData(RecyclerView.ViewHolder viewHolder, HumorUserModel model, int position) {
        int type = model.getModelType();
        if (Constants.ModelType.TYPE_MODEL_HEADER == type) {
            ((VHContent) viewHolder).bind(model, position);
        } else if (Constants.ModelType.TYPE_MODEL_DATA_1 == type) {
            ((VHGoodComment) viewHolder).bind(model, position);
        } else if (Constants.ModelType.TYPE_MODEL_DATA_2 == type) {
            ((VHComment) viewHolder).bind(model, position);
        }
    }

    public class VHContent extends BaseViewHolder {
        @Bind(R.id.iv_comment_user_img)
        CircleImageView mIvCommentUserImg;
        @Bind(R.id.tv_comment_user_name)
        TextView mTvCommentUserName;
        @Bind(R.id.tv_comment_content)
        TextView mTvCommentContent;

        public VHContent(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(HumorUserModel t, int position) {

            if (!TextUtils.isEmpty(t.getUserImg())) {
                Glide.with(mIvCommentUserImg.getContext())
                        .load(t.getUserImg()).
                        into(mIvCommentUserImg);
            } else {
                mIvCommentUserImg.setImageBitmap(BitmapFactory.decodeResource(mIvCommentUserImg.getResources(), R.drawable.humor_user_empty));

            }

            if (!TextUtils.isEmpty(t.getUserName())) {
                mTvCommentUserName.setText(t.getUserName());
            } else {
                mTvCommentUserName.setText("匿名用户");
            }

            mTvCommentContent.setText(t.getContent());


        }

    }

    public class VHGoodComment extends BaseViewHolder {
        @Bind(R.id.tv_humor_comment_label_2)
        TextView mTvHumorCommentLabel;
        @Bind(R.id.iv_humor_user_img)
        CircleImageView mIvHumorUserImg;
        @Bind(R.id.tv_humor_comment_name)
        TextView mTvHumorCommentName;
        @Bind(R.id.tv_humor_comment_content)
        TextView mTvHumorCommentContent;
        @Bind(R.id.v_humor_bottom_line)
        View mVHumorBottomLine;
        @Bind(R.id.v_humor_top_line)
        View mVHumorTopLine;
        @Bind(R.id.tv_humor_comment_position)
        TextView mTvHumorCommentPosition;

        public VHGoodComment(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(HumorUserModel t, int position) {
            if (isCommentGoodFirst) {
                t.setFirst(true);
                isCommentGoodFirst = false;
            }

            mVHumorTopLine.setVisibility(View.GONE);
            if (t.isFirst()) {
                mTvHumorCommentLabel.setVisibility(View.VISIBLE);
            } else {
                mTvHumorCommentLabel.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(t.getUserImg())) {
                Glide.with(mIvHumorUserImg.getContext())
                        .load(t.getUserImg()).
                        into(mIvHumorUserImg);
            } else {
                mIvHumorUserImg.setImageBitmap(BitmapFactory.decodeResource(mIvHumorUserImg.getResources(), R.drawable.humor_user_empty));

            }

            if (!TextUtils.isEmpty(t.getUserName())) {
                mTvHumorCommentName.setText(t.getUserName());
            } else {
                mTvHumorCommentName.setText("匿名用户");
            }

            mTvHumorCommentContent.setText(t.getContent());

            if (position == getItemCount() - 1) {
                mVHumorBottomLine.setVisibility(View.GONE);
            } else {
                mVHumorBottomLine.setVisibility(View.VISIBLE);
            }

            mTvHumorCommentPosition.setVisibility(View.GONE);

        }
    }


    public class VHComment extends BaseViewHolder {
        @Bind(R.id.tv_humor_comment_label)
        TextView mTvHumorCommentLabel;
        @Bind(R.id.iv_humor_user_img)
        CircleImageView mIvHumorUserImg;
        @Bind(R.id.tv_humor_comment_name)
        TextView mTvHumorCommentName;
        @Bind(R.id.tv_humor_comment_content)
        TextView mTvHumorCommentContent;
        @Bind(R.id.v_humor_bottom_line)
        View mVHumorBottomLine;
        @Bind(R.id.v_humor_top_line)
        View mVHumorTopLine;
        @Bind(R.id.tv_humor_comment_position)
        TextView mTvHumorCommentPosition;

        public VHComment(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(HumorUserModel t, int position) {
            if (isCommentFirst) {
                t.setFirst(true);
                isCommentFirst = false;
            }

            if (t.isFirst()) {
                mVHumorTopLine.setVisibility(View.VISIBLE);
                mTvHumorCommentLabel.setVisibility(View.VISIBLE);
            } else {
                mVHumorTopLine.setVisibility(View.GONE);
                mTvHumorCommentLabel.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(t.getUserImg())) {
                Glide.with(mIvHumorUserImg.getContext())
                        .load(t.getUserImg()).
                        into(mIvHumorUserImg);
            } else {
                mIvHumorUserImg.setImageBitmap(BitmapFactory.decodeResource(mIvHumorUserImg.getResources(), R.drawable.humor_user_empty));

            }

            if (!TextUtils.isEmpty(t.getUserName())) {
                mTvHumorCommentName.setText(t.getUserName());
            } else {
                mTvHumorCommentName.setText("匿名用户");
            }

            mTvHumorCommentContent.setText(t.getContent());

            if (position == getItemCount() - 1) {
                mVHumorBottomLine.setVisibility(View.GONE);
            } else {
                mVHumorBottomLine.setVisibility(View.VISIBLE);
            }
            mTvHumorCommentPosition.setVisibility(View.VISIBLE);
            mTvHumorCommentPosition.setText(t.getPosition());

        }
    }


    public void notifyDataChanged() {
        isCommentFirst = true;
        isCommentGoodFirst = true;
        for (HumorUserModel humorUserModel : mData) {
            humorUserModel.setFirst(false);
        }
        notifyDataSetChanged();
    }
}
