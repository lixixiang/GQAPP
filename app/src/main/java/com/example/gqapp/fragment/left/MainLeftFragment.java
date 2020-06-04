package com.example.gqapp.fragment.left;

import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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
import com.example.gqapp.fragment.left.sub.MeterFragment;
import com.example.gqapp.fragment.left.sub.WeChatFragment;
import com.example.gqapp.utils.EventBusUtil;
import com.example.gqapp.utils.uart.UartTxData;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.gqapp.app.Constance.CAR_MODE;
import static com.example.gqapp.app.Constance.LEFT_MAIN_MODE_VALUE;
import static com.example.gqapp.app.Constance.START_PHONE;
import static com.example.gqapp.app.Constance.START_PHONE_TEST;
import static com.example.gqapp.app.Constance.TIPS_INFO;
import static com.example.gqapp.app.Constance.TIPS_MAIN_LEFT_INFO;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/20 16:30
 * @Author: 李熙祥
 * @Description: java类作用描述 左边App 主界面入口
 */
public class MainLeftFragment extends BaseFragment {
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
    private int currentItem = 1; //view下当前显示的模式

    public static final MainLeftFragment newInstance() {
        MainLeftFragment fragment = new MainLeftFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.main_left;
    }

    @Override
    protected void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                DialogBean bean = new DialogBean(0x11, 0);
//                EventBean<DialogBean> eventBean = new EventBean<DialogBean>(TIPS_INFO, bean);
//                EventBusUtil.sendEvent(eventBean);

//                EventBean<Integer> eventBean = new EventBean<Integer>(START_PHONE, 0x11);
//                EventBusUtil.sendEvent(eventBean);
            }
        }, 8000);

        Typeface typeface = Typeface.createFromAsset(_mActivity.getAssets(), "Roboto_Regular.ttf");
        tvMidTitle.setTypeface(typeface);
        tvMidTitle.setText(R.string.str_view);
        currentItem = (int) MyApplication.get(_mActivity, CAR_MODE, currentItem);
        ivCarSpeedDistance.setImageResource(R.drawable.steering_wheel_left_screen_icon_adas);
        ivWechat.setImageResource(R.drawable.steering_wheel_left_screen_icon_wechat);
        UartTxData.PAD_ICM_ViewSt = currentItem;
    }

    @OnClick({R.id.iv_car_speed_distance, R.id.iv_wechat, R.id.re_right_bottom, R.id.iv_left_icon, R.id.iv_right_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_car_speed_distance:
                startFragmentLeft(MyViewPagerFragment.newInstance(0));
                break;
            case R.id.iv_wechat:
                startFragmentLeft(WeChatFragment.newInstance());
                break;
            case R.id.re_right_bottom:
                startFragmentLeft(MeterFragment.newInstance(currentItem));
                break;
            case R.id.iv_left_icon:
                currentItem++;
                EventBean<Integer> eventBean = new EventBean<Integer>(LEFT_MAIN_MODE_VALUE, currentItem);
                EventBusUtil.sendEvent(eventBean);

                break;
            case R.id.iv_right_icon:
                currentItem--;
                EventBean<Integer> eventBean2 = new EventBean<Integer>(LEFT_MAIN_MODE_VALUE, currentItem);
                EventBusUtil.sendEvent(eventBean2);
                break;
            default:
                break;
        }
    }


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(EventBean event) {
        switch (event.getCode()) {
            case LEFT_MAIN_MODE_VALUE:
                if (currentItem > 4) {
                    currentItem = 1;
                } else if (currentItem < 0) {
                    currentItem = 4;
                }
                UartTxData.PAD_ICM_ViewSt = currentItem;
                MyApplication.put(_mActivity, CAR_MODE, currentItem);
                Log.d("currentItem", currentItem + "");
                break;
            case TIPS_MAIN_LEFT_INFO: //提示信息弹出框 总共 4 个
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
        EventBean<String> eventBean = new EventBean<String>(Constance.GONE_LEFT_COME_BACK_LINE, MainLeftFragment.class.getName());
        EventBusUtil.sendEvent(eventBean);
    }
}




