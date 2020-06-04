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
public class CustomDialog1 extends AlertDialog {
    Context mContext;
    @BindView(R.id.btn_single_sure)
    Button btnSingleSure;
    @BindView(R.id.tips_sure)
    LinearLayout tipsSure;

    public CustomDialog1(@NonNull Context context) {
        super(context, R.style.voiceDialog);
        mContext = context;
    }

    public OnClickSingleSure onClickSingleSure;

    public void setOnClickSingleSure(OnClickSingleSure onClickSingleSure) {
        this.onClickSingleSure = onClickSingleSure;
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
        setContentView(R.layout.layout_dialog1);
        ButterKnife.bind(this);
        fullScreenImmersive(tipsSure);
        AnimOrientation.leftRotation90(tipsSure);
    }

    @OnClick(R.id.btn_single_sure)
    public void onViewClicked() {
        if (onClickSingleSure != null) {
            onClickSingleSure.onPositiveClick();
        }
    }

    public interface OnClickSingleSure {  //第一个type 只有一个按钮
        void onPositiveClick();
    }

    @Override
    public void show() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        super.show();
        fullScreenImmersive(getWindow().getDecorView());
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }
}




