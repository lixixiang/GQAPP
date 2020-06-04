package com.example.gqapp.fragment.right;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gqapp.R;
import com.example.gqapp.app.Constance;
import com.example.gqapp.app.MyApplication;
import com.example.gqapp.base.BaseFragment;
import com.example.gqapp.bean.DialogBean;
import com.example.gqapp.bean.EventBean;
import com.example.gqapp.fragment.left.MyViewPagerFragment;
import com.example.gqapp.utils.EventBusUtil;
import com.example.gqapp.utils.Utils;
import com.example.gqapp.utils.uart.UartRxData;
import com.example.gqapp.utils.uart.UartTxData;
import com.example.gqapp.widget.dialog.CustomVoiceDialog;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

import static com.example.gqapp.app.Constance.RIGHT_MAIN_VOICE_VALUE;
import static com.example.gqapp.app.Constance.START_PHONE;
import static com.example.gqapp.app.Constance.START_PHONE_TEST;
import static com.example.gqapp.app.Constance.TIPS_INFO;
import static com.example.gqapp.app.Constance.TIPS_MAIN_RIGHT_INFO;
import static com.example.gqapp.app.Constance.VOICE_VALUE;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/20 16:30
 * @Author: 李熙祥
 * @Description: java类作用描述 左边App 主界面入口
 */
public class MainRightFragment extends BaseFragment {
    @BindView(R.id.re_bg)
    RelativeLayout reBg;
    @BindView(R.id.iv_car_speed_distance)
    ImageView ivCarSpeedDistance;
    @BindView(R.id.iv_wechat)
    ImageView ivWechat;
    @BindView(R.id.iv_left_icon)
    Button ivLeftIcon;
    @BindView(R.id.tv_mid_title)
    TextView tvMidTitle;
    @BindView(R.id.iv_right_icon)
    Button ivRightIcon;
    @BindView(R.id.re_right_bottom)
    RelativeLayout reRightBottom;
    private boolean onClick;
    private int VoiceValue = 60;

    private int currentVolume, currentVolume2;

    public static final MainRightFragment newInstance() {
        MainRightFragment fragment = new MainRightFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.main_left;
    }

    @Override
    protected void initView() {

        Utils.TextType(_mActivity, tvMidTitle, "Roboto_Regular.ttf");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                VoiceValue  = UartRxData.ACU_CurrentVol;
                tvMidTitle.setText(VoiceValue + "%");
            }
        }, 1000);

        ivCarSpeedDistance.setImageResource(R.drawable.steering_wheel_right_screen_icon_air_conditioning);
        ivWechat.setImageResource(R.drawable.steering_wheel_right_screen_icon_media);
        UartTxData.PAD_ACUVolSetting = VoiceValue;

    }

    @OnClick({R.id.iv_car_speed_distance, R.id.iv_wechat, R.id.re_right_bottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_car_speed_distance:
                startFragmentRight(MyViewPagerFragment.newInstance(1));
                break;
            case R.id.iv_wechat:
                startFragmentRight(WMMFragment.newInstance(1));
                break;
            case R.id.re_right_bottom:
                CustomVoiceDialog dialog = new CustomVoiceDialog(_mActivity);
                Display display = dialog.getWindow().getWindowManager().getDefaultDisplay();
                WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                //dialog 不抢占焦点
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
                dialog.getWindow().setWindowAnimations(R.style.horizontal_anim);
                dialog.show();
                lp.width = (int) (display.getWidth() * 0.5);
                lp.height = display.getHeight();
                lp.gravity = Gravity.RIGHT;
//                Bitmap bitmap = FastBlurUtil.getBlurBackgroundDrawer(_mActivity);
//                dialog.getWindow().setBackgroundDrawable(new BitmapDrawable(_mActivity.getResources(),bitmap));
                dialog.getWindow().setAttributes(lp);



//                SampleSupportDialogFragment dialogFragment = SampleSupportDialogFragment.newInstance(
//                        10, 2f, false, false, false, false);
//                dialogFragment.show(getChildFragmentManager(), "blur_sample");

                break;

        }
    }

    @OnTouch({R.id.iv_left_icon, R.id.iv_right_icon})
    boolean onViewTouchClicked(View view, MotionEvent event) {
        switch (view.getId()) {
            case R.id.iv_left_icon:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    onClick = true;
                    Thread t = new Thread() {
                        @Override
                        public void run() {
                            while (onClick) {
                                VoiceValue -= 5;
                                EventBean<Integer> eventFlSwitch = new EventBean<Integer>(RIGHT_MAIN_VOICE_VALUE, VoiceValue);
                                EventBusUtil.sendEvent(eventFlSwitch);
                                try {
                                    Thread.sleep(300);
                                    EventBean<Integer> eventFlSwitch2 = new EventBean<Integer>(RIGHT_MAIN_VOICE_VALUE, UartRxData.ACU_CurrentVol);
                                    EventBusUtil.sendEvent(eventFlSwitch2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    };
                    t.start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    onClick = false;
                }
                break;
            case R.id.iv_right_icon:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    onClick = true;
                    Thread t = new Thread() {
                        @Override
                        public void run() {
                            while (onClick) {
                                VoiceValue += 5;
                                EventBean<Integer> eventFlSwitch = new EventBean<Integer>(RIGHT_MAIN_VOICE_VALUE, VoiceValue);
                                EventBusUtil.sendEvent(eventFlSwitch);
                                try {
                                    Thread.sleep(300);
                                    EventBean<Integer> eventFlSwitch3 = new EventBean<Integer>(RIGHT_MAIN_VOICE_VALUE, UartRxData.ACU_CurrentVol);
                                    EventBusUtil.sendEvent(eventFlSwitch3);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    };
                    t.start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    onClick = false;
                }
                break;
        }
        return false;
    }


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(EventBean event) {
        switch (event.getCode()) {
            case RIGHT_MAIN_VOICE_VALUE:
                VoiceValue = (int) event.getData();
                if (VoiceValue >= 100) {
                    VoiceValue = 100;
                } else if (VoiceValue <= 0) {
                    VoiceValue = 0;
                }
                UartTxData.PAD_ACUVolSetting = VoiceValue;
                MyApplication.put(_mActivity, VOICE_VALUE, VoiceValue);
                VoiceValue = (int) MyApplication.get(_mActivity, VOICE_VALUE, -1);
                tvMidTitle.setText(VoiceValue + "%");
                break;
            case TIPS_MAIN_RIGHT_INFO: //提示信息弹出框 总共 4 个
                DialogBean bean = (DialogBean) event.getData();
                EventBean<DialogBean> beanEventBean = new EventBean<DialogBean>(TIPS_INFO, bean);
                EventBusUtil.sendEvent(beanEventBean);
                break;
            case START_PHONE_TEST:
                int type2 = (int) event.getData();
                EventBean<Integer> eventBean2 = new EventBean<Integer>(START_PHONE, type2);
                EventBusUtil.sendEvent(eventBean2);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBean<String> eventBean = new EventBean<String>(Constance.GONE_RIGHT_COME_BACK_LINE, MainRightFragment.class.getName());
        EventBusUtil.sendEvent(eventBean);
    }
}


