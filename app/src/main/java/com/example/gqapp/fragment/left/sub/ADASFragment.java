package com.example.gqapp.fragment.left.sub;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gqapp.R;
import com.example.gqapp.base.BaseFragment;
import com.example.gqapp.widget.PickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/21 3:28
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class ADASFragment extends BaseFragment {

    @BindView(R.id.pick)
    PickerView pick;
    List<String> data = new ArrayList<String>();
    int s = 0;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.v_line)
    View vLine;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    private boolean isOpen = false;
    private int startValue, endValue, normalPic, pressPic, scrollColor;
    private double progressValue;
    private String strUnit;

    public static final ADASFragment newInstance(int startValue, int endValue, double progressValue, int normalPic, int pressPic, String strUnit, int scrollColor) {
        ADASFragment fragment = new ADASFragment();
        fragment.startValue = startValue;
        fragment.endValue = endValue;
        fragment.progressValue = progressValue;
        fragment.normalPic = normalPic;
        fragment.pressPic = pressPic;
        fragment.strUnit = strUnit;
        fragment.scrollColor = scrollColor;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sub_adas;
    }

    @Override
    protected void initView() {
        if (strUnit.equals("ac")) {
            ivIcon.setVisibility(View.VISIBLE);
            tvUnit.setVisibility(View.GONE);
            ivIcon.setImageResource(R.drawable.iv_ac_wind);
            for (int i = startValue; i <= endValue; i += progressValue) {
                data.add(i + "");
            }
        } else if (strUnit.equals("℃")) {
            ivIcon.setVisibility(View.GONE);
            tvUnit.setVisibility(View.VISIBLE);
            for (float i = 16; i <= 30; i += 0.5) {
                data.add(i + "");
            }
        } else {
            ivIcon.setVisibility(View.GONE);
            tvUnit.setVisibility(View.VISIBLE);
            for (int i = startValue; i <= endValue; i += progressValue) {
                data.add(i + "");
            }
        }

        pick.getParent().requestDisallowInterceptTouchEvent(true);
        pick.setData(data);
        tvUnit.setText(strUnit);
        pick.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                Log.d("text", text);
                isOpen = true;
                changStatus(pressPic, scrollColor, R.color.green);
            }
        });
        OnOffStatus(isOpen);
    }

    private void OnOffStatus(boolean isOpen) {
        if (!isOpen) {
            changStatus(pressPic, scrollColor, R.color.green);
        } else {
            changStatus(normalPic, R.color.gray, R.color.gray);
        }
        this.isOpen = !isOpen;
    }

    /**
     * 改变点击时或滑动时的状态
     * @param pressPic 点击时图片
     * @param scrollColor 滑动时颜色
     * @param p  底线颜色
     */
    private void changStatus(int pressPic, int scrollColor, int p) {
        ivImg.setImageResource(pressPic);
        pick.setColorText(getResources().getColor(scrollColor));
        vLine.setBackgroundColor(getResources().getColor(p));
    }

    @OnClick(R.id.iv_img)
    public void onViewClicked() {
        OnOffStatus(isOpen);
    }
}
