package com.green.dao.ui.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.green.dao.R;
import com.green.dao.base.BaseActivity;
import com.green.dao.ui.presenter.WebPresenter;
import com.green.dao.ui.view.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity<WebView, WebPresenter> implements WebView {
    @BindView(R.id.root_ll)
    LinearLayout rootLl;
    private AgentWeb mAgentWeb;

    @Override
    public WebPresenter initPresenter() {
        return new WebPresenter();
    }

    @Override
    public void loadMore() {

    }

    @Override
    public void refreData() {

    }

    @Override
    public void init(Bundle savedInstanceState) {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) rootLl, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go("https://github.com/Justson/AgentWeb");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected String getTitleStr() {
        return null;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
