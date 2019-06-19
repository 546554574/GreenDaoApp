package com.green.dao.ui.presenter;

import com.green.dao.api.ServiceManager;
import com.green.dao.base.BasePresenterImpl;
import com.green.dao.helper.rxjavahelper.RxObserver;
import com.green.dao.helper.rxjavahelper.RxResultHelper;
import com.green.dao.helper.rxjavahelper.RxSchedulersHelper;
import com.green.dao.ui.model.TestVo;
import com.green.dao.ui.view.MainView;

import io.reactivex.disposables.Disposable;

public class MainActivityPresenter extends BasePresenterImpl<MainView> {

    public void getVideoList() {
        ServiceManager.getVideo()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<TestVo>() {
                    @Override
                    public void _onSubscribe(Disposable d) {
                        getView().showLoading();
                    }

                    @Override
                    public void _onNext(TestVo testVo) {
                        getView().getVideoList(testVo);
                    }

                    @Override
                    public void _onError(String errorMessage) {

                    }

                    @Override
                    public void _onComplete() {
                        getView().hideLoading();
                    }
                });
    }
}
