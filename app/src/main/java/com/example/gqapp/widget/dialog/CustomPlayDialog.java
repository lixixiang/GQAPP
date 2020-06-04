package com.example.gqapp.widget.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gqapp.R;
import com.example.gqapp.fragment.right.MainRightFragment;
import com.example.gqapp.utils.AnimOrientation;
import com.example.gqapp.utils.uart.UartRxData;
import com.example.gqapp.widget.CustomerScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.gqapp.utils.uart.UartTxData.PAD_ACUPlayBtnSt;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/5/24 21:12
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class CustomPlayDialog extends AlertDialog {

    @BindView(R.id.play_status)
    ImageView playStatus;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.view_right_back)
    LinearLayout viewRightBack;
    @BindView(R.id.right_bottom)
    CustomerScrollView rightBottom;
    @BindView(R.id.fl_play)
    FrameLayout flPlay;
    private Activity mActivity;
    private String content; // 内容
    boolean isPlay = false;

    private void fullScreenImmersive(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            view.setSystemUiVisibility(uiOptions);
        } else {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        }
    }

    protected void showBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.VISIBLE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = this.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    public CustomPlayDialog(Activity activity, String content) {
        super(activity, R.style.voiceDialog);
        mActivity = activity;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_play);
        ButterKnife.bind(this);
        showBottomUIMenu();
        AnimOrientation.rightRotation90(flPlay);
        tvTitle.setText(content);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UartRxData.ACU_CurrentplaySt == 1) { //播放
                    isPlay = true;
                } else {
                    isPlay = false;
                }
            }
        }, 1000);
        playStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlay) {
                    playStatus.setImageResource(R.drawable.steering_wheel_music_stop);
                } else {
                    playStatus.setImageResource(R.drawable.steering_wheel_music_play);
                }
                isPlay = !isPlay;
                ON_OFF_STATUS_VD(isPlay);
            }
        });

        rightBottom.setScrollViewListener(new CustomerScrollView.ScrollViewListener() {
            @Override
            public void isBottom(boolean isbottom) {
                if (dialogCallBack != null) {
                    dialogCallBack.backCome();
                }
            }
        });

        flPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();
            }
        });
    }

    private void ON_OFF_STATUS_VD(boolean isOpen) {
        if (isOpen) {
            PAD_ACUPlayBtnSt = 0x00;
        } else {
            PAD_ACUPlayBtnSt = 0x01;
        }
        Log.d("ON_OFF_STATUS_VD", PAD_ACUPlayBtnSt + "");
    }

    @Override
    public void show() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        super.show();
        fullScreenImmersive(getWindow().getDecorView());
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    public DialogCallBack dialogCallBack;


    public void setOnClickDialogCallBack(DialogCallBack dialogCallBack){
        this.dialogCallBack = dialogCallBack;
    }

    public interface DialogCallBack{
        void backCome();
    }

}











