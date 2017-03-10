package com.entertainment.project.modules.alipay.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.entertainment.project.R;
import com.entertainment.project.common.utils.ToastUtil;
import com.entertainment.project.modules.alipay.adapter.AlipayAdapter;
import com.entertainment.project.modules.alipay.domain.DataValue;
import com.entertainment.project.modules.alipay.domain.FonctionValue;
import com.entertainment.project.modules.alipay.domain.MoneyValue;
import com.entertainment.project.modules.alipay.domain.UserValue;
import com.entertainment.project.base.BaseBean;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;



public class AlipayActivity extends AppCompatActivity {


    @Bind(R.id.rv_alipay)
    RecyclerView mRvAlipay;
    private ArrayList<BaseBean> data;
    private String[] a = {"会员中心", "收藏", "设置"};
    private int imgs[] = {R.drawable.alipay_vip,R.drawable.alipay_save,R.drawable.alipay_setting};
    private String[] b = {"余额", "我的银行卡", "余额宝", "基金", "芝麻信用", "蚂蚁花呗",
            "网商银行", "保险服务", "定期理财","淘宝众筹","股票","乐买宝",
            "娱乐宝","爱你心捐赠"};
    private String[] content = {"200.95", "3", "立即转入", "买入费率1折起",
            "因为信用，所以简单", "立即开通",
            "随意存，最高3.333333333", "3亿保民的选择", "招财宝等","创意新品，每天上淘宝众筹",
            "行情，随手查","边消费，边赚钱",
            "人人都能玩的赚","爱在行动"};
    private int[] img = {R.drawable.alipay01, R.drawable.alipay02, R.drawable.alipay03, R.drawable.alipay04,
            R.drawable.alipay05, R.drawable.alipay06, R.drawable.alipay07, R.drawable.alipay08, R.drawable.alipay09,R.drawable.alipay10,R.drawable.alipay11,
            R.drawable.alipay12,R.drawable.alipay13,R.drawable.alipay14};
    private AlipayAdapter adapter;
    private AlipayItemDecoration alipayItemDecoration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay);
        ButterKnife.bind(this);
        data = new ArrayList<BaseBean>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int spansize = 3;
                switch (adapter.getItemViewType(position)) {
                    case 1:
                        spansize = 6;
                        break;
                    case 2:
                        spansize = 2;
                        break;
                    case 3:
                        spansize = 6;
                        break;
                    case 4:
                        spansize = 3;
                        break;
                }
                return spansize;
            }
        });
        mRvAlipay.setLayoutManager(gridLayoutManager);
        alipayItemDecoration=new AlipayItemDecoration(this);
        mRvAlipay.addItemDecoration(alipayItemDecoration);
        adapter = new AlipayAdapter(data,this);
        mRvAlipay.setAdapter(adapter);
        initData();

        adapter.setOnItemClickListener(new AlipayAdapter.OnItemClickListener() {
            @Override
            public void onUserClick(View v, int position) {
                ToastUtil.showShort(position+"");
            }

            @Override
            public void onDataClick(View v, int position) {
                ToastUtil.showShort(position+"");
            }

            @Override
            public void onMoneyClick(View v, int position) {
                ToastUtil.showShort(position+"");
            }

            @Override
            public void onFunctionClick(View v, int position) {
                ToastUtil.showShort(position+"");
            }
        });
    }

    private void initData() {
        UserValue userValue = new UserValue();
        userValue.setName("力者");
        data.add(userValue);

        MoneyValue moneyValue = new MoneyValue();
        moneyValue.setMoney("686.32");
        data.add(moneyValue);

        for (int i = 0; i < a.length; i++) {
            DataValue dataValue = new DataValue();
            dataValue.setName(a[i]);
            dataValue.setImg(imgs[i]);
            data.add(dataValue);
        }
        for (int i = 0; i < b.length; i++) {
            FonctionValue fonctionValue = new FonctionValue();
            fonctionValue.setName(b[i]);
            fonctionValue.setImg(img[i]);
            fonctionValue.setContent(content[i]);
            data.add(fonctionValue);
        }
        Collections.sort(data);
        alipayItemDecoration.setDat(a.length);
        adapter.notifyDataSetChanged();
    }
}
