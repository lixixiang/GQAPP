package com.example.gqapp;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.gqapp.app.MyApplication;
import com.example.gqapp.base.BaseActivity;
import com.example.gqapp.bean.DialogBean;
import com.example.gqapp.bean.EventBean;
import com.example.gqapp.fragment.finger.FingerPrintFragment;
import com.example.gqapp.fragment.finger.FingerPrintFragment2;
import com.example.gqapp.fragment.left.MainLeftFragment;
import com.example.gqapp.fragment.right.MainRightFragment;
import com.example.gqapp.utils.AnimationUtil;
import com.example.gqapp.utils.EventBusUtil;
import com.example.gqapp.utils.Utils;
import com.example.gqapp.utils.uart.UartComUtil;
import com.example.gqapp.utils.uart.UartRxData;
import com.example.gqapp.utils.uart.UartTxData;
import com.example.gqapp.widget.CustomerScrollView;
import com.example.gqapp.widget.button.MorphingButton;
import com.example.gqapp.widget.dialog.custom.CustomDialog1;
import com.example.gqapp.widget.dialog.custom.CustomDialog2;
import com.example.gqapp.widget.dialog.custom.CustomDialog3;
import com.example.gqapp.widget.dialog.custom.CustomDialog4;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.gqapp.app.Constance.GONE_LEFT_COME_BACK_LINE;
import static com.example.gqapp.app.Constance.GONE_RIGHT_COME_BACK_LINE;
import static com.example.gqapp.app.Constance.LEFT_RIGHT_VALUE;
import static com.example.gqapp.app.Constance.START_PHONE;
import static com.example.gqapp.app.Constance.START_PHONE_TEST;
import static com.example.gqapp.app.Constance.TIPS_INFO;
import static com.example.gqapp.app.Constance.TIPS_MAIN_LEFT_INFO;
import static com.example.gqapp.app.Constance.TIPS_MAIN_RIGHT_INFO;
import static com.example.gqapp.utils.AnimOrientation.leftRotation90;
import static com.example.gqapp.utils.AnimOrientation.rightRotation90;

public class MainActivity extends BaseActivity {
    @BindView(R.id.fl_background)
    FrameLayout llMainBg;
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


    private int LeftRightValue; //判断是左还是右的界面

    byte aliveCount;
    byte b0 = (byte) 0x55, b1 = 0x00, b2 = 0x00, b3 = 0x00, b4 = 0x00, b5 = 0x00, b6 = 0x00, b8 = (byte) 0xFF;
    byte byteCount1 = 0x00, byteCount2 = 0x00;//byte1 传过来的数据
    byte[] OutData = {b0, b1, b2, b3, b4, b5, b6, aliveCount, b8};
    //左边 PAD UART 送信
    byte LeftUART = 0x02;
    //右边 PAD UART 送信
    byte RightUART = 0x01;

    //字符串值 本来是 byteCount1 和 byteCount2
    String strCount1, strCount2;

