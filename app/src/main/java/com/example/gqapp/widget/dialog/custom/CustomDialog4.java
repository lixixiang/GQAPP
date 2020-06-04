package com.example.gqapp.widget.dialog.custom;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gqapp.R;
import com.example.gqapp.utils.AnimOrientation;
import com.example.gqapp.utils.Utils;
import com.example.gqapp.widget.button.MorphingButton;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/27 1:30
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class CustomDialog4 extends AlertDialog {

    @BindView(R.id.fl_wechat_close_phone)
    MorphingButton flWechatClosePhone;
    @BindView(R.id.fl_wechat_open_phone)
    MorphingButton flWechatOpenPhone;
    @BindView(R.id.tips_phone_open_close)
    RelativeLayout tipsPhoneOpenClose;
    @BindView(R.id.iv_circle_img)
    CircleImageView ivCircleImg;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.iv_come_phone_anim)
    ImageView ivComePhoneAnim;

    Context mContext;
    //彷打电话场景
    private int[] ImageResources = {R.mipmap.wc1, R.mipmap.wc2, R.mipmap.wc3, R.mipmap.wc4, R.mipmap.wc5, R.mipmap.wc6};
    private String[] titles = {"米可", "仙道·彰", "张明德", "TOM", "Alice", "丽莎"};

    Random ran = new Random();

    public CustomDialog4(@NonNull Context context) {
        super(context, R.style.voiceDialog);
        mContext = context;
    }

    public ImageView AnimBg() {
        return ivComePhoneAnim;
    }

    public MorphingButton closePhone() {
        return flWechatClosePhone;
    }

    public MorphingButton openPhone() {
        return flWechatOpenPhone;
    }


    private void fullScreenImmersive(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            view.setSystemUiVisibility(uiOptions);
        }
    }

    AnimationSet set;
    AlphaAnimation alpha;
    ScaleAnimation scale;
    //显示来电话的动画
    public void startComeAnim(){
        set = new AnimationSet(true);
         alpha = new AlphaAnimation(1.0f, 0.0f);
        alpha.setDuration(1500);
        alpha.setRepeatCount(Animation.INFINITE);
        alpha.setInterpolator(new DecelerateInterpolator());
         scale = new ScaleAnimation(1.0f,1.5f,1.0f,1.5f,Animation.RELATIVE_TO_SELF,0.5f,
                AnimationSet.RELATIVE_TO_SELF,0.5f);
        scale.setDuration(1500);
        scale.setRepeatCount(Animation.INFINITE);
        scale.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(alpha);
        set.addAnimation(scale);
        set.setDuration(1500);
        //设置插值器
        set.setInterpolator(new DecelerateInterpolator());
        //设置动画结束之后是否保持动画的目标状态
        set.setFillAfter(false);
        //设置动画结束之后是否保持动画开始时的状态
        set.setFillBefore(true);
        set.setRepeatCount(Animation.INFINITE);
        //设置动画延时时间
        ivComePhoneAnim.startAnimation(set);

    }

    public void stopAnim(){
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                alpha.cancel();
                alpha.reset();
                ivComePhoneAnim.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                alpha.cancel();
                alpha.reset();
                ivComePhoneAnim.setVisibility(View.GONE);
            }
        });
        scale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                scale.cancel();
                scale.reset();
                ivComePhoneAnim.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                scale.cancel();
                scale.reset();
                ivComePhoneAnim.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog4);
        ButterKnife.bind(this);
        Utils.TextType(mContext, tvTips, "HYQiHei_65J.ttf");
        fullScreenImmersive(tipsPhoneOpenClose);
        AnimOrientation.rightRotation90(tipsPhoneOpenClose);
        int random = ran.nextInt(ImageResources.length);
        ivCircleImg.setImageResource(ImageResources[random]);
        startComeAnim();
    }

    public OnClickWechatCloseOpen onClickWechatCloseOpen;

    public void setOnClickWechatCloseOpen(OnClickWechatCloseOpen onClickSingleSure) {
        this.onClickWechatCloseOpen = onClickSingleSure;
    }

    @OnClick({R.id.fl_wechat_close_phone, R.id.fl_wechat_open_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_wechat_close_phone:
                if (onClickWechatCloseOpen != null) {
                    onClickWechatCloseOpen.onWechatClose(tvTips, flWechatClosePhone, ivCircleImg);
                }
                stopAnim();
                break;
            case R.id.fl_wechat_open_phone:
                if (onClickWechatCloseOpen != null) {
                    onClickWechatCloseOpen.onWechatOpen(flWechatClosePhone, flWechatOpenPhone);
                }
                stopAnim();
                break;
        }
    }

    public interface OnClickWechatCloseOpen {
        void onWechatClose(TextView textView, MorphingButton closeView, CircleImageView ivCircleImg);

        void onWechatOpen(MorphingButton closeView, MorphingButton openView);
    }

    @Override
    public void show() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        super.show();
        fullScreenImmersive(getWindow().getDecorView());
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }
}




