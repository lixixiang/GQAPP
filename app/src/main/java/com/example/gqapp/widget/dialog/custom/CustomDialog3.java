package com.example.gqapp.widget.dialog.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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
public class CustomDialog3 extends AlertDialog {
    Context mContext;
    @BindView(R.id.iv_car_open_door)
    ImageView ivCarOpenDoor;
    @BindView(R.id.btn_close_door)
    Button btnCloseDoor;
    @BindView(R.id.tips_close_door)
    LinearLayout tipsCloseDoor;

    public CustomDialog3(@NonNull Context context) {
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
        setContentView(R.layout.layout_dialog3);
        ButterKnife.bind(this);
        fullScreenImmersive(tipsCloseDoor);
        AnimOrientation.leftRotation90(tipsCloseDoor);
    }

    @OnClick(R.id.btn_close_door)
    public void onViewClicked() {
        if (onClickBtnCloseDoor != null) {
            onClickBtnCloseDoor.onBtnCloseDoor();
        }
    }

    public OnClickBtnCloseDoor onClickBtnCloseDoor;

    public void setOnClickBtnCloseDoor(OnClickBtnCloseDoor onClickBtnCloseDoor){
        this.onClickBtnCloseDoor = onClickBtnCloseDoor;
    }

    public interface OnClickBtnCloseDoor{ //第三个type 一个按钮
        void onBtnCloseDoor();
    }

    @Override
    public void show() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        super.show();
        fullScreenImmersive(getWindow().getDecorView());
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }
}




