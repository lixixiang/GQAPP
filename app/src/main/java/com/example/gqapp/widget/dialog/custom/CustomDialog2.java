package com.example.gqapp.widget.dialog.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.gqapp.R;
import com.example.gqapp.utils.AnimOrientation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/27 1:30
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class CustomDialog2 extends AlertDialog {
    Context mContext;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.tips_sure_cancel)
    LinearLayout tipsSureCancel;

    public CustomDialog2(@NonNull Context context) {
        super(context, R.style.voiceDialog);
        mContext = context;
    }

    private void fullScreenImmersive(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            view.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog2);
        ButterKnife.bind(this);
        fullScreenImmersive(tipsSureCancel);
        AnimOrientation.leftRotation90(tipsSureCancel);
    }

    public OnClickSureCancel onClickSureCancel;

    public void setOnClickSureCancel(OnClickSureCancel onClickSureCancel) {
        this.onClickSureCancel = onClickSureCancel;
    }

    @OnClick({R.id.btn_cancel, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                if (onClickSureCancel != null) {
                    onClickSureCancel.onNegativeClick();
                }
                break;
            case R.id.btn_sure:
                if (onClickSureCancel != null) {
                    onClickSureCancel.onPositiveSureClick();
                }
                break;
        }
    }

    public interface OnClickSureCancel { //第二个type 两个按钮
        void onPositiveSureClick();
        void onNegativeClick();
    }

    @Override
    public void show() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        super.show();
        fullScreenImmersive(getWindow().getDecorView());
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }
}




