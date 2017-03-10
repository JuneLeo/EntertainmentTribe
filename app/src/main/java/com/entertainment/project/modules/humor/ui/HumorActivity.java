package com.entertainment.project.modules.humor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.entertainment.project.R;
import com.entertainment.project.base.BaseActivity;
import com.entertainment.project.common.Constants;
import com.entertainment.project.modules.about.AboutActivity;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sick on 2017/2/16.
 */
public class HumorActivity extends BaseActivity {

    @Bind(R.id.stl_tab)
    SlidingTabLayout mStlTab;
    @Bind(R.id.vp_pager)
    ViewPager mVpPager;
    @Bind(R.id.fl_change)
    FrameLayout mFlChange;
    @Bind(R.id.iv_humor_logo)
    ImageView mIvHumorLogo;

    private final String[] mTitles = {
            "热门", "24小时", "热图"
            , "文字", "穿越", "糗图", "新鲜"
    };

    private String[] humorUrl = {
            "8hr/page/", "hot/page/", "imgrank/page/", "text/page/",
            "history/page/", "pic/page/", "textnew/page/"
    };
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humor);
        ButterKnife.bind(this);
        initFragment();
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mVpPager.setAdapter(mAdapter);
        mStlTab.setViewPager(mVpPager, mTitles, this, mFragments);

    }

    private void initFragment() {
        for (int i = 0; i < humorUrl.length; i++) {
            HumorFragment fragment = new HumorFragment();
            fragment.setUrl(Constants.url.BASE_HUMOR_URL + humorUrl[i]);
            mFragments.add(fragment);
        }
    }

    @OnClick({R.id.iv_humor_logo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_humor_logo:
//                Intent intent = new Intent(this, ReactActivity.class);
//                startActivity(intent);
                AboutActivity.launch(this);
                break;

        }
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, HumorActivity.class));
    }


}
