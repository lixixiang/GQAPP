package com.example.gqapp;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gqapp.base.BaseActivity;
import com.example.gqapp.utils.AnimationUtil;
import com.example.gqapp.utils.FrameAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartPhoneAnimActivity extends BaseActivity {

    @BindView(R.id.img_anim)
    ImageView imgAnim;

    @Override
    protected void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
         //       AnimationUtil.startAlphaAnima(imgAnim, 1, 0, 1000);
          //      imgAnim.setVisibility(View.VISIBLE);
                FrameAnimation frameAnimation = new FrameAnimation(imgAnim, getRes(), 10, false);
                frameAnimation.setAnimationListener(new FrameAnimation.AnimationListener() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(StartPhoneAnimActivity.this, MainActivity.class));
                                overridePendingTransition(R.anim.default_dialog_in,R.anim.default_dialog_out);
                   //             imgAnim.setVisibility(View.GONE);
                            }
                        }, 1000);
                    }

                    @Override
                    public void onAnimationRepeat() {

                    }
                });
            }
        }, 1000);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start_phone_anim;
    }

    private int[] getRes() {
        TypedArray typedArray = getResources().obtainTypedArray(R.array.welcome_anim);
        int len = typedArray.length();
        int[] resId = new int[len];
        for (int i = 0; i < len; i++) {
            resId[i] = typedArray.getResourceId(i, -1);
        }
        typedArray.recycle();
        return resId;
    }
}
