package com.green.dao.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.green.dao.R;
import com.green.dao.ui.model.UpdateAppVo;


public class UpdateAppDialog extends Dialog {
    Context context;
    boolean isMustUpdate;
    private View mainView;
    private TextView versionNumTv, contentTv, noUpdateTv, nowUpdateTv;
    UpdateAppVo updateVo;

    public UpdateAppDialog(Context context, UpdateAppVo updateVo, boolean isMustUpdate) {
        super(context, R.style.dialog_style);
        this.context = context;
        this.isMustUpdate = isMustUpdate;
        this.updateVo = updateVo;
        initView(context);
    }

    public UpdateAppDialog(Context context) {
        super(context, R.style.dialog_style);
        this.context = context;
        initView(context);
    }

    public UpdateAppDialog(Context context, int themeResId) {
        super(context, R.style.dialog_style);
        this.context = context;
        initView(context);
    }

    protected UpdateAppDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        mainView = View.inflate(context, R.layout.layout_dialog_update_app, null);
        setContentView(mainView);
        versionNumTv = mainView.findViewById(R.id.version_num_tv);
        versionNumTv.setText(updateVo.getData().getNum());
        contentTv = mainView.findViewById(R.id.content_tv);
        contentTv.setText(updateVo.getData().getContent());
        noUpdateTv = mainView.findViewById(R.id.no_update_tv);
        nowUpdateTv = mainView.findViewById(R.id.now_update_tv);
        if (isMustUpdate) {
            nowUpdateTv.setVisibility(View.GONE);
        } else {
            nowUpdateTv.setVisibility(View.VISIBLE);
        }
        noUpdateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        nowUpdateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                //通知通知栏显示更新
                if (onUpdateAppClickListener != null) {
                    onUpdateAppClickListener.onUpdate();
                }
            }
        });
    }

    OnUpdateAppClickListener onUpdateAppClickListener;

    public void setOnUpdateAppClickListener(OnUpdateAppClickListener onUpdateAppClickListener) {
        this.onUpdateAppClickListener = onUpdateAppClickListener;
    }

    public interface OnUpdateAppClickListener {
        void onUpdate();
    }
}
