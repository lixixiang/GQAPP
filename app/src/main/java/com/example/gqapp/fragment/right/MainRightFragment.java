package com.example.gqapp.fragment.right;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gqapp.R;
import com.example.gqapp.app.Constance;
import com.example.gqapp.app.MyApplication;
import com.example.gqapp.base.BaseFragment;
import com.example.gqapp.bean.EventBean;
import com.example.gqapp.fragment.left.MyViewPagerFragment;
import com.example.gqapp.utils.EventBusUtil;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

import static com.example.gqapp.app.Constance.RIGHT_MAIN_VOICE_VALUE;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/20 16:30
 * @Author: 李熙祥
 * @Description: java类作用描述 左边App 主界面入口
 */
public class MainRightFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_title2)
    TextView tvTitle2;
    @BindView(R.id.iv_icon2)
    ImageView ivIcon2;
    @BindView(R.id.iv_left_icon)
    Button ivLeftIcon;
    @BindView(R.id.tv_mid_title)
    TextView tvMidTitle;
    @BindView(R.id.iv_right_icon)
    Button ivRightIcon;
    @BindView(R.id.iv_voice_bg)
    ImageView ivVoice;
    private int[] iconDataL = {R.drawable.car03, R.drawable.car04};
    private String[] string = MyApplication.getAppContext().getResources().getStringArray(R.array.main_right_titles);
    private boolean onClick;
    private int VoiceValue = 60;

    public static final MainRightFragment newInstance() {
        MainRightFragment fragment = new MainRightFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.main_right;
    }

    @Override
    protected void initView() {
        tvTitle.setText(string[0]);
        tvTitle2.setText(string[1]);
        tvMidTitle.setText(VoiceValue + "%");
        ivIcon.setImageResource(iconDataL[0]);
        ivIcon2.setImageResource(iconDataL[1]);
        ivLeftIcon.setBackgroundResource(R.drawable.iv_minus);
        ivRightIcon.setBackgroundResource(R.drawable.add_voice);
        ivVoice.setImageResource(R.drawable.ic_souhu_player_volume);
    }

    @OnClick({R.id.ll_vrv, R.id.ll_wmm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_vrv:
                startFragmentRight(MyViewPagerFragment.newInstance(1));
                break;
            case R.id.ll_wmm:
                startFragmentRight(WMMFragment.newInstance(1));
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
                                VoiceValue-=5;
                                EventBean<Integer> eventFlSwitch = new EventBean<Integer>(RIGHT_MAIN_VOICE_VALUE, VoiceValue);
                                EventBusUtil.sendEvent(eventFlSwitch);
                                try {
                                    Thread.sleep(300);
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
                                VoiceValue+=5;
                                EventBean<Integer> eventFlSwitch = new EventBean<Integer>(RIGHT_MAIN_VOICE_VALUE, VoiceValue);
                                EventBusUtil.sendEvent(eventFlSwitch);
                                try {
                                    Thread.sleep(300);
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
                Log.d("VoiceValue", VoiceValue + "");
                if (VoiceValue >= 100) {
                    VoiceValue = 100;
                } else if (VoiceValue <= 0) {
                    VoiceValue = 0;
                }
                tvMidTitle.setText(VoiceValue + "%");
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


