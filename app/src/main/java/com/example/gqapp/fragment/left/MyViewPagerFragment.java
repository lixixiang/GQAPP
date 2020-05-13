package com.example.gqapp.fragment.left;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.gqapp.R;
import com.example.gqapp.base.BaseFragment;
import com.example.gqapp.bean.CommonBean;
import com.example.gqapp.bean.EventBean;
import com.example.gqapp.fragment.adapter.FragmentAdapter;
import com.example.gqapp.fragment.left.sub.ADASFragment;
import com.example.gqapp.utils.EventBusUtil;
import com.example.gqapp.widget.VerticalViewPager;

import java.util.ArrayList;

import butterknife.BindView;

import static com.example.gqapp.app.Constance.LEFT_RIGHT_VALUE;
import static com.example.gqapp.app.Constance.TIPS_INFO_LEFT;


/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/22 2:09
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class MyViewPagerFragment extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.viewpager1)
    VerticalViewPager viewpager;
    @BindView(R.id.ll_dot)
    LinearLayout llDot;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ImageView[] mDots;//底部小圆点的集合
    //记录当前选中位置
    private int currentIndex;
    private boolean isPress;//手指是否触摸屏幕
    //判断是左还是右显示
    private int isLeftRight;
    CommonBean bean;


    public static final MyViewPagerFragment newInstance(int isLeftRight) {
        MyViewPagerFragment fragment = new MyViewPagerFragment();
        fragment.isLeftRight = isLeftRight;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_viewpager;
    }

    @Override
    protected void initView() {
        switch (isLeftRight) {
            case 0:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EventBean<Integer> eventBean = new EventBean<Integer>(TIPS_INFO_LEFT, 1);
                        EventBusUtil.sendEvent(eventBean);
                    }
                },5000);
                break;
            case 1:
                break;
        }
        initData();
    }

    private void initData() {
        switch (isLeftRight) {
            case 0:
                mFragments.add(ADASFragment.newInstance(30, 120, 5, R.drawable.iv_adas_normal, R.drawable.iv_adas_selector, "km/h", android.R.color.white));
                mFragments.add(ADASFragment.newInstance(1, 3, 1, R.drawable.iv_distance_normal, R.drawable.iv_distance_selector, "", android.R.color.white));
                mFragments.add(ADASFragment.newInstance(30, 120, 5, R.drawable.iv_adas_normal, R.drawable.iv_adas_selector, "km/h", android.R.color.white));
                mFragments.add(ADASFragment.newInstance(1, 3, 1, R.drawable.iv_distance_normal, R.drawable.iv_distance_selector, "", android.R.color.white));
                break;
            case 1:
                mFragments.add(ADASFragment.newInstance(16, 30, 0.5, R.drawable.iv_ac, R.drawable.iv_ac, "℃", R.color.blue));
                mFragments.add(ADASFragment.newInstance(1, 5, 1, R.drawable.iv_ac, R.drawable.iv_ac, "ac", R.color.green));
                mFragments.add(ADASFragment.newInstance(16, 30, 0.5, R.drawable.iv_ac, R.drawable.iv_ac, "℃", R.color.blue));
                mFragments.add(ADASFragment.newInstance(1, 5, 1, R.drawable.iv_ac, R.drawable.iv_ac, "ac", R.color.green));
                break;
        }

        mDots = new ImageView[mFragments.size()];
        for (int i = 0; i < mFragments.size() - 2; i++) {
            mDots[i] = (ImageView) llDot.getChildAt(i);
            mDots[i].setEnabled(true);
            mDots[i].setOnClickListener(this);
            mDots[i].setTag(i); //设置位置tag,方便取出与当前位对应
        }
        currentIndex = 0;
        viewpager.setCurrentItem(1);
        mDots[currentIndex].setEnabled(false);//设置为灰黑色
        viewpager.setAdapter(new FragmentAdapter(getChildFragmentManager(), mFragments));
        viewpager.addOnPageChangeListener(this);
    }

    /**
     * 这只当前引导小点的图标
     */
    private void setCurDot(int position) {
        if (position < 0 || position > mFragments.size() - 1 || currentIndex == position) {
            return;
        }
        mDots[position].setEnabled(false);
        mDots[currentIndex].setEnabled(true);
        currentIndex = position;
    }

    /**
     * 设置当前的引导页
     */
    private void setCurView(int position) {
        if (position < 0 || position >= mFragments.size()) {
            return;
        }
        viewpager.setCurrentItem(position);
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d("positionOffset", positionOffset+"   isPress    "+isPress +"   "+position);
        bean = new CommonBean(isLeftRight,positionOffset,isPress,position);

    }

    @Override
    public void onPageSelected(int position) {
        //设置底部小点选中状态
        setCurDot((mFragments.size() - position) % 2);
        if (position == 0) {//首位扩展的item
            //延迟执行才能看到动画
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewpager.setCurrentItem(mFragments.size() - 2, false);//跳转到末位

                }
            }, 500);
        } else if (position == mFragments.size() - 1) {//末位扩展的item
            //延迟执行才能看到动画
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewpager.setCurrentItem(1, false);//跳转到首位
                }
            }, 500);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         *  判断是从左边还是右边进入viewpager 页面
         */
        EventBean<Integer> eventBean = new EventBean<Integer>(LEFT_RIGHT_VALUE, isLeftRight);
        EventBusUtil.sendEvent(eventBean);
    }
}









