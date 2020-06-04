package com.example.gqapp.widget.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.example.gqapp.R;
import com.example.gqapp.app.MyApplication;
import com.example.gqapp.utils.AnimOrientation;
import com.example.gqapp.utils.Utils;
import com.example.gqapp.utils.uart.UartTxData;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.gqapp.app.Constance.NECK_PRO;
import static com.example.gqapp.app.Constance.SOUND;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/5/24 23:20
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class CustomVoiceDialog extends AlertDialog {
    @BindView(R.id.sb_headrest)
    SeekBar sbHeadrest;
    @BindView(R.id.sb_surround)
    SeekBar sbSurround;
    @BindView(R.id.ll_dialog_vmm)
    LinearLayout llDialogVmm;
    private Activity mActivity;

    private int currentVolume, currentVolume2;

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

    public CustomVoiceDialog(Activity activity) {
        super(activity, R.style.voiceDialog);
        mActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_voice);
        ButterKnife.bind(this);
        showBottomUIMenu();
        AnimOrientation.rightRotation90(llDialogVmm);
        currentVolume = (int) MyApplication.get(mActivity, NECK_PRO, -1);
        currentVolume2 = (int) MyApplication.get(mActivity, SOUND, -1);
        sbHeadrest.setProgress(currentVolume);
        sbSurround.setProgress(currentVolume2);
        UartTxData.PAD_HeadrestSetting = currentVolume;
        UartTxData.PAD_SoundSurroundSetting = currentVolume2;

        sbSurround.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setThumb(mActivity.getResources().getDrawable(R.drawable.iv_thumb_seek));
                currentVolume2 = progress;
                UartTxData.PAD_SoundSurroundSetting = currentVolume2;
                sbSurround.setProgress(currentVolume2);
                MyApplication.put(mActivity, SOUND, currentVolume2);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBar.setThumb(mActivity.getResources().getDrawable(R.drawable.iv_thumb_seek));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setThumb(null);
            }
        });

        sbHeadrest.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setThumb(mActivity.getResources().getDrawable(R.drawable.iv_thumb_seek));
                currentVolume = progress;
                UartTxData.PAD_HeadrestSetting = currentVolume;
                sbHeadrest.setProgress(currentVolume);
                MyApplication.put(mActivity, NECK_PRO, currentVolume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBar.setThumb(mActivity.getResources().getDrawable(R.drawable.iv_thumb_seek));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setThumb(null);
            }
        });

        llDialogVmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void show() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        super.show();
        fullScreenImmersive(getWindow().getDecorView());
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }
}
