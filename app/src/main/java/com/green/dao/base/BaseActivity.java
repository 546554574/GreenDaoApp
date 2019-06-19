package com.green.dao.base;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.green.dao.ui.activity.MainActivity;
import com.green.dao.R;
import com.green.util.rxtool.RxActivityTool;
import com.green.util.rxtool.RxPermissionsTool;
import com.green.util.rxtool.view.RxToast;
import com.green.util.rxview.progressing.style.ThreeBounce;
import com.zhy.changeskin.SkinManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wangjitao on 2016/11/8 0008.
 * 基类Activity的封装
 * 一般使用mvp模式的话会在BaseActivity中进行P和V的初始化绑定
 */
public abstract class BaseActivity<V, T extends BasePresenterImpl> extends AppCompatActivity implements BaseView {

    public static final String TAG = BaseActivity.class.getSimpleName();
    protected T mPresenter;
    ImageView leftIv;
    LinearLayout leftLl;
    TextView titleTv;
    ImageView rightIv;
    Toolbar toolbar;
    RelativeLayout contentRL;
    private Unbinder mUnbinder;
    public Context context;

    /**
     * 传来的initViewID对应的View
     */
    public View viewActivity = null;
    //title相关  标题
    public String titleStr = "";
    //下拉刷新
    private RefreshLayout refreshLayout;
    private ProgressBar loadingProgress;
    private RelativeLayout loadingLyout;
    private TextView rightTv;

    public void showProDialog() {
        try {
            loadingLyout.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProDialog() {
        try {
            loadingLyout.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        RxActivityTool.addActivity(this);
        SkinManager.getInstance().register(this);
        View view = View.inflate(context, R.layout.activity_basic, null);
        setContentView(view);
        titleTv = findViewById(R.id.title_tv);
        toolbar = findViewById(R.id.toolbar);
        contentRL = findViewById(R.id.basic_view);
        loadingProgress = findViewById(R.id.progress);
        loadingLyout = findViewById(R.id.loading_layout);
        /**
         * 设置loading样式
         */
        setLoadingStyle();
        int initViewID = getLayout();
        this.viewActivity = View.inflate(context, initViewID, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        contentRL.addView(viewActivity, layoutParams);
        mUnbinder = ButterKnife.bind(this);
        //initPresenter()是抽象方法，让view初始化自己的presenter
        mPresenter = initPresenter();
        //presenter和view的绑定
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
        titleStr = getTitleStr();
        //标题  ：布局文件中引入lyout_title
        if (!TextUtils.isEmpty(titleStr)) {
            leftLl = view.findViewById(R.id.left_ll);
            rightTv = view.findViewById(R.id.right_tv);
            leftIv = view.findViewById(R.id.left_iv);
            rightIv = view.findViewById(R.id.right_iv);
            if (leftLl != null) {
                leftLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onTopLeftListener();
                    }
                });
            }
            if (rightTv != null) {
                rightTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onTopRightTvListener();
                    }

                });
            }
            if (rightIv != null) {
                rightIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onTopRightIvListener();
                    }

                });
            }
            setToolBar(toolbar, titleStr);
        } else {
            toolbar.setVisibility(View.GONE);
        }
        //引入下拉刷新，加载更多
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        if (refreshLayout != null) {
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    refreData();
                    refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                }
            });
//            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//                @Override
//                public void onLoadMore(RefreshLayout refreshlayout) {
//                    loadMore();
//                    refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
//                }
//            });
        }
        RxPermissionsTool.with(this)
                .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .addPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .addPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                .addPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .initPermission();
        //butter绑定
        init(savedInstanceState);
        //返回的不是null和空则是为有标题，标题的为返回值
        initEventAndData();
    }

    private void setLoadingStyle() {
        if (loadingProgress != null) {
            int color = android.graphics.Color.parseColor("#89CFF0");
            ThreeBounce chasingDots = new ThreeBounce();
            chasingDots.setColor(color);
            loadingProgress.setIndeterminateDrawable(chasingDots);
        }
    }

    private void onTopRightTvListener() {
    }

    public void onTopRightIvListener() {
    }

    public void onTopLeftListener() {
        onBackIv();
    }

    // 实例化presenter
    public abstract T initPresenter();

    @Override
    protected void onStart() {
        super.onStart();
    }

    public abstract void loadMore();

    public abstract void refreData();

    /**
     * 返回按钮，可不实现
     */
    public void onBackIv() {
        RxActivityTool.finishActivity();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        SkinManager.getInstance().unregister(this);
        super.onDestroy();
    }

    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle("");
        titleTv.setText(title);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
    }

    public abstract void init(Bundle savedInstanceState);

    protected abstract int getLayout();

    protected abstract String getTitleStr();

    protected abstract void initEventAndData();


    @Override
    public void showLoading() {
        showProDialog();
    }

    @Override
    public void hideLoading() {
        hideProDialog();
    }

    private long exitTime = 0;

    /**
     * 判断是否是主页面
     *
     * @return
     */
    private boolean isLoginOut() {
        return getClass().getSimpleName().equals(
                MainActivity.class.getSimpleName())
//                | getClass().getSimpleName().equals(LoginActivity.class.getSimpleName())
                ;
    }

    @Override
    public void onBackPressed() {
        if (isLoginOut()) {
            //连续按2次返回键退出
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                RxToast.showToast("再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                RxActivityTool.AppExit(context);
            }
        } else {
            onBackIv();
        }
    }

}
