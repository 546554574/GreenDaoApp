package com.green.dao.ui.activity;

import android.os.Bundle;
import android.widget.Button;

import com.blankj.rxbus.RxBus;
import com.green.dao.R;
import com.green.dao.base.BaseActivity;
import com.green.dao.ui.model.TestVo;
import com.green.dao.ui.presenter.MainActivityPresenter;
import com.green.dao.ui.view.MainView;
import com.green.util.rxtool.RxActivityTool;
import com.zhy.changeskin.SkinManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainView, MainActivityPresenter> implements MainView {

    @BindView(R.id.web_btn)
    Button webBtn;

    @Override
    public MainActivityPresenter initPresenter() {
        return new MainActivityPresenter();
    }

    @Override
    public void loadMore() {

    }

    @Override
    public void refreData() {
        mPresenter.getVideoList();
    }

    @Override
    public void init(Bundle savedInstanceState) {
//        mPresenter.getVideoList();
        RxBus.getDefault().subscribe(this, new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                SkinManager.getInstance().changeSkin("night");
            }
        });


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTitleStr() {
        return "Main";
    }

    @Override
    protected void initEventAndData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void getVideoList(TestVo testVo) {
//        Glide.with(context).load(testVo.getData().get(0).getVideos().getCover()).into(imgIv);
    }

    @OnClick(R.id.web_btn)
    public void onViewClicked() {
        RxActivityTool.skipActivity(context, WebActivity.class);
    }
}
