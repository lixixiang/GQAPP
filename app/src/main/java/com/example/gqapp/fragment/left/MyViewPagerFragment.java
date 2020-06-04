package com.example.gqapp.fragment.left;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.gqapp.R;
import com.example.gqapp.base.BaseFragment;
import com.example.gqapp.bean.ACACCean;
import com.example.gqapp.bean.ADASBean;
import com.example.gqapp.bean.DialogBean;
import com.example.gqapp.bean.EventBean;
import com.example.gqapp.fragment.adapter.FragmentAdapter;
import com.example.gqapp.fragment.left.sub.ACACFragment;
import com.example.gqapp.fragment.left.sub.ADASFragment;
import com.example.gqapp.utils.AnimationUtil;
import com.example.gqapp.utils.EventBusUtil;
import com.example.gqapp.utils.FrameAnimation;
import com.example.gqapp.utils.Utils;
import com.example.gqapp.utils.uart.UartTxData;
import com.example.gqapp.widget.transformer.AlphaTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.gqapp.app.Constance.LEFT_RIGHT_VALUE;
import static com.example.gqapp.app.Constance.START_PHONE;
import static com.example.gqapp.app.Constance.START_PHONE_TEST;
import static com.example.gqapp.app.Constance.TEXT_COLOR;
import static com.example.gqapp.app.Constance.TEXT_COLOR2;
import static com.example.gqapp.app.Constance.TIPS_INFO;
import static com.example.gqapp.app.Constance.TIPS_MAIN_LEFT_INFO;
import static com.example.gqapp.app.Constance.TIPS_MAIN_RIGHT_INFO;
import static com.example.gqapp.app.Constance.VIEWPAGER_TEXT_COLOR;


/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/22 2:09
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class MyViewPagerFragment extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.viewpager1)
    ViewPager viewpager;
    @BindView(R.id.ll_dot)
    LinearLayout llDot;
    @BindView(R.id.fl_viewpager_bg)
    FrameLayout flViewPagerBg;
    @BindView(R.id.iv_img)
    ImageView ivImage;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.iv_circle_img1)
    ImageView ivCircleImg1;
    @BindView(R.id.iv_circle_img2)
    ImageView ivCircleImg2;
    @BindView(R.id.iv_circle_img3)
    ImageView ivCircleImg3;
    @BindView(R.id.iv_circle_img4)
    ImageView ivCircleImg4;
    @BindView(R.id.iv_circle_img5)
    ImageView ivCircleImg5;


    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ImageView[] mDots;//底部小圆点的集合
    //记录当前选中位置
    private int currentIndex = 0;
    private int currentPos = 0;
    private boolean isPress = true;//点击按钮 adas
    private boolean isPressD = true; // distance
    private boolean isTxt = true; //风扇
    //判断是左还是右显示
    private int isLeftRight;
    FragmentAdapter adapter;

    public static final MyViewPagerFragment newInstance(int isLeftRight) {
        MyViewPagerFragment fragment = new MyViewPagerFragment();
        fragment.isLeftRight = isLeftRight;
        return fragment;
    }

    private int[] icons = {R.drawable.iv_circle_1, R.drawable.iv_circle_2, R.drawable.iv_circle_3, R.drawable.iv_circle_4, R.drawable.iv_circle_5};

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_viewpager;
    }

    @Override
    protected void initView() {
        initData();
    }

    private void initData() {
        startAnim();
        Utils.TextType(_mActivity, tvTitle, "Roboto_Regular.ttf");
        switch (isLeftRight) {
            case 0:
                ivImage.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.GONE);
                tvUnit.setText("km/h");
                if (isPress) {
                    ivImage.setImageResource(R.drawable.iv_adas_selector);
                } else {
                    ivImage.setImageResource(R.drawable.iv_adas_normal);
                }
                mFragments.add(ADASFragment.newInstance(30, 120, 5, 0));
                mFragments.add(ADASFragment.newInstance(1, 4, 1, 1));
                break;
            case 1:
                ivImage.setVisibility(View.GONE);
                tvTitle.setVisibility(View.VISIBLE);
                tvUnit.setText("℃");
                if (isTxt) {
                    tvTitle.setText("A/C");
                    tvTitle.setTextColor(getResources().getColor(R.color.green));
                } else {
                    tvTitle.setText("A/C");
                    tvTitle.setTextColor(getResources().getColor(R.color.gray));
                }
                mFragments.add(ACACFragment.newInstance(16, 30, 0.5, 0));
                mFragments.add(ACACFragment.newInstance(1, 5, 1, 1));
                break;
        }
        mDots = new ImageView[mFragments.size()];
        for (int i = 0; i < mFragments.size(); i++) {
            mDots[i] = (ImageView) llDot.getChildAt(i);
            mDots[i].setEnabled(true);
            mDots[i].setOnClickListener(this);
            mDots[i].setTag(i); //设置位置tag,方便取出与当前位对应
        }
        currentIndex = 0;
        viewpager.setCurrentItem(currentIndex);
        viewpager.setOffscreenPageLimit(2);
        mDots[currentIndex].setEnabled(false);//设置为灰黑色
        adapter = new FragmentAdapter(getChildFragmentManager(), mFragments);
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(this);
        viewpager.setPageTransformer(true, new AlphaTransformer());

    }

    private void startAnim() {
        List<ImageView> imageViews = new ArrayList<>();
        imageViews.add(ivCircleImg1);
        imageViews.add(ivCircleImg2);
        imageViews.add(ivCircleImg3);
        imageViews.add(ivCircleImg4);
        imageViews.add(ivCircleImg5);
        AnimationUtil.startAlphaSet(imageViews, 0f, 0.75f, 1000);
    }

    private void stopAnim() {

    }

    /**
     * 视频播放用于按钮周边效果
     */
