package com.green.dao.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.green.dao.R;
import com.green.dao.service.UpdateService;
import com.green.dao.ui.model.UpdateAppVo;
import com.green.dao.widget.dialog.UpdateAppDialog;
import com.green.util.rxtool.GsonBinder;
import com.green.util.rxtool.RxAppTool;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class UpdateReceiver extends BroadcastReceiver {

    //Dialog弹框
    private static UpdateAppDialog mDialog;
    //这个是用来是否为手动点击获取更新信息的 默认为false
    private static boolean isClick = false;
    //刚刚在App中获取到的信息
    private static UpdateAppVo updateVo;

    @Override
    public void onReceive(Context context, Intent intent) {
        //获取到网络上apk的信息
        updateVo = GsonBinder.toObj(intent.getStringExtra("vo"), UpdateAppVo.class);

        //获取是否为手动点击的信息 默认为false自动检测，true 为手动检测
        isClick = intent.getBooleanExtra("isclick", true);

        checkVersion(context);
    }

    /**
     * 检查版本更新
     */
    public void checkVersion(Context context) {
        //如果服务上的版本大于本地的版本
        int netCode = Integer.parseInt(updateVo.getData().getNum().replace(".", ""));
        int localCode = Integer.parseInt(RxAppTool.getAppVersionName(context).replace(".", ""));
        if (netCode > localCode) {
            //判断是否为强制更新
            if (updateVo.getData().getIs_must_update() > 0) {// 为是强制升级
                promptDiglog(context, true);
            } else {                            //为正常升级
                if (isClick) {
                    promptDiglog(context, false);
                } else {
                    promptUpdate(context);
                }
            }
        } else {
            if (isClick) {
                promptDiglog(context, false);
                Log.d("版本更新", "无需更新");
            }
        }
    }

    /**
     * 通知栏提示更新
     */
    private void promptUpdate(Context context) {
        NotificationManager motificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        notification.icon = R.mipmap.ic_launcher;
        //添加声音提示
//        notification.defaults = Notification.DEFAULT_SOUND;
        /* 或者使用以下几种方式
         * notification.sound = Uri.parse("file:///sdcard/notification/ringer.mp3");
         * notification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
         * 如果想要让声音持续重复直到用户对通知做出反应，则可以在notification的flags字段增加"FLAG_INSISTENT"
         * 如果notification的defaults字段包括了"DEFAULT_SOUND"属性，则这个属性将覆盖sound字段中定义的声音
         */
        //audioStreamType的值必须AudioManager中的值，代表响铃模式
        notification.audioStreamType = AudioManager.ADJUST_LOWER;
        //添加LED灯提醒
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        //或者可以自己的LED提醒模式:
        /*notification.ledARGB = 0xff00ff00;
        notification.ledOnMS = 300; //亮的时间
        notification.ledOffMS = 1000; //灭的时间
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;*/
        //添加震动
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        //或者可以定义自己的振动模式：
        /*long[] vibrate = {0,100,200,300}; //0毫秒后开始振动，振动100毫秒后停止，再过200毫秒后再次振动300毫秒
        notification.vibrate = vibrate;*/
        //状态栏提示信息
        notification.tickerText = context.getResources().getString(R.string.app_name) + " 发现新版本，点击下载";
        //获取当前时间
        notification.when = System.currentTimeMillis();
        //加载自定义布局
        notification.contentView = getRemoteViews(context, "发现新版本，点击下载");
        //加载点击事件
        notification.contentIntent = getPendingIntent(context);
        // 点击清除按钮或点击通知后会自动消失
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        //取消Intent事件
        //notification.contentIntent.cancel();
        motificationManager.notify(0, notification);

    }

    //自定义notification布局
    public static RemoteViews getRemoteViews(Context context, String info) {
        RemoteViews remoteviews = new RemoteViews(context.getPackageName(), R.layout.download_promp);
        remoteviews.setImageViewResource(R.id.download_promp_icon, R.mipmap.ic_launcher_round);
        remoteviews.setTextViewText(R.id.download_title, context.getResources().getString(R.string.app_name));
        remoteviews.setTextViewText(R.id.download_promp_info, info);
        //如果你需要对title中的哪一个小控件添加点击事件可以这样为控件添加点击事件（详情请下载代码进行观看）
        //remoteviews.setOnClickPendingIntent(R.id.download_notification_root,getPendingIntent(context));
        return remoteviews;
    }

    /**
     * 给通知栏添加点击事件，实现具体操作
     *
     * @param context 上下文
     * @return
     */
    private PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, UpdateService.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("appname", "zydlksyg");
        intent.putExtra("updateurl", updateVo.getData().getUrl());
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        return pendingIntent;
    }

    /**
     * Dialog提示升级，用户可以选择是否取消升级
     */
    private void promptDiglog(final Context context, boolean type) {

        mDialog = new UpdateAppDialog(context, updateVo, type);
        mDialog.setOnUpdateAppClickListener(new UpdateAppDialog.OnUpdateAppClickListener() {
            @Override
            public void onUpdate() {
                Intent mIntent = new Intent(context, UpdateService.class);
                mIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                mIntent.putExtra("appname", "zydlksyg");
                mIntent.putExtra("updateurl", updateVo.getData().getUrl());
                //传递数据
                context.startService(mIntent);
            }
        });
        mDialog.show();
    }
}

