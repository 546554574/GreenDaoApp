package com.green.dao.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;

import com.green.dao.R;
import com.green.dao.base.MyApp;
import com.green.dao.base.MyUtil;
import com.green.dao.receiver.UpdateReceiver;
import com.green.util.rxtool.RxAppTool;
import com.green.util.rxtool.RxFileTool;
import com.green.util.rxtool.RxIntentTool;
import com.green.util.rxtool.RxPermissionsTool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 状态栏通知服务
 * Created by WL-鬼 on 2017/5/13.
 */

public class UpdateService extends Service {


    private static final float SIZE_BT = 1024L; // BT字节参考量
    private static final float SIZE_KB = SIZE_BT * 1024.0f; // KB字节参考量
    private static final float SIZE_MB = SIZE_KB * 1024.0f;// MB字节参考量

    private final static String DOWNLOAD_COMPLETE = "1";// 完成
    private final static String DOWNLOAD_NOMEMORY = "-1";// 内存异常
    private final static String DOWNLOAD_FAIL = "-2";// 失败

    private String appName;// 应用名字
    private String appUrl;// 应用升级地址
    //    private File updateDir;// 文件目录
    private File updateFile;// 升级文件

    private NotificationManager updateNotificationManager; // 通知栏
    private Notification updateNotification;
    private PendingIntent updatePendingIntent;// 在下载的时候


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        appName = intent.getStringExtra("appname");
        appUrl = intent.getStringExtra("updateurl");


        updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            CreateNotificationChannel(updateNotificationManager);
            updateNotification = new Notification.Builder(MyApp.getInstance(), NOTIFICATION_CHANNEL).build();
        } else {
            updateNotification = new Notification();
        }
        updateNotification.icon = R.mipmap.ic_launcher;//通知图标
        updateNotification.tickerText = "正在下载" + appName;//通知信息描述
        updateNotification.when = System.currentTimeMillis();
        updateNotification.contentView = new RemoteViews(getPackageName(),
                R.layout.download_notification);
        updateNotification.contentView.setTextViewText(
                R.id.download_notice_name_tv, appName + " 正在下载");
        new UpdateThread().execute();

    }

    private static final String NOTIFICATION_CHANNEL = "com.zydl.pay";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CreateNotificationChannel(NotificationManager notificationManager) {
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL, RxAppTool.getAppPackageName(MyApp.getInstance()), NotificationManager.IMPORTANCE_HIGH);
        channel.setSound(null, null);
        notificationManager.createNotificationChannel(channel);
    }

    /**
     * 这里使用一个内部类去继承AsyncTask
     * 实现异步操作
     */
    class UpdateThread extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            return downloadUpdateFile(appUrl);
        }
    }

    private long totalSize;      //APK总大小
    private long downloadSize;  // 下载的大小
    private int count = 0;       //下载百分比

    /**
     * 下载更新程序文件
     *
     * @param appUrl 下载地址
     * @return
     */
    private String downloadUpdateFile(String appUrl) {

        OkHttpClient mOkHttpClient = new OkHttpClient();

        Request mRequest = new Request.Builder().url(appUrl).build();

        Call mCall = mOkHttpClient.newCall(mRequest);

        mCall.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                downloadResult(DOWNLOAD_FAIL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    InputStream is = null;
                    byte[] buf = new byte[4096];
                    int len = 0;
                    FileOutputStream fos = null;
                    try {
                        totalSize = response.body().contentLength();
                        downloadSize = 0;
                        if (MemoryAvailable(totalSize)) {
                            is = response.body().byteStream();
                            fos = new FileOutputStream(updateFile, true);
                            while ((len = is.read(buf)) != -1) {
                                downloadSize += len;
                                fos.write(buf, 0, len);
                                if ((count == 0) || (int) (downloadSize * 100 / totalSize) >= count) {
                                    count += 5;
                                    //文本进度（百分比）
                                    updateNotification.contentView
                                            .setTextViewText(
                                                    R.id.download_notice_speed_tv,
                                                    getMsgSpeed(downloadSize, totalSize));
                                    //进度条
                                    updateNotification.contentView.setProgressBar(
                                            R.id.pbProgress,
                                            (int) totalSize, (int) downloadSize, false);
                                    updateNotificationManager.notify(0, updateNotification);
                                }
                            }
                            fos.flush();
                            if (totalSize >= downloadSize) {
                                //进度条
                                updateNotification.contentView.setProgressBar(
                                        R.id.pbProgress,
                                        (int) totalSize, (int) totalSize, false);
                                downloadResult(DOWNLOAD_COMPLETE);
                            } else {
                                downloadResult(DOWNLOAD_FAIL);
                            }
                        } else {
                            downloadResult(DOWNLOAD_NOMEMORY);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        downloadResult(DOWNLOAD_FAIL);
                    } finally {
                        try {
                            if (is != null) {
                                is.close();
                            }
                            if (fos != null) {
                                fos.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    downloadResult(DOWNLOAD_FAIL);
                }
            }
        });
        return null;
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RxAppTool.installApp(MyApp.getInstance(), updateFile);
        }
    };

    /**
     * 下载结果
     *
     * @param integer
     */
    private void downloadResult(String integer) {
        switch (integer) {
            case DOWNLOAD_COMPLETE:
                //权限
                RxPermissionsTool.with(MyApp.getInstance()).addPermission(Manifest.permission.REQUEST_INSTALL_PACKAGES).initPermission();
                updatePendingIntent = PendingIntent.getActivity(UpdateService.this, 0, RxIntentTool.getInstallAppIntent(MyApp.getInstance(), updateFile.getPath()), 0);
                updateNotification.contentIntent = updatePendingIntent;
                updateNotification.tickerText = appName + " 下载完成";//通知信息描述
                /**
                 * 这里做为保留，是选择显示之前有进度条的下载完成提示还是选择另外的显示样式，可根据自己定义
                 */
                updateNotification.contentView.setTextViewText(
                        R.id.download_notice_name_tv, appName + " 下载完成");
//                updateNotification.contentView.setTextViewText(
//                        R.id.download_notice_speed_tv,
//                        getString(R.string.update_notice_install));
                updateNotification.contentView = UpdateReceiver.getRemoteViews(MyApp.getInstance(), "下载完成，点击安装");
                updateNotification.when = System.currentTimeMillis();
                updateNotification.defaults = Notification.DEFAULT_SOUND;
                updateNotification.flags |= Notification.FLAG_AUTO_CANCEL;
                updateNotificationManager.notify(0, updateNotification);
                //启动安装程序
//                startActivity(installIntent);
                handler.sendEmptyMessageDelayed(0, 500);
                stopSelf();
                break;

            case DOWNLOAD_NOMEMORY:
                //如果内存有问题
                updateNotification.tickerText = appName + "下载失败";
                updateNotification.when = System.currentTimeMillis();
                updateNotification.contentView.setTextViewText(
                        R.id.download_notice_speed_tv,
                        getString(R.string.update_notice_nomemory));
                updateNotification.flags |= Notification.FLAG_AUTO_CANCEL;
                updateNotification.defaults = Notification.FLAG_ONLY_ALERT_ONCE;
                updateNotificationManager.notify(0, updateNotification);
                stopSelf();
                break;

            case DOWNLOAD_FAIL:
                //下载失败
                updateNotification.tickerText = appName + "下载失败";
                updateNotification.when = System.currentTimeMillis();
                updateNotification.contentView.setTextViewText(
                        R.id.download_notice_speed_tv,
                        getString(R.string.update_notice_error));
                updateNotification.flags |= Notification.FLAG_AUTO_CANCEL;
                updateNotification.defaults = Notification.DEFAULT_SOUND;
                updateNotificationManager.notify(0, updateNotification);
                stopSelf();
                break;
        }
    }

    /**
     * 可用内存大小
     *
     * @param fileSize
     * @return
     */
    private boolean MemoryAvailable(long fileSize) {
        fileSize += (1024 << 10);
        if (RxFileTool.sdCardIsAvailable()) {
            if ((RxFileTool.getSDCardAvailaleSize() <= fileSize)) {
                if ((RxFileTool.getSDCardAvailaleSize() > fileSize)) {
                    createFile(false);
                    return true;
                } else {
                    return false;
                }
            } else {
                createFile(true);
                return true;
            }
        } else {
            if (RxFileTool.getSDCardAvailaleSize() <= fileSize) {
                return false;
            } else {
                createFile(false);
                return true;
            }
        }
    }

    /**
     * 获取下载进度
     *
     * @param downSize
     * @param allSize
     * @return
     */
    public static String getMsgSpeed(long downSize, long allSize) {
        StringBuffer sBuf = new StringBuffer();
        sBuf.append(getSize(downSize));
        sBuf.append("/");
        sBuf.append(getSize(allSize));
        sBuf.append(" ");
        sBuf.append(getPercentSize(downSize, allSize));
        return sBuf.toString();
    }

    /**
     * 获取大小
     *
     * @param size
     * @return
     */
    public static String getSize(long size) {
        if (size >= 0 && size < SIZE_BT) {
            return (double) (Math.round(size * 10) / 10.0) + "B";
        } else if (size >= SIZE_BT && size < SIZE_KB) {
            return (double) (Math.round((size / SIZE_BT) * 10) / 10.0) + "KB";
        } else if (size >= SIZE_KB && size < SIZE_MB) {
            return (double) (Math.round((size / SIZE_KB) * 10) / 10.0) + "MB";
        }
        return "";
    }

    /**
     * 获取到当前的下载百分比
     *
     * @param downSize 下载大小
     * @param allSize  总共大小
     * @return
     */
    public static String getPercentSize(long downSize, long allSize) {
        String percent = (allSize == 0 ? "0.0" : new DecimalFormat("0.0")
                .format((double) downSize / (double) allSize * 100));
        return "(" + percent + "%)";
    }

    /**
     * 创建file文件
     *
     * @param sd_available sdcard是否可用
     */
    private void createFile(boolean sd_available) {
        RxFileTool.createFileByDeleteOldFile(MyUtil.getBasePath() + appName + ".apk");
        updateFile = RxFileTool.getFileByPath(MyUtil.getBasePath() + appName + ".apk");
    }
}
