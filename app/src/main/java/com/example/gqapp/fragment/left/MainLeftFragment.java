package com.example.gqapp.fragment.left;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gqapp.R;
import com.example.gqapp.app.Constance;
import com.example.gqapp.app.MyApplication;
import com.example.gqapp.base.BaseFragment;
import com.example.gqapp.bean.EventBean;
import com.example.gqapp.fragment.left.sub.MeterFragment;
import com.example.gqapp.fragment.left.sub.WeChatFragment;
import com.example.gqapp.utils.EventBusUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/20 16:30
 * @Author: 李熙祥
 * @Description: java类作用描述 左边App 主界面入口
 */
public class MainLeftFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_title2)
    TextView tvTitle2;
    @BindView(R.id.iv_icon2)
    ImageView ivIcon2;
    @BindView(R.id.iv_left_icon)
    ImageView ivLeftIcon;
    @BindView(R.id.tv_mid_title)
    TextView tvMidTitle;
    @BindView(R.id.iv_right_icon)
    ImageView ivRightIcon;
    private int[] iconDataL = {R.drawable.car01, R.drawable.car02};
    private String[] string = MyApplication.getAppContext().getResources().getStringArray(R.array.main_left_titles);


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
        tvTitle.setText(string[0]);
        tvTitle2.setText(string[1]);
        tvMidTitle.setText(R.string.str_view);
        ivIcon.setImageResource(iconDataL[0]);
        ivIcon2.setImageResource(iconDataL[1]);

    }

    @OnClick({R.id.ll_adns, R.id.ll_wechat,R.id.tv_mid_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_adns:
                startFragmentLeft(MyViewPagerFragment.newInstance(0));
                break;
            case R.id.ll_wechat:
                startFragmentLeft(WeChatFragment.newInstance());
                break;
            case R.id.tv_mid_title:
                startFragmentLeft(MeterFragment.newInstance());
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




