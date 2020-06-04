package com.example.gqapp.fragment.left.sub;

import android.speech.tts.Voice;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.gqapp.R;
import com.example.gqapp.base.BaseFragment;
import com.example.gqapp.bean.DialogBean;
import com.example.gqapp.bean.EventBean;
import com.example.gqapp.utils.EventBusUtil;
import com.example.gqapp.widget.voice.SoundWaveView;
import com.example.gqapp.widget.voice.VoiceLineView;

import butterknife.BindView;
import butterknife.OnTouch;

import static com.example.gqapp.app.Constance.LEFT_RIGHT_VALUE;
import static com.example.gqapp.app.Constance.START_PHONE;
import static com.example.gqapp.app.Constance.START_PHONE_TEST;
import static com.example.gqapp.app.Constance.TIPS_INFO;
import static com.example.gqapp.app.Constance.TIPS_MAIN_LEFT_INFO;
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
    Button ivBtnVoice;
    @BindView(R.id.wave_view)
    SoundWaveView waveView;
    private boolean onClick;
    private int VoiceValue = 0;


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
                waveView.setSoundVolume(10);
                waveView.start();
                break;
            case TIPS_MAIN_LEFT_INFO: //提示信息弹出框 总共 4 个
                DialogBean bean = (DialogBean) event.getData();
                EventBean<DialogBean> beanEventBean = new EventBean<DialogBean>(TIPS_INFO, bean);
                EventBusUtil.sendEvent(beanEventBean);
                break;
            case START_PHONE_TEST:
                int type2 = (int) event.getData();
                Log.d("START_PHONE", "START_PHONE+MAIN_LEFT===="+type2 + "");
                EventBean<Integer> eventBean2 = new EventBean<Integer>(START_PHONE, type2);
                EventBusUtil.sendEvent(eventBean2);
                break;
        }
    }
}


