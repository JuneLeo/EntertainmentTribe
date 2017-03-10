package com.entertainment.project.modules.humor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.entertainment.project.R;
import com.entertainment.project.base.BaseToolbarActivity;
import com.entertainment.project.common.Constants;
import com.entertainment.project.common.StateView;
import com.entertainment.project.modules.humor.HumorApi;
import com.entertainment.project.modules.humor.adapter.HumorCommentAdapter;
import com.entertainment.project.modules.humor.domain.HumorContentModel;
import com.entertainment.project.modules.humor.domain.HumorModel;
import com.entertainment.project.modules.humor.domain.HumorUserModel;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Sick on 2017/3/2.
 */
public class HumorCommentActivity extends BaseToolbarActivity {
    @Bind(R.id.ll_container)
    LinearLayout mLlContainer;
    @Bind(R.id.rv_humor_comment)
    RecyclerView mRvHumorComment;
    private StateView stateView;
    private HumorModel humorModel;
    private HumorCommentAdapter adapter;
    private ArrayList<HumorUserModel> humorUserList = new ArrayList<>();

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_humor_content;
    }

    @Override
    public boolean canBack() {
        return true;
    }


    @Override
    protected void setToolBarAttr() {
        mToolbar.setTitle("评论");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        stateView = StateView.inject(mLlContainer);
        Intent intent = getIntent();
        humorModel = (HumorModel) intent.getSerializableExtra("humorModel");
        initRecyclerView();
        updateUI(humorModel.getId());

        stateView.setOnRetryClickListener(new StateView.OnStateViewClickListener() {
            @Override
            public void onRetryClick() {
                updateUI(humorModel.getId());
            }

            @Override
            public void onEmptyClick() {
                updateUI(humorModel.getId());
            }
        });

    }



    private void initRecyclerView() {
        mRvHumorComment.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HumorCommentAdapter(this);
        adapter.setData(humorUserList, mRvHumorComment);
    }

    private void updateUI(Long contentId) {
        getHumorMainContentById(contentId)
                .doOnRequest(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        stateView.showLoading();

                    }
                }).subscribe(new Subscriber<HumorContentModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                stateView.showRetry();
            }

            @Override
            public void onNext(HumorContentModel humorContentModel) {
                humorUserList.clear();
                HumorUserModel humorUser = new HumorUserModel();
                humorUser.setUserId(humorModel.getHumorUserModel().getUserId());
                humorUser.setUserName(humorModel.getHumorUserModel().getUserName());
                humorUser.setUserImg(humorModel.getHumorUserModel().getUserImg());
                humorUser.setUserLevel(humorModel.getHumorUserModel().getUserLevel());
                humorUser.setContent(humorContentModel.getContent());
                humorUser.setModelType(Constants.ModelType.TYPE_MODEL_HEADER);
                humorUserList.add(humorUser);
                humorUserList.addAll(humorContentModel.getCommentators());
                Collections.sort(humorUserList);
                if (humorUserList.isEmpty()) {
                    stateView.showEmpty();
                } else {
                    stateView.showContent();
                }
                adapter.notifyDataChanged();
            }
        });
    }


    /**
     * 获取数据
     *
     * @param contentId
     * @return
     */
    public Observable<HumorContentModel> getHumorMainContentById(Long contentId) {
        String URL = Constants.url.BASE_HUMOR_URL + "article/" + contentId;
        return HumorApi.getHumorMainContentById(URL)
                .compose(this.bindToLifecycle());
    }

    public static void launch(Context context, HumorModel humorModel) {
        Intent intent = new Intent(context, HumorCommentActivity.class);
        intent.putExtra("humorModel", humorModel);
        context.startActivity(intent);
    }
}
