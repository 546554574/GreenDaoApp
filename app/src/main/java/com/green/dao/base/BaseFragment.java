package com.green.dao.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.green.dao.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public abstract class BaseFragment<T extends BasePresenterImpl> extends Fragment implements BaseView {

    public static final String TAG = BaseFragment.class.getSimpleName();
    public View mRootView;
    public Unbinder mUnbinder;

    protected T mPresenter;
    //下拉刷新
    private RefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayout(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        //initPresenter()是抽象方法，让view初始化自己的presenter
        mPresenter = initPresenter();
        //presenter和view的绑定
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
//        AutoSizeConfig.getInstance().setCustomFragment(true);
        //引入下拉刷新，加载更多
        refreshLayout = (RefreshLayout) mRootView.findViewById(R.id.refreshLayout);
        if (refreshLayout != null) {
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    refreData();
                    refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                }
            });
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(RefreshLayout refreshlayout) {
                    loadMore();
                    refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                }
            });
        }
        init(savedInstanceState);
        return mRootView;
    }

    // 实例化presenter
    public abstract T initPresenter();

    /**
     * 加载更多
     */
    public abstract void loadMore();

    /**
     * 下拉刷新
     */
    public abstract void refreData();

    public abstract void init(Bundle savedInstanceState);

    public abstract int getLayout();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
