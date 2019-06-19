package com.green.dao.api;

import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.ObservableBody;
import com.green.dao.helper.ResponseData;
import com.green.dao.ui.model.TestVo;
import com.green.dao.helper.JsonConvert;
import com.green.dao.ui.model.UpdateAppVo;

import io.reactivex.Observable;

public class ServiceManager {
    public static final String Token = "";
    public static String BaseUrl = "http://pay-forum.zydl-tec.cn";
    public static String UpdateApp = BaseUrl + "/v1/app/my/check_version"; //更新APP
    public static final String TestUrl = BaseUrl + "/v1/app/videos/list";

    public static Observable<ResponseData<TestVo>> getVideo() {
        return OkGo.<ResponseData<TestVo>>get(TestUrl)
                .converter(new JsonConvert<ResponseData<TestVo>>() {
                })
                .adapt(new ObservableBody<ResponseData<TestVo>>());
    }

    public static Observable<ResponseData<UpdateAppVo>> updateApp() {
        return OkGo.<ResponseData<UpdateAppVo>>get(UpdateApp)
                .params("client", "android")
                .converter(new JsonConvert<ResponseData<UpdateAppVo>>())
                .adapt(new ObservableBody<ResponseData<UpdateAppVo>>());
    }
}