    public Timer timer, timer2;
    UartComUtil uartUtil;
    UartTxData uartTxData;
    UartRxData uartRxData;


    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            uartTxData.UartSendFrame();
            byte[] buf = new byte[14];
            uartRxData.ReturnResult(buf);
            switch (buf[0]) {
                case 0x1C: //弹出仪表警报弹窗
                    DialogBean bean5 = new DialogBean(1, 0x1C);
                    EventBean<DialogBean> eventBean5 = new EventBean<DialogBean>(TIPS_MAIN_LEFT_INFO, bean5);
                    EventBusUtil.sendEvent(eventBean5);
                    Log.d("uartTxData", "弹出仪表警报弹窗");
                    break;
                case 0x1D: //退出仪表报警弹窗
                    DialogBean bean6 = new DialogBean(1, 0x1D);
                    EventBean<DialogBean> eventBean6 = new EventBean<DialogBean>(TIPS_MAIN_LEFT_INFO, bean6);
                    EventBusUtil.sendEvent(eventBean6);
                    Log.d("uartTxData", "退出仪表报警弹窗");
                    break;
                case 0x1E: //弹出推送选择弹窗
                    DialogBean bean7 = new DialogBean(2, 0x1E);
                    EventBean<DialogBean> eventBean7 = new EventBean<DialogBean>(TIPS_MAIN_LEFT_INFO, bean7);
                    EventBusUtil.sendEvent(eventBean7);
                    Log.d("uartTxData", "弹出推送选择弹窗");
                    break;
                case 0x1F: //退出推送选择弹窗
                    DialogBean bean8 = new DialogBean(2, 0x1F);
                    EventBean<DialogBean> eventBean8 = new EventBean<DialogBean>(TIPS_MAIN_LEFT_INFO, bean8);
                    EventBusUtil.sendEvent(eventBean8);
                    Log.d("uartTxData", "退出推送选择弹窗");
                    break;
                case 0x1A: //弹出关门弹窗
                    DialogBean bean3 = new DialogBean(3, 0x1A);
                    EventBean<DialogBean> eventBean3 = new EventBean<DialogBean>(TIPS_MAIN_LEFT_INFO, bean3);
                    EventBusUtil.sendEvent(eventBean3);
                    Log.d("uartTxData", "弹出关门弹窗");
                    break;
                case 0x1B: //监听退出关门弹窗按钮
                    DialogBean bean4 = new DialogBean(3, 0x1B);
                    EventBean<DialogBean> eventBean4 = new EventBean<DialogBean>(TIPS_MAIN_LEFT_INFO, bean4);
                    EventBusUtil.sendEvent(eventBean4);
                    Log.d("uartTxData", "监听退出关门弹窗按钮");
                    break;
                case 0x18: //弹出电话窗口
                    DialogBean bean = new DialogBean(4, 0x18);
                    EventBean<DialogBean> eventBean = new EventBean<DialogBean>(TIPS_MAIN_RIGHT_INFO, bean);
                    EventBusUtil.sendEvent(eventBean);
                    Log.d("uartTxData", "弹出电话窗口");
                    break;
                case 0x19: //监听退出通话界面，显示来电之前的界面
                    DialogBean bean2 = new DialogBean(4, 0x19);
                    EventBean<DialogBean> eventBean2 = new EventBean<DialogBean>(TIPS_MAIN_RIGHT_INFO, bean2);
                    EventBusUtil.sendEvent(eventBean2);
                    Log.d("uartTxData", "监听退出通话界面，显示来电之前的界面");
                    break;
                case 0x11: //启动开机，播放开机动画；在启动中判断
                    EventBean<Integer> eventBean9 = new EventBean<Integer>(START_PHONE_TEST, 0x11);
                    EventBusUtil.sendEvent(eventBean9);
                    break;
                case 0x12: //启动开机中立刻黑屏 ；在启动中判断
                    EventBean<Integer> eventBean10 = new EventBean<Integer>(START_PHONE_TEST, 0x12);
                    EventBusUtil.sendEvent(eventBean10);
                    break;
                case 0x13: //画面逐渐变暗，1s后完全变黑；在关机中判断
                    EventBean<Integer> eventBean11 = new EventBean<Integer>(START_PHONE_TEST, 0x13);
                    EventBusUtil.sendEvent(eventBean11);
                    break;
                case 0x14:
                    EventBean<Integer> eventBean12 = new EventBean<Integer>(START_PHONE_TEST, 0x14);
                    EventBusUtil.sendEvent(eventBean12);
                    break;
                case 0x15:
                    EventBean<Integer> eventBean13 = new EventBean<Integer>(START_PHONE_TEST, 0x15);
                    EventBusUtil.sendEvent(eventBean13);
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        leftRotation90(flMainLeft);
        rightRotation90(flMainRight);
        uartUtil = new UartComUtil();
        uartTxData = new UartTxData();
        uartRxData = new UartRxData();

        comeBack(FingerPrintFragment.newInstance());
        comeBack2(FingerPrintFragment2.newInstance());
        TimerMethod();
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

    private void TimerMethod() {
        timer = new Timer();
        timer.schedule(timerTask, 0, 10);
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
                Log.d("LeftRightValue", LeftRightValue + "");
                switch (LeftRightValue) {
                    case 0:
                        leftBottom.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        rightBottom.setVisibility(View.VISIBLE);
                        break;
                }
                break;
            case TIPS_INFO: //提示信息
                DialogBean dialogBean = (DialogBean) event.getData();
                switch (dialogBean.getType()) {
                    case 1:
                        CustomDialog1 dialog1 = new CustomDialog1(_mActivity);
                        Display display1 = dialog1.getWindow().getWindowManager().getDefaultDisplay();
                        WindowManager.LayoutParams lp1 = dialog1.getWindow().getAttributes();
                        //dialog 不抢占焦点
                        dialog1.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
                        dialog1.setCanceledOnTouchOutside(false);
                        dialog1.getWindow().setWindowAnimations(R.style.horizontal_anim);
                        dialog1.show();
                        if (dialogBean.getExitPhone() == 0x1D) {
                            dialog1.dismiss();
                        }

                        lp1.width = (int) (display1.getWidth() * 0.5);
                        lp1.height = display1.getHeight();
                        lp1.gravity = Gravity.LEFT;
                        dialog1.getWindow().setAttributes(lp1);

                        dialog1.setOnClickSingleSure(new CustomDialog1.OnClickSingleSure() {
                            @Override
                            public void onPositiveClick() {
                                UartTxData.PAD_ICM_PopupConBtnSt = 1;
                                dialog1.dismiss();
                            }
                        });
                        UartTxData.PAD_ICM_PopupConBtnSt = 0;
                        break;
                    case 2:
                        CustomDialog2 dialog2 = new CustomDialog2(_mActivity);
                        Display display2 = dialog2.getWindow().getWindowManager().getDefaultDisplay();
                        WindowManager.LayoutParams lp2 = dialog2.getWindow().getAttributes();
                        //dialog 不抢占焦点
                        dialog2.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
                        dialog2.setCanceledOnTouchOutside(false);
                        dialog2.getWindow().setWindowAnimations(R.style.horizontal_anim);
                        dialog2.show();
                        if (dialogBean.getExitPhone() == 0x1F) {
                            dialog2.dismiss();
                        }

                        lp2.width = (int) (display2.getWidth() * 0.5);
                        lp2.height = display2.getHeight();
                        lp2.gravity = Gravity.LEFT;
                        dialog2.getWindow().setAttributes(lp2);
                        UartTxData.PAD_ICM_PromptConfirmBtnSt = 0;
                        UartTxData.PAD_ICM_PromptCancelBtnSt = 0;
                        dialog2.setOnClickSureCancel(new CustomDialog2.OnClickSureCancel() {
                            @Override
                            public void onPositiveSureClick() {
                                UartTxData.PAD_ICM_PromptConfirmBtnSt = 1;
                                dialog2.dismiss();
                            }

                            @Override
                            public void onNegativeClick() {
                                UartTxData.PAD_ICM_PromptCancelBtnSt = 1;
                                dialog2.dismiss();
                            }
                        });
                        UartTxData.PAD_ICM_PromptConfirmBtnSt = 0;
                        UartTxData.PAD_ICM_PromptCancelBtnSt = 0;
                        break;
                    case 3:
                        CustomDialog3 dialog3 = new CustomDialog3(_mActivity);
                        Display display3 = dialog3.getWindow().getWindowManager().getDefaultDisplay();
                        WindowManager.LayoutParams lp3 = dialog3.getWindow().getAttributes();
                        //dialog 不抢占焦点
                        dialog3.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
                        dialog3.setCanceledOnTouchOutside(false);
                        dialog3.getWindow().setWindowAnimations(R.style.horizontal_anim);
                        dialog3.show();
                        if (dialogBean.getExitPhone() == 0x1B) {
                            dialog3.dismiss();
                        }

                        lp3.width = (int) (display3.getWidth() * 0.5);
                        lp3.height = display3.getHeight();
                        lp3.gravity = Gravity.LEFT;
                        dialog3.getWindow().setAttributes(lp3);
                        UartTxData.PAD_FLPDUCloseSt = 0;

                        dialog3.setOnClickBtnCloseDoor(new CustomDialog3.OnClickBtnCloseDoor() {
                            @Override
                            public void onBtnCloseDoor() {
                                UartTxData.PAD_FLPDUCloseSt = 1;
                                dialog3.dismiss();
                            }
                        });
                        UartTxData.PAD_FLPDUCloseSt = 0;
                        break;
                    case 4:
                        CustomDialog4 dialog4 = new CustomDialog4(_mActivity);
                        Display display4 = dialog4.getWindow().getWindowManager().getDefaultDisplay();
                        WindowManager.LayoutParams lp4 = dialog4.getWindow().getAttributes();
                        //dialog 不抢占焦点
                        dialog4.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
                        dialog4.getWindow().setWindowAnimations(R.style.horizontal_anim);
                        dialog4.setCanceledOnTouchOutside(false);
                        dialog4.show();

                        if (dialogBean.getExitPhone() == 0x19) {
                            dialog4.cancel();
                        }

                        morphToSquare(dialog4.closePhone(), 0, R.color.red2, R.color.red2, R.drawable.close_phone);
                        morphToSquare(dialog4.openPhone(), 0, R.color.green, R.color.green, R.drawable.open_phone);

                        lp4.width = (int) (display4.getWidth() * 0.5);
                        lp4.height = display4.getHeight();
                        lp4.gravity = Gravity.RIGHT;
                        dialog4.getWindow().setAttributes(lp4);
                        UartTxData.PAD_AnswertheBulephoneBtSt = 0;
                        UartTxData.PAD_RefusetheBulephoneBtSt = 0;

                        dialog4.setOnClickWechatCloseOpen(new CustomDialog4.OnClickWechatCloseOpen() {
                            @Override
                            public void onWechatClose(TextView textView, MorphingButton closeView, CircleImageView ivCircleImg) {
                                textView.setVisibility(View.VISIBLE);
                                closeView.setEnabled(false);
                                ivCircleImg.setColorFilter(0xC0000000);
                                dialog4.openPhone().setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_btn_grey_alpha));
                                closeView.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_btn_grey_alpha));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog4.cancel();
                                    }
                                }, 3000);
                            }

                            @Override
                            public void onWechatOpen(MorphingButton closeView, MorphingButton openView) {
                                AnimatorSet animatorSet = new AnimatorSet();
                                ObjectAnimator animAlpha = ObjectAnimator.ofFloat(openView, "alpha", 1f, 0f);
                                ObjectAnimator animTranslationX = ObjectAnimator.ofFloat(closeView, "translationX", 0f, lp4.width / 6f);
                                animatorSet.setDuration(500);
                                animatorSet.setInterpolator(new DecelerateInterpolator());
                                animatorSet.playTogether(animAlpha, animTranslationX);
                                animatorSet.start();

                                UartTxData.PAD_AnswertheBulephoneBtSt = 1;
                                Utils.morphToBehindSquare(_mActivity, closeView, Utils.integer(_mActivity, R.integer.mb_animation), R.color.red2, R.color.red2);
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
                        UartTxData.PAD_AnswertheBulephoneBtSt = 0;
                        UartTxData.PAD_RefusetheBulephoneBtSt = 0;
                        break;
                    default:
                        break;
                }
                break;
            case START_PHONE:
                int type2 = (int) event.getData();
                Log.d("START_PHONE", "START_PHONE====" + type2 + "");
                switch (type2) {
                    case 0x11: //启动开机，播放开机动画；在启动中判断
                        startActivity(new Intent(MainActivity.this, StartPhoneAnimActivity.class));
                        overridePendingTransition(R.anim.default_dialog_in,R.anim.default_dialog_out);
                        break;
                    case 0x12: //启动开机中立刻黑屏 ；在启动中判断
                        llMainBg.setVisibility(View.VISIBLE);
                        break;
                    case 0x13: //画面逐渐变暗，1s后完全变黑；在关机中判断
                    case 0x14:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AnimationUtil.startAlphaAnima(llMainBg, 0, 1, 1000);
                                llMainBg.setVisibility(View.VISIBLE);
                            }
                        }, 1000);
                        break;
                    case 0x15: // 屏幕逐渐点亮，1s后完全点亮；
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AnimationUtil.startAlphaAnima(llMainBg, 1, 0, 1000);
                                llMainBg.setVisibility(View.GONE);
                            }
                        }, 1000);
                        break;
                }
                break;
        }
    }

    private void morphToSquare(final MorphingButton btnMorph, int duration, int color, int colorPressed, int icon) {
        MorphingButton.Params square = MorphingButton.Params.create()
                .duration(duration)
                .cornerRadius(Utils.dimen(_mActivity, R.dimen.mb_height_56))
                .width(Utils.dimen(_mActivity, R.dimen.mb_height_56))
                .height(Utils.dimen(_mActivity, R.dimen.mb_height_56))
                .color(Utils.color(_mActivity, color))
                .colorPressed(Utils.color(_mActivity, colorPressed))
                .icon(icon);
        btnMorph.morph(square);
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        MyApplication.clear(_mActivity);
        timer.cancel();
        timer2.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
