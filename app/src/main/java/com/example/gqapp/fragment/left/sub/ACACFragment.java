package com.example.gqapp.fragment.left.sub;

import android.util.Log;

import com.example.gqapp.R;
import com.example.gqapp.base.BaseFragment;
import com.example.gqapp.bean.ACACCean;
import com.example.gqapp.bean.EventBean;
import com.example.gqapp.utils.EventBusUtil;
import com.example.gqapp.utils.uart.UartTxData;
import com.example.gqapp.widget.PickerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.example.gqapp.app.Constance.TEXT_COLOR2;
import static com.example.gqapp.app.Constance.VIEWPAGER_TEXT_COLOR;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/21 3:28
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class ACACFragment extends BaseFragment {

    @BindView(R.id.pick)
    PickerView pick;

    private int startValue, endValue, currentPos;
    private double progressValue;
    private String selectValue;
    List<String> data = new ArrayList<String>();
    List<String> data2 = new ArrayList<String>();
    private boolean isOpen = true;
    ACACCean acacCean = new ACACCean();

    public static final ACACFragment newInstance(int startValue, int endValue, double progressValue, int currentPos) {
        ACACFragment fragment = new ACACFragment();
        fragment.startValue = startValue;
        fragment.endValue = endValue;
        fragment.progressValue = progressValue;
        fragment.currentPos = currentPos;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sub_adas;
    }

    @Override
    protected void initView() {
        initViewData(startValue, endValue, progressValue, data, data2);
        pick.setSelected(selectValue);

        pick.getParent().requestDisallowInterceptTouchEvent(true);
        pick.setData(data);

        pick.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectValue = text;
                Log.d("text", text);
                setSelectValue(selectValue);
                changStatus(android.R.color.white);
                acacCean.setType(currentPos);
                acacCean.setOpen(true);

                EventBean<ACACCean> bean = new EventBean<ACACCean>(VIEWPAGER_TEXT_COLOR,acacCean);
                EventBusUtil.sendEvent(bean);
            }
        });
        OnOffStatus(isOpen);
    }

    /**
     * 选中值来进较对应的 int数组表
     *
     * @param text
     */
    private void setSelectValue(String text) {
        for (int i = 0; i < data2.size(); i++) {
            if (data2.get(i).equals(text)) {
              if (currentPos == 0) {
                    UartTxData.PAD_HVACFR_DrTempSetting = i;
                    return;
                } else if (currentPos == 1) {
                    UartTxData.PAD_HVACFR_WindSpeedSetting = i;
                    return;
                }
            }
        }
    }

    private void OnOffStatus(boolean isOpen) {
        if (isOpen) {
            changStatus(android.R.color.white);
        } else {
            changStatus(R.color.gray);
        }
    }


    /**
     * 改变点击时或滑动时的状态
     *
     * @param scrollColor 滑动文字颜色
     */
    private void changStatus(int scrollColor) {
        pick.setColorText(getResources().getColor(scrollColor));
    }

    private void initViewData(int startValue, int endValue, double progressValue,
                              List<String> data, List<String> data2) {
        if (progressValue == 0.5) {
            for (float i = startValue; i <= endValue; i += progressValue) {
                data.add(i + "");
            }
        } else {
            for (int i = startValue; i <= endValue; i += progressValue) {
                data.add(i + "");
            }
        }
        data2.addAll(data);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(EventBean event) {
        switch (event.getCode()) {
            case TEXT_COLOR2:
                ACACCean bean2 = (ACACCean) event.getData();
                Log.d("TEXT_COLOR2",  bean2.isOpen() +"");
                OnOffStatus(bean2.isOpen());
                break;
        }
    }
}
