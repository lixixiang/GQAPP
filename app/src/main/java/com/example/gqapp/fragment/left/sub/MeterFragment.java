package com.example.gqapp.fragment.left.sub;

import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.example.gqapp.R;
import com.example.gqapp.app.MyApplication;
import com.example.gqapp.base.BaseFragment;
import com.example.gqapp.bean.DialogBean;
import com.example.gqapp.bean.EventBean;
import com.example.gqapp.fragment.adapter.SmartPagerAdapter;
import com.example.gqapp.fragment.adapter.VerticalPagerAdapter;
import com.example.gqapp.utils.EventBusUtil;
import com.example.gqapp.utils.uart.UartTxData;
import com.example.gqapp.widget.transformer.VerticalScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.example.gqapp.app.Constance.CAR_MODE;
import static com.example.gqapp.app.Constance.LEFT_RIGHT_VALUE;
import static com.example.gqapp.app.Constance.START_PHONE;
import static com.example.gqapp.app.Constance.START_PHONE_TEST;
import static com.example.gqapp.app.Constance.TIPS_INFO;
import static com.example.gqapp.app.Constance.TIPS_MAIN_LEFT_INFO;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/23 9:43
 * @Author: 李熙祥
 * @Description: java类作用描述 仪表功能页
 */
public class MeterFragment extends BaseFragment {

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private int[] images = {R.drawable.steering_wheel_theme_adjustment_1_btn_vehicle_show3,
            R.drawable.steering_wheel_theme_adjustment_1_btn_drive_show1,
            R.drawable.steering_wheel_theme_adjustment_1_btn_navigation_show2,
            R.drawable.steering_wheel_theme_adjustment_1_btn_vehicle_show3,
            R.drawable.steering_wheel_theme_adjustment_1_btn_drive_show1};
    private int currentPosition, currentItem;

    public static final MeterFragment newInstance(int currentItem) {
        MeterFragment fragment = new MeterFragment();
        fragment.currentItem = currentItem;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_meter;
    }

    @Override
    protected void initView() {
        currentPosition = (int) MyApplication.get(_mActivity, CAR_MODE, 0);
        UartTxData.PAD_ICM_ViewSt = currentPosition;

        viewpager.setOffscreenPageLimit(3); //预加载三个
        viewpager.setAdapter(new VerticalPagerAdapter(_mActivity, images));
        viewpager.setCurrentItem(currentItem);
        viewpager.setPageTransformer(true, new VerticalScaleInTransformer());
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("ddddddddd", positionOffset + "         " + positionOffsetPixels);
               //从左至右滑 【0-1】
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                UartTxData.PAD_ICM_ViewSt = currentPosition;
                MyApplication.put(_mActivity, CAR_MODE, currentPosition);
                Log.d("currentPosition", currentPosition + "");
                if (position == 0) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            viewpager.setCurrentItem(images.length - 2, false);//跳转到末位
                        }
                    }, 500);

                } else if (position == images.length - 1) {
                    new Handler().postDelayed(new Runnable() {//末位扩展的item
                        @Override
                        public void run() {
                            viewpager.setCurrentItem(1, false);//跳转到首位
                        }
                    }, 500);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("state", state + "         " );
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (currentPosition == 0) {
                        viewpager.setCurrentItem(images.length - 2, false);
                    } else if (currentPosition == images.length - 1) {
                        viewpager.setCurrentItem(1, false);
                    }
                } else {
                }
            }
        });
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
            case START_PHONE_TEST:
                int type2 = (int) event.getData();
                Log.d("START_PHONE", "START_PHONE+MAIN_LEFT===="+type2 + "");
                EventBean<Integer> eventBean2 = new EventBean<Integer>(START_PHONE, type2);
                EventBusUtil.sendEvent(eventBean2);
                break;
            case TIPS_MAIN_LEFT_INFO: //提示信息弹出框 总共 4 个
                DialogBean bean = (DialogBean) event.getData();
                EventBean<DialogBean> beanEventBean = new EventBean<DialogBean>(TIPS_INFO, bean);
                EventBusUtil.sendEvent(beanEventBean);
                break;
        }
    }
}
