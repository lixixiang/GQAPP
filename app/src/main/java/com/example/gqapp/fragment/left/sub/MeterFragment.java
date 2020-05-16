package com.example.gqapp.fragment.left.sub;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.example.gqapp.R;
import com.example.gqapp.app.MyApplication;
import com.example.gqapp.base.BaseFragment;
import com.example.gqapp.bean.EventBean;
import com.example.gqapp.fragment.adapter.SmartPagerAdapter;
import com.example.gqapp.utils.EventBusUtil;
import com.example.gqapp.widget.transformer.VerticalScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.example.gqapp.app.Constance.CAR_MODE;
import static com.example.gqapp.app.Constance.LEFT_RIGHT_VALUE;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/23 9:43
 * @Author: 李熙祥
 * @Description: java类作用描述 仪表功能页
 */
public class MeterFragment extends BaseFragment {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private int[] images = {R.drawable.iv_meter_04, R.drawable.iv_meter_01, R.drawable.iv_meter_02, R.drawable.iv_meter_03, R.drawable.iv_meter_04, R.drawable.iv_meter_01};
    private List<ImageView> mList = new ArrayList<>();
    private String[] titles;
    TextView textView;
    private int currentPosition,currentItem;

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
        viewpager.setOffscreenPageLimit(3); //预加载三个
        titles = getResources().getStringArray(R.array.meters);

        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(_mActivity);
            imageView.setImageResource(images[i]);
            mList.add(imageView);
        }

        viewpager.setAdapter(new SmartPagerAdapter(_mActivity, mList));
        viewpager.setCurrentItem(currentItem);
        viewpager.setPageTransformer(true, new VerticalScaleInTransformer());
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                MyApplication.put(_mActivity,CAR_MODE,currentPosition);
                Log.d("currentPosition", currentPosition + "");
                if (position == 0) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            viewpager.setCurrentItem(mList.size() - 2, false);//跳转到末位
                        }
                    }, 500);

//                tvTitle.setText(titles[position]);
//                        Log.d("test", titles[position]+"      "+position);
                } else if (position == mList.size() - 1) {
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
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    tvTitle.setVisibility(View.VISIBLE);
                    if (currentPosition == 0) {
                        viewpager.setCurrentItem(mList.size() - 2, false);
                    } else if (currentPosition == mList.size() - 1) {
                        viewpager.setCurrentItem(1, false);
                    }
                } else {
                    tvTitle.setVisibility(View.GONE);
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
}
