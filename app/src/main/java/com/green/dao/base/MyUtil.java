package com.green.dao.base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.green.dao.api.ServiceManager;
import com.green.dao.helper.rxjavahelper.RxObserver;
import com.green.dao.helper.rxjavahelper.RxResultHelper;
import com.green.dao.helper.rxjavahelper.RxSchedulersHelper;
import com.green.dao.ui.model.UpdateAppVo;
import com.green.util.rxtool.GsonBinder;
import com.green.util.rxtool.RxAppTool;
import com.green.util.rxtool.RxFileTool;
import com.green.util.rxtool.view.RxToast;

public class MyUtil {
    /**
     * 更新APP对话框等
     *
     * @param context
     * @param isShowToast
     */
    public static void updateApp(final Context context, final boolean isShowToast) {
        ServiceManager.updateApp()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<UpdateAppVo>() {
                    @Override
                    public void _onNext(UpdateAppVo updateAppVo) {
                        try {
                            int netCode = Integer.parseInt(updateAppVo.getData().getNum().replace(".", ""));
                            int localCode = Integer.parseInt(RxAppTool.getAppVersionName(context).replace(".", ""));
                            if (netCode > localCode) {
                                //创建Intent
                                Intent intent = new Intent(AppConstant.UPDATE_ACTION);
                                intent.setComponent(new ComponentName(RxAppTool.getAppPackageName(context),
                                        "com.zydl.pay.receiver.UpdateReceiver"));
                                intent.putExtra("vo", GsonBinder.toJsonStr(updateAppVo));
                                //开启广播
                                context.sendBroadcast(intent);
                            } else {
                                if (isShowToast) {
                                    RxToast.info("已经是最新版本");
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void _onError(String errorMessage) {

                    }
                });
    }

    /**
     * 截图照片存放路径
     *
     * @return
     */
    public static String getBasePath() {
        return RxFileTool.getSDCardPath() + "myapp/app/";
    }
}
