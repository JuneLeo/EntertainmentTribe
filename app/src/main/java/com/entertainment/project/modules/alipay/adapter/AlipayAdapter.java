package com.entertainment.project.modules.alipay.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.entertainment.project.R;
import com.entertainment.project.base.BaseBean;
import com.entertainment.project.modules.alipay.domain.DataValue;
import com.entertainment.project.modules.alipay.domain.FonctionValue;
import com.entertainment.project.modules.alipay.domain.MoneyValue;
import com.entertainment.project.modules.alipay.domain.UserValue;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sick on 2016/11/2.
 */
public class AlipayAdapter extends RecyclerView.Adapter {



    private ArrayList<BaseBean> data;
    private Context context;
    private LayoutInflater inflater;

    public AlipayAdapter(ArrayList<BaseBean> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).type == BaseBean.TYPE_CONTENT_1) {
            return BaseBean.TYPE_CONTENT_1;
        } else if (data.get(position).type == BaseBean.TYPE_CONTENT_2) {
            return BaseBean.TYPE_CONTENT_2;
        } else if (data.get(position).type == BaseBean.TYPE_CONTENT_3) {
            return BaseBean.TYPE_CONTENT_3;
        } else if (data.get(position).type == BaseBean.TYPE_CONTENT_4) {
            return BaseBean.TYPE_CONTENT_4;
        } else {
            throw new IllegalArgumentException("Wrong template");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BaseBean.TYPE_CONTENT_1) {
            View user = inflater.inflate(R.layout.item_alipay_user, parent, false);
            return new UserVH(user);
        } else if (viewType == BaseBean.TYPE_CONTENT_2) {
            View data = inflater.inflate(R.layout.item_alipay_data, parent, false);
            return new DataVH(data);
        } else if (viewType == BaseBean.TYPE_CONTENT_3) {
            View money = inflater.inflate(R.layout.item_alipay_money, parent, false);
            return new MoneyVH(money);
        } else if (viewType == BaseBean.TYPE_CONTENT_4) {
            View function = inflater.inflate(R.layout.item_alipay_function, parent, false);
            return new FunctionVH(function);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        BaseBean bean = data.get(position);
        if (holder instanceof UserVH) {
            ((UserVH) holder).mTvAlipayUser.setText(((UserValue) bean).getName());
        } else if (holder instanceof DataVH) {
            ((DataVH) holder).mTvAlipayData.setText(((DataValue) bean).getName());
            ((DataVH) holder).mIvDataImg.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),((DataValue) bean).getImg()));
        } else if (holder instanceof MoneyVH) {
            ((MoneyVH) holder).mTvAlipayMoney.setText(((MoneyValue) bean).getMoney());
        } else if (holder instanceof FunctionVH) {
            ((FunctionVH) holder).mTvAlipayFunction.setText(((FonctionValue) bean).getName());
            ((FunctionVH) holder).mTvAlipayFunctionContent.setText(((FonctionValue) bean).getContent());
            ((FunctionVH) holder).mIvFunctionImg.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), ((FonctionValue) bean).getImg()));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    if (holder instanceof UserVH) {
                        mOnItemClickListener.onUserClick(v, position);
                    } else if (holder instanceof DataVH) {
                        mOnItemClickListener.onDataClick(v, position);
                    } else if (holder instanceof MoneyVH) {
                        mOnItemClickListener.onMoneyClick(v, position);
                    } else if (holder instanceof FunctionVH) {
                        mOnItemClickListener.onFunctionClick(v, position);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public class UserVH extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_alipay_user)
        TextView mTvAlipayUser;

        public UserVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class DataVH extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_alipay_data)
        TextView mTvAlipayData;
        @Bind(R.id.iv_data_img)
        ImageView mIvDataImg;

        public DataVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class MoneyVH extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_alipay_money)
        TextView mTvAlipayMoney;

        public MoneyVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FunctionVH extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_alipay_function)
        TextView mTvAlipayFunction;
        @Bind(R.id.iv_function_img)
        ImageView mIvFunctionImg;
        @Bind(R.id.tv_alipay_function_content)
        TextView mTvAlipayFunctionContent;

        public FunctionVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onUserClick(View v, int position);

        void onDataClick(View v, int position);

        void onMoneyClick(View v, int position);

        void onFunctionClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
