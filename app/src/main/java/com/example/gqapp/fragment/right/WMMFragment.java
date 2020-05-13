package com.example.gqapp.fragment.right;

import android.app.AlertDialog;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.example.gqapp.R;
import com.example.gqapp.base.BaseFragment;
import com.example.gqapp.bean.EventBean;
import com.example.gqapp.fragment.adapter.HorizontalPagerAdapter;
import com.example.gqapp.utils.AnimOrientation;
import com.example.gqapp.utils.EventBusUtil;
import com.example.gqapp.widget.horizontal.HorizontalInfiniteCycleViewPager;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.gqapp.app.Constance.LEFT_RIGHT_VALUE;
import static com.example.gqapp.app.Constance.TIPS_INFO_LEFT;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/24 6:39
 * @Author: 李熙祥
 * @Description: java类作用描述 多媒体碎片
 */
public class WMMFragment extends BaseFragment {
    @BindView(R.id.circleViewPager)
    HorizontalInfiniteCycleViewPager ImageViewPager;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.iv_bluetooth)
    ImageView ivBluetooth;
    @BindView(R.id.iv_playing)
    ImageView ivPlaying;
    @BindView(R.id.iv_volume)
    ImageView ivVolume;

    private boolean isChangeStatus = false, isPaying = false;
    private int currentVolume, currentVolume2;
    private String[] strVMMTitles = {"We Will Rock You Queen", "Something Change Queen", "We Will Rock You Queen"};
    private int isLeftRight;

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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBean<Integer> eventBean = new EventBean<Integer>(TIPS_INFO_LEFT, 4);
                EventBusUtil.sendEvent(eventBean);
            }
        },5000);

        ImageViewPager.setAdapter(new HorizontalPagerAdapter(_mActivity));
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
                Log.d("onPageSelected", strVMMTitles[ImageViewPager.getRealItem()]);
                tvTitle.setText(strVMMTitles[ImageViewPager.getRealItem()]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.iv_bluetooth, R.id.iv_playing, R.id.iv_volume})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bluetooth:
                if (!isChangeStatus) {
                    ivBluetooth.setImageResource(R.drawable.iv_signal);
                } else {
                    ivBluetooth.setImageResource(R.drawable.iv_bluetooth);
                }
                isChangeStatus = !isChangeStatus;
                break;
            case R.id.iv_playing:
                if (!isPaying) {
                    ivPlaying.setImageResource(R.drawable.iv_pause);
                } else {
                    ivPlaying.setImageResource(R.drawable.iv_playing);
                }
                isPaying = !isPaying;
                break;
            case R.id.iv_volume:
                final AlertDialog dlg = new AlertDialog.Builder(_mActivity, R.style.voiceDialog).create();
                dlg.show();
                dlg.setContentView(R.layout.dialog_voice);
                LinearLayout ll = dlg.findViewById(R.id.ll_dialog_vmm);
                SeekBar sbHeadrest = dlg.findViewById(R.id.sb_headrest);
                SeekBar sbSurround = dlg.findViewById(R.id.sb_surround);
                AnimOrientation.rightRotation90(ll);
                sbHeadrest.setProgress(currentVolume);
                sbSurround.setProgress(currentVolume2);
                sbSurround.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        currentVolume2 = progress;
                        sbSurround.setProgress(currentVolume2);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                sbHeadrest.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        currentVolume = progress;
                        sbHeadrest.setProgress(currentVolume);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dlg.dismiss();
                    }
                });
                //     window.setWindowAnimations(R.style.mystyle);//设置从屏幕下方弹框动画
                //设置弹框的高为屏幕的一半宽是屏幕的宽
                WindowManager windowManager = _mActivity.getWindowManager();
                Display display = windowManager.getDefaultDisplay();

                WindowManager.LayoutParams lp = dlg.getWindow().getAttributes();
                lp.width = (int) (display.getWidth() * 0.5); //设置宽度
                lp.height = (int) (display.getHeight()); //设置宽度
                lp.gravity = Gravity.END;
                dlg.getWindow().setWindowAnimations(R.style.horizontal_anim);
                dlg.getWindow().setAttributes(lp);
                break;
        }
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
