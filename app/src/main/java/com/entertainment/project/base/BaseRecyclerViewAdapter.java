package com.entertainment.project.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Sick on 2016/11/8.
 */
public abstract class BaseRecyclerViewAdapter<M> extends RecyclerView.Adapter {

    protected Context mContext;
    protected ArrayList<M> mData;

    public LayoutInflater inflater;

    public BaseRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return CreateVH(parent, viewType);
    }

    public abstract int getViewType(int position);

    @Override
    public int getItemViewType(int position) {

        return getViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        fillData(holder, getItem(position), position);
    }

    protected abstract RecyclerView.ViewHolder CreateVH(ViewGroup parent, int viewType);

    /**
     * 填充item数据
     *
     * @param model
     */
    protected abstract void fillData(RecyclerView.ViewHolder viewHolder, M model, int position);


    public M getItem(int position) {
        return mData.get(position);
    }

    /**
     * 获取数据集合
     *
     * @return
     */
    public ArrayList<M> getData() {
        return mData;
    }

    /**
     * 在集合头部添加新的数据集合（下拉从服务器获取最新的数据集合，例如新浪微博加载最新的几条微博数据）
     *
     * @param data
     */
    public void addNewData(ArrayList<M> data) {
        if (data != null) {
            mData.addAll(0, data);
            notifyItemRangeInserted(0, data.size());
        }
    }

    /**
     * 在集合尾部添加更多数据集合（上拉从服务器获取更多的数据集合，例如新浪微博列表上拉加载更晚时间发布的微博数据）
     *
     * @param data
     */
    public void addMoreData(ArrayList<M> data) {
        if (data != null) {
            mData.addAll(mData.size(), data);
            notifyItemRangeInserted(mData.size(), data.size());
        }
    }

    /**
     * 设置全新的数据集合，如果传入null，则清空数据列表（第一次从服务器加载数据，或者下拉刷新当前界面数据表）
     *
     * @param data
     */
    public void setData(ArrayList<M> data, RecyclerView recyclerView) {
        if (data != null) {
            mData = data;
        } else {
            mData.clear();
        }
        recyclerView.setAdapter(this);
    }

    /**
     * 设置全新的数据集合，如果传入null，则清空数据列表（第一次从服务器加载数据，或者下拉刷新当前界面数据表）
     *
     * @param data
     */
    public void setData(ArrayList<M> data) {
        if (data != null) {
            mData = data;
        } else {
            mData.clear();
        }
    }

    /**
     * 设置全新的数据集合，如果传入null，则清空数据列表（第一次从服务器加载数据，或者下拉刷新当前界面数据表）
     *
     * @param data
     */
    public void updatetData(ArrayList<M> data) {
        if (data != null) {
            mData = data;
        } else {
            mData.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 清空数据列表
     */
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * 删除指定索引数据条目
     *
     * @param position
     */
    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 删除指定数据条目
     *
     * @param model
     */
    public void removeItem(M model) {
        removeItem(mData.indexOf(model));
    }

    /**
     * 在指定位置添加数据条目
     *
     * @param position
     * @param model
     */
    public void addItem(int position, M model) {
        mData.add(position, model);
        notifyItemInserted(position);
    }

    /**
     * 在集合头部添加数据条目
     *
     * @param model
     */
    public void addFirstItem(M model) {
        addItem(0, model);
    }

    /**
     * 在集合末尾添加数据条目
     *
     * @param model
     */
    public void addLastItem(M model) {
        addItem(mData.size(), model);
    }

    /**
     * 替换指定索引的数据条目
     *
     * @param location
     * @param newModel
     */
    public void setItem(int location, M newModel) {
        mData.set(location, newModel);
        notifyItemChanged(location);
    }

    /**
     * 替换指定数据条目
     *
     * @param oldModel
     * @param newModel
     */
    public void setItem(M oldModel, M newModel) {
        setItem(mData.indexOf(oldModel), newModel);
    }

    /**
     * 移动数据条目的位置
     *
     * @param fromPosition
     * @param toPosition
     */
    public void moveItem(int fromPosition, int toPosition) {
        mData.add(toPosition, mData.remove(fromPosition));
        notifyItemMoved(fromPosition, toPosition);
    }

    public interface VHClickListener<M> {

        void onItemClick(int position, View v, M model, RecyclerView.ViewHolder holder);

        void onItemLongClick(int position, View v, M model, RecyclerView.ViewHolder holder);

    }

    public VHClickListener<M> vhClickListener;

    public void setItemClickListener(VHClickListener<M> vhClickListener) {
        this.vhClickListener = vhClickListener;
    }

    public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected abstract void bind(M t,int position);
    }
}