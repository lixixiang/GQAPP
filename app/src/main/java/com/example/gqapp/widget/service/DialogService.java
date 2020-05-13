package com.example.gqapp.widget.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.example.gqapp.widget.dialog.CustomDialog;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/27 4:19
 * @Author: 李熙祥
 * @Description: java类作用描述 全局弹出对话框
 */
public class DialogService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showDialog();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showDialog() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CustomDialog customDialog = new CustomDialog(getApplicationContext(), 1);
                Display display = customDialog.getWindow().getWindowManager().getDefaultDisplay();
                WindowManager.LayoutParams lp = customDialog.getWindow().getAttributes();
                customDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                lp.width = (int) (display.getWidth() * 0.5);
                lp.height = display.getHeight();
                lp.gravity = Gravity.LEFT;
                customDialog.getWindow().setAttributes(lp);
                customDialog.show();

            }
        }, 3 * 1000);
    }
}