//    private void videoStart() {
//        videoView.setVisibility(View.VISIBLE);
//        //加载视频资源
//        videoView.setVideoURI(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video));
//        //播放
//        videoView.start();
//        //循环播放
//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                videoView.start();
//            }
//        });
//    }

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

    //1. 需要一个平移和一个透时度的动画来执行单位移动的动画
    float tranX = 500f;
    float alphaX = 0f;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        switch (isLeftRight) {
            case 0:
                if (0 < positionOffset && positionOffset < 1) {
                    float transA = tranX + (1 - tranX) * (1 + positionOffset);
                    float alphaA = alphaX + (1 - alphaX) * (1 - positionOffset);
                    tvUnit.setTranslationX(transA);
                    tvUnit.setAlpha(alphaA);
                    if (positionOffset == 0.0f) {
                        tvUnit.setVisibility(View.GONE);
                    } else {
                        tvUnit.setVisibility(View.VISIBLE);
                    }
                } else { //右页面 //[1,0]
                    float transB = tranX + (1 - tranX) * (1 - positionOffset);
                    float alphaB = alphaX + (1 - alphaX) * (1 - positionOffset);
                    tvUnit.setTranslationX(transB);
                    tvUnit.setAlpha(alphaB);
                    if (position == 0) {
                        tvUnit.setVisibility(View.VISIBLE);
                    } else {
                        tvUnit.setVisibility(View.GONE);
                    }
                }
                break;
            case 1:
                if (0 < positionOffset && positionOffset < 1) {
                    float transA = tranX + (1 - tranX) * (1 + positionOffset);
                    float alphaA = alphaX + (1 - alphaX) * (1 - positionOffset);
                    tvUnit.setTranslationX(transA);
                    tvUnit.setAlpha(alphaA);
                    ivIcon.setImageResource(R.drawable.iv_ac_wind);
                    float transIcon = tranX - (tranX * positionOffset);
                    ivIcon.setTranslationX(transIcon);

                    if (positionOffset == 0.0f) {
                        tvUnit.setVisibility(View.GONE);
                        ivIcon.setAlpha(positionOffset);
                    } else if (positionOffset >= 0.2f) {
                        ivIcon.setAlpha(positionOffset);
                    } else {
                        tvUnit.setVisibility(View.VISIBLE);
                    }
                } else { //右页面 //[1,0]
                    float transB = tranX + (1 - tranX) * (1 - positionOffset);
                    float alphaB = alphaX + (1 - alphaX) * (1 - positionOffset);
                    tvUnit.setTranslationX(transB);
                    tvUnit.setAlpha(alphaB);
                    if (position == 0) {
                        tvUnit.setVisibility(View.VISIBLE);
                    } else {
                        tvUnit.setVisibility(View.GONE);
                    }
                }
                break;
        }
    }

    @Override
    public void onPageSelected(int position) {
        currentPos = (mFragments.size() - position) % 2;
        OffStatus(currentPos, isPressD, isPress, isTxt);
        //设置底部小点选中状态
        setCurDot(currentPos);
        adapter.notifyDataSetChanged();

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

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(EventBean event) {
        switch (event.getCode()) {
            case TIPS_MAIN_LEFT_INFO: //提示信息弹出框 总共 4 个
                DialogBean bean = (DialogBean) event.getData();
                if (isLeftRight == 0) {
                    EventBean<DialogBean> beanEventBean = new EventBean<DialogBean>(TIPS_INFO, bean);
                    EventBusUtil.sendEvent(beanEventBean);
                }
                break;
            case TIPS_MAIN_RIGHT_INFO:
                DialogBean bean2 = (DialogBean) event.getData();
                if (isLeftRight == 1) {
                    EventBean<DialogBean> beanEventBean = new EventBean<DialogBean>(TIPS_INFO, bean2);
                    EventBusUtil.sendEvent(beanEventBean);
                }
                break;
            case START_PHONE_TEST:
                int type2 = (int) event.getData();
                EventBean<Integer> eventBean2 = new EventBean<Integer>(START_PHONE, type2);
                EventBusUtil.sendEvent(eventBean2);
                break;
            case VIEWPAGER_TEXT_COLOR:
                switch (isLeftRight) {
                    case 0:
                        ADASBean adasBean = (ADASBean) event.getData();
                        if (currentPos == 1) {
                            isPressD = adasBean.isOpen2();
                        } else {
                            isPress = adasBean.isOpen();
                        }

                        break;
                    case 1:
                        ACACCean acacCean = (ACACCean) event.getData();
                        isTxt = acacCean.isOpen();
                        break;
                }
                OffStatus(currentPos, isPressD, isPress, isTxt);
                break;
        }
    }

    @OnClick({R.id.iv_img, R.id.tv_title})     //点击按钮变化
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_img:
                if (currentPos == 1) {
                    isPressD = !isPressD;
                } else {
                    isPress = !isPress;
                }
                break;
            case R.id.tv_title:
                isTxt = !isTxt;
                break;
        }
        OffStatus(currentPos, isPressD, isPress, isTxt);
    }

    private void OffStatus(int current, boolean isPressD, boolean isPress, boolean isTxt) {
        switch (isLeftRight) {
            case 0:
                ADASBean onoffBean = new ADASBean();
                if (current == 1) {
                    if (isPressD) {
                        UartTxData.PAD_FCWSt = 1;
                        onoffBean.setType(1);
                        ivImage.setImageResource(R.drawable.iv_distance_selector);
                        startAnim();
                    } else {
                        UartTxData.PAD_FCWSt = 0;
                        onoffBean.setType(0);
                        ivImage.setImageResource(R.drawable.iv_distance_normal);
                        stopAnim();
                    }
                    onoffBean.setOpen2(isPressD);
                } else {
                    if (isPress) {
                        UartTxData.PAD_ACCBtnSt = 1;
                        ivImage.setImageResource(R.drawable.iv_adas_selector);
                        startAnim();
                    } else {
                        UartTxData.PAD_ACCBtnSt = 0;
                        ivImage.setImageResource(R.drawable.iv_adas_normal);
                        stopAnim();
                    }
                    onoffBean.setOpen(isPress);
                }
                EventBean<ADASBean> eventBean = new EventBean<ADASBean>(TEXT_COLOR, onoffBean);
                EventBusUtil.sendEvent(eventBean);
                break;
            case 1:
                ACACCean acacCean = new ACACCean();
                if (isTxt) {
                    UartTxData.HCP_HVACF_ACBtnSt = 1;
                    tvTitle.setText("A/C");
                    tvTitle.setTextColor(getResources().getColor(R.color.green));
                    startAnim();
                } else {
                    UartTxData.HCP_HVACF_ACBtnSt = 0;
                    tvTitle.setText("A/C");
                    tvTitle.setTextColor(getResources().getColor(R.color.gray));
                    stopAnim();
                }
                acacCean.setOpen(isTxt);
                EventBean<ACACCean> eventBean2 = new EventBean<ACACCean>(TEXT_COLOR2, acacCean);
                EventBusUtil.sendEvent(eventBean2);
                break;
        }
    }
}





