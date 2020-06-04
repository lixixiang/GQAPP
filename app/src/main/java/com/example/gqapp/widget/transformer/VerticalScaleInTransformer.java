package com.example.gqapp.widget.transformer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.example.gqapp.R;
import com.example.gqapp.app.MyApplication;

public class VerticalScaleInTransformer implements ViewPager.PageTransformer {
    protected static final float MIN_SCALE = 0.8f;
    protected static final float MIN_SCALE_OFF = 0.5f;
    protected static final float ALPHA = 0.5f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < -1) {  //[负无穷,-1]
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        } else if (position <= 1) { //[-1,1]
            if (position < 0) { //左边这个页面作用在{0，-1} {-1，0} 之间
                float scaleA = MIN_SCALE + (1 - MIN_SCALE) * (1 + position);
                float alphaA = ALPHA + (1 - ALPHA) * (1 + position);
                page.setScaleX(scaleA);
                page.setScaleY(scaleA);
                page.setAlpha(alphaA);
                if (position == -1) {
                    page.setBackgroundResource(0);
                } else if (position == 0) {
                    page.setBackgroundResource(R.drawable.steering_wheel_theme_adjustment_1_btn_selected);
                }
            } else { //右边这个页面 {1，0}  {0，1} 之间
                float scaleB = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);
                float alphaB = ALPHA + (1 - ALPHA) * (1 - position);
                page.setScaleX(scaleB);
                page.setScaleY(scaleB);
                page.setAlpha(alphaB);
                if (position == 1) {
                    page.setBackgroundResource(0);
                } else if (position == 0) {
                    page.setBackgroundResource(R.drawable.steering_wheel_theme_adjustment_1_btn_selected);
                }
            }

        } else { //[1,正无穷]
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        }

        float transX = page.getWidth() * -position;
        page.setTranslationX(transX);
        float transY = position * page.getHeight();
        page.setTranslationY(transY);
    }
}
