package com.example.gqapp.base;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.IntegerRes;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.gqapp.R;
import com.example.gqapp.bean.EventBean;
import com.example.gqapp.utils.EventBusUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * created by lxx at 2020/3/8 23:34
 * 描述:
 */
public abstract class BaseActivity extends FragmentActivity {
    private Unbinder unbinder;
    public BaseActivity _mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        closeBar();
        //设置窗体全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置屏幕长亮
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //设置窗体背景模糊
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
//                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        hideBottomUIMenu();
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }

        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        _mActivity = this;
        this.setData();
        this.initView();
        this.setListener();
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    private void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View view = this.getWindow().getDecorView();
            view.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    /**
     * 设置数据
     */
    private void setData() {

    }


    protected abstract void initView();

    protected abstract int getLayoutId();

    /**
     * 设置监听
     */
    protected void setListener() {

    }

    protected boolean isRegisterEventBus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(EventBean event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(EventBean event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    protected void comeBack(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.dialog_enter,R.anim.dialog_exit).replace(R.id.left, fragment).commit();
    }

    protected void comeBack2(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.dialog_enter,R.anim.dialog_exit).replace(R.id.right, fragment).commit();
    }



    /**
     * 接收到分发的粘性事件
     *
     * @param event
     */
    protected void receiveStickyEvent(EventBean event) {

    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    protected void receiveEvent(EventBean event) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBusUtil.unregister(this);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    //关闭Android导航栏，实现全屏
    private void closeBar(){
        try {
            String command;
            command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib service call activity 42 s16 com.android.systemui";
            ArrayList<String> envlist = new ArrayList<String>();
            Map<String, String> env = System.getenv();
            for (String envName : env.keySet()) {
                envlist.add(envName + "=" + env.get(envName));
            }
            String[] envp = envlist.toArray(new String[0]);
            Process proc = Runtime.getRuntime().exec(
                    new String[] { "su", "-c", command }, envp);
            proc.waitFor();
        } catch (Exception ex) {
            // Toast.makeText(getApplicationContext(), ex.getMessage(),
            // Toast.LENGTH_LONG).show();        }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

}


