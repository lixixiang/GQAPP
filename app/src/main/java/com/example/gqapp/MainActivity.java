package com.example.gqapp;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.gqapp.base.BaseActivity;
import com.example.gqapp.bean.EventBean;
import com.example.gqapp.fragment.finger.FingerPrintFragment;
import com.example.gqapp.fragment.finger.FingerPrintFragment2;
import com.example.gqapp.fragment.left.MainLeftFragment;
import com.example.gqapp.fragment.right.MainRightFragment;
import com.example.gqapp.widget.CustomerScrollView;
import com.example.gqapp.widget.dialog.CustomDialog;

import butterknife.BindView;

import static com.example.gqapp.app.Constance.GONE_LEFT_COME_BACK_LINE;
import static com.example.gqapp.app.Constance.GONE_RIGHT_COME_BACK_LINE;
import static com.example.gqapp.app.Constance.LEFT_RIGHT_VALUE;
import static com.example.gqapp.app.Constance.TIPS_INFO_LEFT;
import static com.example.gqapp.utils.AnimOrientation.leftRotation90;
import static com.example.gqapp.utils.AnimOrientation.rightRotation90;

public class MainActivity extends BaseActivity {
    @BindView(R.id.left)
    FrameLayout left;
    @BindView(R.id.right)
    FrameLayout right;
    @BindView(R.id.left_bottom)
    CustomerScrollView leftBottom;
    @BindView(R.id.right_bottom)
    CustomerScrollView rightBottom;
    @BindView(R.id.fl_main_left)
    FrameLayout flMainLeft;
    @BindView(R.id.fl_main_right)
    FrameLayout flMainRight;

    private int LeftRightValue;

    // NanoPC-T4 UART4
    private String devName = "/dev/ttyS4";
    private int speed = 38400;
    private int dataBits = 8;
    private int stopBits = 1;
    private int devfd = -1;

    @Override
    protected void initView() {
        leftRotation90(flMainLeft);
        rightRotation90(flMainRight);
        comeBack(FingerPrintFragment.newInstance());
        comeBack2(FingerPrintFragment2.newInstance());

        leftBottom.setScrollViewListener(new CustomerScrollView.ScrollViewListener() {
            @Override
            public void isBottom(boolean isbottom) {
                Log.d("isbottom", isbottom + "");
                comeBack(MainLeftFragment.newInstance());
            }
        });
        rightBottom.setScrollViewListener(new CustomerScrollView.ScrollViewListener() {
            @Override
            public void isBottom(boolean isbottom) {
                comeBack2(MainRightFragment.newInstance());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    String fragmentName;

    @Override
    public void onEventBusCome(EventBean event) {
        switch (event.getCode()) {
            case GONE_LEFT_COME_BACK_LINE: //底部返回条
                fragmentName = (String) event.getData();
                leftBottom.setVisibility(View.GONE);
                break;
            case GONE_RIGHT_COME_BACK_LINE:
                fragmentName = (String) event.getData();
                rightBottom.setVisibility(View.GONE);
                break;
            case LEFT_RIGHT_VALUE:
                LeftRightValue = (int) event.getData();
                Log.d("LeftRightValue", LeftRightValue+"");
                switch (LeftRightValue) {
                    case 0:
                        leftBottom.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        rightBottom.setVisibility(View.VISIBLE);
                        break;
                }
                break;
            case TIPS_INFO_LEFT: //提示信息
                int type = (int) event.getData();
                CustomDialog customDialog = new CustomDialog(_mActivity, type);
                Display display = customDialog.getWindow().getWindowManager().getDefaultDisplay();
                WindowManager.LayoutParams lp = customDialog.getWindow().getAttributes();
                //dialog 不抢占焦点
                customDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
                customDialog.setCanceledOnTouchOutside(true);
                customDialog.getWindow().setWindowAnimations(R.style.horizontal_anim);
                switch (type) {
                    case 1:
                        customDialog.show();
                        lp.width = (int) (display.getWidth() * 0.5);
                        lp.height = display.getHeight();
                        lp.gravity = Gravity.LEFT|Gravity.FILL_HORIZONTAL;
                        customDialog.getWindow().setAttributes(lp);
                        customDialog.setOnClickSingleSure(new CustomDialog.OnClickSingleSure() {
                            @Override
                            public void onPositiveClick() {
                                customDialog.dismiss();
                            }
                        });
                        break;
                    case 2:
                        customDialog.show();
                        lp.width = (int) (display.getWidth() * 0.5);
                        lp.height = display.getHeight();
                        lp.gravity = Gravity.LEFT;
                        customDialog.getWindow().setAttributes(lp);
                        customDialog.setOnClickSureCancel(new CustomDialog.OnClickSureCancel() {
                            @Override
                            public void onPositiveSureClick() {
                                customDialog.dismiss();
                            }

                            @Override
                            public void onNegativeClick() {
                                customDialog.dismiss();
                            }
                        });
                        break;
                    case 3:
                        customDialog.show();
                        lp.width = (int) (display.getWidth() * 0.5);
                        lp.height = display.getHeight();
                        lp.gravity = Gravity.LEFT;
                        customDialog.getWindow().setAttributes(lp);
                        customDialog.setOnClickBtnCloseDoor(new CustomDialog.OnClickBtnCloseDoor() {
                            @Override
                            public void onBtnCloseDoor() {
                                customDialog.dismiss();
                            }
                        });
                        break;
                    case 4:
                        customDialog.show();
                        lp.width = (int) (display.getWidth() * 0.5);
                        lp.height = display.getHeight();
                        lp.gravity = Gravity.RIGHT;
                        customDialog.getWindow().setAttributes(lp);
                        customDialog.setOnClickWechatCloseOpen(new CustomDialog.OnClickWechatCloseOpen() {
                            @Override
                            public void onWechatClose() {
                                customDialog.dismiss();
                            }

                            @Override
                            public void onWechatOpen(View closeView, View openView) {
                                AnimatorSet animatorSet = new AnimatorSet();
                                ObjectAnimator animAlpha = ObjectAnimator.ofFloat(openView, "alpha", 1f, 0f);
                                ObjectAnimator animTranslationX = ObjectAnimator.ofFloat(closeView, "translationX", 0f, lp.width / 4f);
                                animatorSet.setDuration(500);
                                animatorSet.setInterpolator(new DecelerateInterpolator());
                                animatorSet.playTogether(animAlpha, animTranslationX);
                                animatorSet.start();
                                //    openView.setVisibility(View.GONE);
                                animatorSet.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        openView.setVisibility(View.INVISIBLE);
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                });
                            }
                        });
                        break;
                    default:
                        break;
                }
                break;
        }
    }
}
