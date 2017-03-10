package com.entertainment.project.modules.humor.ui;

/**
 * Created by Sick on 2017/2/20.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.entertainment.project.R;
import com.entertainment.project.base.BaseFragment;
import com.entertainment.project.base.BaseRecyclerViewAdapter;
import com.entertainment.project.modules.humor.HumorApi;
import com.entertainment.project.modules.humor.adapter.HumorAdapter;
import com.entertainment.project.modules.humor.domain.HumorModel;
import com.entertainment.project.common.StateView;
import com.entertainment.project.common.photoview.PhotoViewDialog;
import com.entertainment.project.common.photoview.TListener;
import com.entertainment.project.common.photoview.TribeRefreshViewHolder;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Sick on 2017/2/16.
 */
public class HumorFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @Bind(R.id.rv_humor)
    RecyclerView mRvHumor;
    @Bind(R.id.bga_fl)
    BGARefreshLayout mBgaFl;
    private View view;
    private String TAG = HumorActivity.class.getSimpleName();
    private int initPage = 1;
    private int page = initPage;
    private HumorAdapter adapter;
    private ArrayList<HumorModel> humorList = new ArrayList<>();
    private boolean isRefresh = true;
    private boolean isFirst = true;
    private String url;
    private StateView stateView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTheme(com.baoyz.actionsheet.R.style.ActionSheetStyleWeCaiWu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_humor, container, false);
            ButterKnife.bind(this, view);
            stateView = StateView.inject(view, false);
        }
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRefreshLayout();
        initView();
        updateUI(url, page);
        stateView.setOnRetryClickListener(new StateView.OnStateViewClickListener() {
            @Override
            public void onRetryClick() {
                initPage = 1;
                updateUI(url, initPage);
            }

            @Override
            public void onEmptyClick() {
                initPage = 1;
                updateUI(url, initPage);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 初始化VIew
     */
    private void initView() {

        mRvHumor.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HumorAdapter(getActivity());
        adapter.setData(humorList, mRvHumor);
        adapter.setItemClickListener(new HumorClickListener());
    }

    /**
     * 配置下拉刷新上拉加载框架
     */
    private void initRefreshLayout() {
        // 为BGARefreshLayout 设置代理
        mBgaFl.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshVH = new TribeRefreshViewHolder(getActivity(), true);
        // 设置下拉刷新和上拉加载更多的风格
        mBgaFl.setRefreshViewHolder(refreshVH);

//        // 可选配置选项  -------------START
//        // 设置正在加载更多时不显示加载更多控件
//        // mRefreshLayout.setIsShowLoadingMoreView(false);
//        // 设置正在加载更多时的文本
//        refreshVH.setLoadingMoreText(loadingMoreText);
//        // 设置整个加载更多控件的背景颜色资源 id
//        refreshVH.setLoadMoreBackgroundColorRes(loadMoreBackgroundColorRes);
//        // 设置整个加载更多控件的背景 drawable 资源 id
//        refreshVH.setLoadMoreBackgroundDrawableRes(loadMoreBackgroundDrawableRes);
//        // 设置下拉刷新控件的背景颜色资源 id
//        refreshVH.setRefreshViewBackgroundColorRes(refreshViewBackgroundColorRes);
//        // 设置下拉刷新控件的背景 drawable 资源 id
//        refreshVH.setRefreshViewBackgroundDrawableRes(refreshViewBackgroundDrawableRes);
//        // 设置自定义头部视图（也可以不用设置）     参数1：自定义头部视图（例如广告位）， 参数2：上拉加载更多是否可用
//        refreshVH.setCustomHeaderView(mBanner, false);
//        // 可选配置  -------------END

    }

    /**
     * 获取数据
     *
     * @param page
     * @return
     */
    public Observable<ArrayList<HumorModel>> getDataByPage(String url, int page) {
        String URL = url + page + "/";
        return HumorApi.getHumorByPage(URL)
                .compose(this.bindToLifecycle());
    }

    /**
     * 更新UI
     *
     * @param page
     */
    public void updateUI(String url, int page) {
        getDataByPage(url, page)
                .doOnRequest(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if (isRefresh && !isFirst) {
                            mBgaFl.beginRefreshing();
                        } else if (isRefresh && isFirst) {
                            stateView.showLoading();
                        }
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        mBgaFl.endRefreshing();
                        isFirst = false;
                    }
                })
                .subscribe(new Subscriber<ArrayList<HumorModel>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Throwable: ");
                        stateView.showRetry();
                    }

                    @Override
                    public void onNext(ArrayList<HumorModel> humorModels) {
                        Log.d(TAG, "数据获取成功 ");
                        if (humorModels.isEmpty()) {
                            stateView.showEmpty();
                        } else {
                            stateView.showContent();
                        }
                        if (isRefresh) {
                            humorList.clear();
                        }
                        humorList.addAll(humorModels);
                        adapter.notifyDataSetChanged();


                    }
                });
        Log.d(TAG, "onClick: ");
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isRefresh = true;
        updateUI(url, initPage);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isRefresh = false;
        page++;
        updateUI(url, page);
        return false;
    }

    public HumorFragment setUrl(String url) {
        this.url = url;
        return this;
    }


    public class HumorClickListener implements BaseRecyclerViewAdapter.VHClickListener<HumorModel> {

        @Override
        public void onItemClick(int position, View v, HumorModel model, RecyclerView.ViewHolder holder) {
            if (v.getId() == R.id.iv_img) {
                Log.d(TAG, "图片部分被点击：" + R.id.iv_img);
                PhotoViewDialog<HumorModel> dialog = new PhotoViewDialog<HumorModel>();
                dialog.setData(humorList, model);
                dialog.setTListener(new TListener<HumorModel>() {
                    @Override
                    public String getImageUrl(HumorModel humorModel) {
                        return humorModel.getImg();//读取实体类中的img字段   图片的url
                    }

                    @Override
                    public String getImageName(HumorModel humorModel) {
                        return humorModel.getHumorUserModel().getUserName() + "(用户名替代图片名)";//图片名
                    }
                });
                dialog.show(getActivity(), "糗事百科");
                //HumorActivity.this.getSupportFragmentManager(),
            } else if (v.getId() == R.id.tv_content) {
                Log.d(TAG, "内容部分被点击：" + R.id.tv_content);
                HumorCommentActivity.launch(getActivity(), model);
            }
        }

        @Override
        public void onItemLongClick(int position, View v, HumorModel model, RecyclerView.ViewHolder holder) {
            if (v.getId() == R.id.tv_content) {
                Log.d(TAG, "内容部分被长按点击：" + R.id.tv_content);
            }
        }
    }
}
