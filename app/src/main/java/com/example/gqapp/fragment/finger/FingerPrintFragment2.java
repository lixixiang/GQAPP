package com.example.gqapp.fragment.finger;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.example.gqapp.R;
import com.example.gqapp.base.BaseFragment;
import com.example.gqapp.bean.EventBean;
import com.example.gqapp.fragment.left.MainLeftFragment;
import com.example.gqapp.fragment.right.MainRightFragment;
import com.example.gqapp.utils.EventBusUtil;
import com.example.gqapp.widget.ObjectRippleView;

import butterknife.BindView;
import me.jessyan.autosize.internal.CustomAdapt;

import static com.example.gqapp.app.Constance.FINGER_CLICK;
import static com.example.gqapp.app.Constance.FINGER_CLICK2;

/**
 * created by lxx at 2019/9/12 15:28
 * 描述:左右要分开否则会报错
 */
public class FingerPrintFragment2 extends BaseFragment implements CustomAdapt {
    @BindView(R.id.iv_scan_finger)
    ImageView ivScanFinger;
    @BindView(R.id.iv_finger)
    ImageView ivFinger;
    @BindView(R.id.rippleview)
    ObjectRippleView rippleview;


    public static FingerPrintFragment2 newInstance() {
        FingerPrintFragment2 fragment = new FingerPrintFragment2();
        return fragment;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_finger_print;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView() {
        //      vibrator = (Vibrator) _mActivity.getSystemService(_mActivity.VIBRATOR_SERVICE);
        ivFinger.setColorFilter(getResources().getColor(R.color.colorTranslateHalf));
        final AnimatorSet set = new AnimatorSet();
        ivFinger.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        EventBean<Boolean> eventBean = new EventBean<Boolean>(FINGER_CLICK, false);
                        EventBusUtil.sendEvent(eventBean);
                        ivFinger.setVisibility(View.VISIBLE);
                        fingerDownAnim();
                        ivScanFinger.setVisibility(View.VISIBLE);
                        ObjectAnimator animator = ObjectAnimator.ofFloat(ivScanFinger, "translationY", 200f, -200f);
                        ObjectAnimator animator2 = ObjectAnimator.ofFloat(ivScanFinger, "translationY", -200f, 200f);

                        set.playSequentially(animator, animator2);
                        set.setInterpolator(new AccelerateDecelerateInterpolator());
                        set.setDuration(1000);
                        set.start();

                        set.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                ivFinger.setEnabled(true);
                                ivScanFinger.setVisibility(View.GONE);
                                ivFinger.setImageResource(R.mipmap.finger_grey);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startFragmentLeft(MainLeftFragment.newInstance());
                                        startFragmentRight(MainRightFragment.newInstance());
                                    }
                                }, 1000);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        break;
                    case MotionEvent.ACTION_UP:
                        rippleview.stop();
                        rippleview.setVisibility(View.GONE);
                        if (set.isStarted()) {
                            ivFinger.setEnabled(false);
                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(EventBean event) {
        switch (event.getCode()) {
            case FINGER_CLICK2:
                boolean isClick = (boolean) event.getData();
                ivFinger.setEnabled(isClick);
                ivFinger.setVisibility(View.GONE);
                break;
        }
    }

    private void fingerDownAnim() {//手指按下时部分透明
        ivFinger.setImageResource(R.mipmap.finger_green);
        rippleview.setVisibility(View.VISIBLE);
        rippleview.setMaxRadius(800f);
        rippleview.setMaxRadiusRate(1000f);
        rippleview.setDuration(5000);
        rippleview.setStyle(Paint.Style.STROKE);
        rippleview.setColor(getResources().getColor(R.color.white_1));
        rippleview.setInterpolator(new DecelerateInterpolator());
        rippleview.start();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        return 1080;
    }
}

