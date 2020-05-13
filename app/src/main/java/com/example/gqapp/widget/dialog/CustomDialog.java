package com.example.gqapp.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gqapp.R;
import com.example.gqapp.utils.AnimOrientation;
import com.example.gqapp.widget.SemicircleImageView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/27 1:30
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class CustomDialog extends AlertDialog {
    @BindView(R.id.re_dialog)
    RelativeLayout reDialog;
    @BindView(R.id.tips_sure)
    LinearLayout tipsSure;
    @BindView(R.id.tips_sure_cancel)
    LinearLayout tipsSureCancel;
    @BindView(R.id.iv_car_open_door)
    ImageView ivCarOpenDoor;
    @BindView(R.id.tips_close_door)
    LinearLayout tipsCloseDoor;
    @BindView(R.id.iv_wechat_pic)
    SemicircleImageView ivWechatPic;
    @BindView(R.id.tips_phone_open_close)
    LinearLayout tipsPhoneOpenClose;
    @BindView(R.id.tv_wechat_user_name)
    TextView tvWechatUserName;
    @BindView(R.id.fl_wechat_close_phone)
    ImageButton flWechatClosePhone;
    @BindView(R.id.fl_wechat_open_phone)
    ImageButton flWechatOpenPhone;

    private int type; //出现哪个dialog
    private Context mContext;

    //彷打电话场景
    private int[] ImageResources = {R.mipmap.wc1, R.mipmap.wc2, R.mipmap.wc3, R.mipmap.wc4, R.mipmap.wc5, R.mipmap.wc6};
    private String[] titles = {"米可", "仙道·彰", "张明德", "TOM", "Alice", "丽莎"};

    Random ran = new Random();

    public OnClickSingleSure onClickSingleSure;
    public OnClickSureCancel onClickSureCancel;
    public OnClickBtnCloseDoor onClickBtnCloseDoor;
    public OnClickWechatCloseOpen onClickWechatCloseOpen;

    public CustomDialog setOnClickSingleSure(OnClickSingleSure onClickSingleSure) {
        this.onClickSingleSure = onClickSingleSure;
        return this;
    }

    public CustomDialog setOnClickSureCancel(OnClickSureCancel onClickSureCancel) {
        this.onClickSureCancel = onClickSureCancel;
        return this;
    }

    public CustomDialog setOnClickBtnCloseDoor(OnClickBtnCloseDoor onClickBtnCloseDoor){
        this.onClickBtnCloseDoor = onClickBtnCloseDoor;
        return this;
    }

    public CustomDialog setOnClickWechatCloseOpen(OnClickWechatCloseOpen onClickWechatCloseOpen){
        this.onClickWechatCloseOpen = onClickWechatCloseOpen;
        return this;
    }

    public CustomDialog(@NonNull Context context, int type) {
        super(context, R.style.voiceDialog);
        this.type = type;
        mContext =  context;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog);
        ButterKnife.bind(this);
        fullScreenImmersive(reDialog);
        int random = ran.nextInt(ImageResources.length);
        ivWechatPic.setImageResource(ImageResources[random]);
        tvWechatUserName.setText(titles[random]);
        if (type == 4)
            AnimOrientation.rightRotation90(reDialog);
        else
            AnimOrientation.leftRotation90(reDialog);

        switch (type) {
            case 1:
                tipsSure.setVisibility(View.VISIBLE);
                break;
            case 2:
                tipsSureCancel.setVisibility(View.VISIBLE);
                break;
            case 3:
                tipsCloseDoor.setVisibility(View.VISIBLE);
                break;
            case 4:
                tipsPhoneOpenClose.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.tips_sure,R.id.tips_sure_cancel,R.id.btn_single_sure, R.id.btn_cancel, R.id.btn_sure, R.id.btn_close_door,R.id.fl_wechat_close_phone, R.id.fl_wechat_open_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tips_sure:
            case R.id.tips_sure_cancel:
            case R.id.tips_close_door:
            case R.id.tips_phone_open_close:
                dismiss();
                break;
            case R.id.btn_single_sure:
                if (onClickSingleSure != null) {
                    onClickSingleSure.onPositiveClick();
                }
                break;
            case R.id.btn_cancel:
                if (onClickSureCancel != null) {
                    onClickSureCancel.onNegativeClick();
                }
                break;
            case R.id.btn_sure:
                if (onClickSureCancel != null) {
                    onClickSureCancel.onPositiveSureClick();
                }
                break;
            case R.id.btn_close_door:
                if (onClickBtnCloseDoor != null) {
                    onClickBtnCloseDoor.onBtnCloseDoor();
                }
                break;
            case R.id.fl_wechat_close_phone:
                if (onClickWechatCloseOpen != null) {
                    onClickWechatCloseOpen.onWechatClose();
                }
                break;
            case R.id.fl_wechat_open_phone:
                if (onClickWechatCloseOpen != null) {
                    onClickWechatCloseOpen.onWechatOpen(flWechatClosePhone,flWechatOpenPhone);
                }
                break;
        }
    }

    public interface OnClickSingleSure {  //第一个type 只有一个按钮
        void onPositiveClick();
    }

    public interface OnClickSureCancel { //第二个type 两个按钮
        void onPositiveSureClick();
        void onNegativeClick();
    }

    public interface OnClickBtnCloseDoor{ //第三个type 一个按钮
        void onBtnCloseDoor();
    }

    public interface OnClickWechatCloseOpen{
        void onWechatClose();
        void onWechatOpen(View closeView,View openView);
    }

    @Override
    public void show() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        super.show();
        fullScreenImmersive(getWindow().getDecorView());
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }
}




