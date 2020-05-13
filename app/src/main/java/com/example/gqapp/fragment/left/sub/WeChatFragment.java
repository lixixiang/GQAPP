package com.example.gqapp.fragment.left.sub;

import android.speech.tts.Voice;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.gqapp.R;
import com.example.gqapp.base.BaseFragment;
import com.example.gqapp.bean.EventBean;
import com.example.gqapp.utils.EventBusUtil;
import com.example.gqapp.widget.voice.SoundWaveView;
import com.example.gqapp.widget.voice.VoiceLineView;

import butterknife.BindView;
import butterknife.OnTouch;

import static com.example.gqapp.app.Constance.LEFT_RIGHT_VALUE;
import static com.example.gqapp.app.Constance.WECHAT_WAVE;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/21 3:28
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class WeChatFragment extends BaseFragment {

    @BindView(R.id.iv_voice_bg)
    ImageView ivVoiceBg;
    @BindView(R.id.iv_cancel)
    ImageView ivCancel;
    @BindView(R.id.iv_btn_voice)
    ImageButton ivBtnVoice;
    @BindView(R.id.wave_view)
    SoundWaveView waveView;
    private boolean onClick;
    private int VoiceValue = 0;
    short[] data = {
            91, -13, 12, -68, 138, -193, 150, 30, 186, -29, 6, -25, -127, -167, -26, -11, -164, 293, 86, -181, -82, 327, -81, 56, -3, -116, -165, 293, 86, -181,
            156, -13, 12, -68, 33, -104, 150, 30, 186, -29, 6, -25, -127, -167, -26, -25, -164, 293, 86, -181, -48, 327, -81, 56, -3, -116, -165, 293, 86, -181,
            91, -13, 12, -68, 230, -193, 150, 30, 186, -29, 6, -25, -127, -167, -101, -11, -164, 293, 86, -181, -82, 327, -81, 101, -3, -116, -165, -81, 101, -3,
            91, -13, 12, -68, 47, -193, 140, 30, 186, -29, 6, -25, -127, -167, -26, -11, -164, 293, 86, -181, -82, 116, -81, 56, -3, -17, -165, 30, 186, -29,
            91, -13, 12, -68, 138, -193, 150, 30, 186, -29, 6, -25, -127, -167, -26, -11, -164, 293, 86, -181, -82, 327, -81, 56, -3, -116, -165, 293, 86, -181,
            91, -13, 12, -68, 138, -193, 150, 30, 186, -29, 6, -25, -127, -167, -26, -11, -164, 293, 86, -181, -82, 327, -81, 56, -3, -116, -165, 293, 86, -181,
            91, -13, 12, -68, 138, -193, 150, 30, 186, -29, 6, -25, -127, -167, -26, -11, -164, 293, 86, -181, -82, 327, -81, 56, -3, -116, -165, 293, 86, -181,
            91, -13, 12, -68, 138, 138, -193, 150, 30, 186, -29, -127, -167, -26, -11, -164, 293, 86, -181, -82, 327, -81, 56, -3, -116, -165, 293, 86, -181,
            91, -13, 12, -68, 138, -193, 150, 30, 186, -29, 6, -25, -127, -167, -26, -11, -164, 293, 86, -181, -82, 327, -81, 56, -3, -116, -165, 293, 86, -181,
            91, -13, 12, -68, 138, -193, 150, 30, 186, -29, 6, -25, -127, -167, -26, -11, -164, 293, 86, -181, -82, 327, -81, 56, -3, -116, -165, 293, 86, -181,
            91, -13, 12, -68, 138, -193, 150, 30, 186, -29, 6, -25, -127, -167, -26, -11, -164, 293, 86, -181, -82, 327, -81, 56, -3, -116, -165, 293, 86, -181,
            91, -13, 12, -68, 138, -193, 150, 30, 186, -29, 6, -25, -127, -167, -26, -11, -164, 293, 86, 3, 86, -181, -82, 327, -81, 56, -116, -165, 293, 86, -181,
            91, -13, 12, -68, 138, -193, 150, 30, 186, -29, 6, -25, -127, -167, -26, -11, -164, 293, 86, -181, -82, 327, -81, 56, -3, -116, -165, 293, 86, -181,
            91, -13, 12, -68, 138, -193, 150, 30, 186, -29, 6, -25, -127, -167, -26, -11, -164, 293, 86, -181, -82, 327, -81, 56, -3, -116, -165, 293, 86, -181,
            91, -13, 12, 293, 86, -181, -82, 327, -81, 56, -3, -116, -165, 6, -25, -127, -167, -26, -11, -164, 293, 86, -181, -82, 327, -81, 56, -3, -116, -165, 293, 86, -181,
            91, -13, 12, -68, 138, -193, 150, 30, 186, -29, 6, -25, -127, -167, -26, -11, -164, 293, 86, -181, -82, 327, -81, 56, -3, -116, -165, 293, 86, -181,
            91, -13, 12, -68, 138, -193, 150, 30, 186, -29, 6, -25, -127, -167, -26, -11, -164, 293, 86, -181, -82, 327, -81, 56, -3, -116, -165, 293, 86, -181,
            91, -13, 12, -68, 138, -193, 150, 30, 186, -29, 6, -25, -127, -167, -26, -11, -164, 293, 86, -181, -82, 327, -81, 56, -3, -116, -165, 293, 86, -181,
            91, -13, 12, -68, 138, -193, 150, 30, 186, -29, 6, -25, -127, -167, -26, -11, -164, 293, 86, -181, -82, 327, -81, 56, -3, -116, -165, 293, 86, -181,
            91, -13, 12, -68, 138, -193, 150, 30, 186, -29, 6, -25, -127, -167, -26, -11, -164, 293, 86, -181, -82, 327, -81, 56, -3, -116, -165, 293, 86, -181,
    };

    public static final WeChatFragment newInstance() {
        WeChatFragment fragment = new WeChatFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sub_wechat;
    }

    @Override
    protected void initView() {

        ivVoiceBg.setColorFilter(getResources().getColor(R.color.wechat_green_main));
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                EventBean<Integer> eventBean = new EventBean<Integer>(TIPS_INFO_LEFT, 3);
//                EventBusUtil.sendEvent(eventBean);
//            }
//        }, 5000);

    }

    @OnTouch({R.id.iv_btn_voice})
    boolean onViewTouchClicked(View view, MotionEvent event) {
        switch (view.getId()) {
            case R.id.iv_btn_voice:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    onClick = true;
                    Thread t = new Thread() {
                        @Override
                        public void run() {
                            while (onClick) {
                                VoiceValue += 1;
                                EventBean<Integer> eventFlSwitch = new EventBean<Integer>(WECHAT_WAVE, VoiceValue);
                                EventBusUtil.sendEvent(eventFlSwitch);
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                    t.start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    onClick = false;
                    waveView.stop();
                }
                break;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBean<Integer> eventBean = new EventBean<Integer>(LEFT_RIGHT_VALUE, 0);
        EventBusUtil.sendEvent(eventBean);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(EventBean event) {
        switch (event.getCode()) {
            case WECHAT_WAVE:
                VoiceValue = (int) event.getData();
                Log.d("ddddddddd", VoiceValue + "");
                if (VoiceValue > data.length) {
                    VoiceValue = data.length;
                }
                waveView.setSoundVolume(10);
                waveView.start();
                break;
        }
    }
}









