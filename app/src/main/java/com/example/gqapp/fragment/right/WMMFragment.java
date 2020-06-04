package com.example.gqapp.fragment.right;

import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.gqapp.R;
import com.example.gqapp.app.MyApplication;
import com.example.gqapp.base.BaseFragment;
import com.example.gqapp.bean.DialogBean;
import com.example.gqapp.bean.EventBean;
import com.example.gqapp.utils.EventBusUtil;
import com.example.gqapp.utils.Utils;
import com.example.gqapp.utils.uart.UartRxData;
import com.example.gqapp.utils.uart.UartTxData;
import com.example.gqapp.widget.SemicircleImageView;
import com.example.gqapp.widget.dialog.CustomPlayDialog;
import com.example.gqapp.widget.transformer.RotateTransformer;

import butterknife.BindView;

import static com.example.gqapp.app.Constance.CHANGE_MEDIAL;
import static com.example.gqapp.app.Constance.LEFT_RIGHT_VALUE;
import static com.example.gqapp.app.Constance.PLAY_ON_OFF;
import static com.example.gqapp.app.Constance.START_PHONE;
import static com.example.gqapp.app.Constance.START_PHONE_TEST;
import static com.example.gqapp.app.Constance.TIPS_INFO;
import static com.example.gqapp.app.Constance.TIPS_MAIN_RIGHT_INFO;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/24 6:39
 * @Author: 李熙祥
 * @Description: java类作用描述 多媒体碎片
 */
public class WMMFragment extends BaseFragment {
    @BindView(R.id.circleViewPager)
    ViewPager ImageViewPager;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvDescription;
//    @BindView(R.id.iv_bluetooth)
//    ImageView ivBluetooth;
//    @BindView(R.id.iv_playing)
//    ImageView ivPlaying;
//    @BindView(R.id.iv_volume)
//    ImageView ivVolume;

    private boolean isChangeStatus = false, isPaying = false;


    private String[] strVMMTitles = {"We Will Rock You Queen1", "Something Change Queen2", "We Will Rock You Queen3", "Something Change Queen4", "We Will Rock You Queen5"};
    private String[] titles = {"米可", "张明德", "TOM", "Alice", "丽莎"};
    private int[] images = {R.drawable.iv_wmm_pic1, R.drawable.iv_wmm_pic2, R.drawable.iv_wmm_pic1, R.drawable.iv_wmm_pic2, R.drawable.iv_wmm_pic1};

    private int isLeftRight;
    private int currentPlay = 1;
    boolean isPlay = false;

    public static WMMFragment newInstance(int isLeftRight) {
        WMMFragment fragment = new WMMFragment();
        fragment.isLeftRight = isLeftRight;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_wmm;
    }

    @Override
    protected void initView() {
        ImageViewPager.setOffscreenPageLimit(3);
        ImageViewPager.setPageTransformer(true, new RotateTransformer());
        ImageViewPager.setCurrentItem(2);
        ImageViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return images.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = LayoutInflater.from(_mActivity).inflate(R.layout.image_item, container, false);
                SemicircleImageView imageView = view.findViewById(R.id.img);
                imageView.setImageResource(images[position]);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomPlayDialog dialog = new CustomPlayDialog(_mActivity, strVMMTitles[position]);
                        Display display = dialog.getWindow().getWindowManager().getDefaultDisplay();
                        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                        //dialog 不抢占焦点
                        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

                        dialog.getWindow().setWindowAnimations(R.style.horizontal_anim);
                        dialog.show();
                        lp.width = (int) (display.getWidth() * 0.5);
                        lp.height = display.getHeight();
                        lp.gravity = Gravity.RIGHT;
                        dialog.getWindow().setAttributes(lp);
                        dialog.setOnClickDialogCallBack(new CustomPlayDialog.DialogCallBack() {
                            @Override
                            public void backCome() {
                                startFragmentRight(MainRightFragment.newInstance());
                                dialog.dismiss();
                            }
                        });
                    }
                });

                imageView.setTag(images[position]);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPlay  = UartRxData.ACU_CurrentSourceNo;
            }
        }, 1000);

        isPaying = (boolean) MyApplication.get(_mActivity, PLAY_ON_OFF, false);
        if (currentPlay != -1) {
            ImageViewPager.setCurrentItem(currentPlay);
            UartTxData.PAD_SourceSetting = currentPlay;
        }

        ImageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("positionOffset", positionOffset + "");
                if (positionOffset > 0 && positionOffset < 1) {
                    tvTitle.setAlpha(0.5f);
                    tvDescription.setAlpha(0.5f);
                } else {
                    tvTitle.setAlpha(1.0f);
                    tvDescription.setAlpha(1.0f);
                }
            }

            @Override
            public void onPageSelected(int position) {
                currentPlay = position;
                MyApplication.put(_mActivity, CHANGE_MEDIAL, position);
                tvTitle.setText(strVMMTitles[position]);
                UartTxData.PAD_SourceSetting = currentPlay;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
            case START_PHONE_TEST:
                int type2 = (int) event.getData();
                Log.d("START_PHONE", "START_PHONE+MAIN_LEFT====" + type2 + "");
                EventBean<Integer> eventBean2 = new EventBean<Integer>(START_PHONE, type2);
                EventBusUtil.sendEvent(eventBean2);
                break;
            case TIPS_MAIN_RIGHT_INFO: //提示信息弹出框 总共 4 个
                DialogBean bean = (DialogBean) event.getData();
                EventBean<DialogBean> beanEventBean = new EventBean<DialogBean>(TIPS_INFO, bean);
                EventBusUtil.sendEvent(beanEventBean);
                break;
        }
    }
}
