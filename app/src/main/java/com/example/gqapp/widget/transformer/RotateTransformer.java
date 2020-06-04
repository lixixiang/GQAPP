package com.example.gqapp.widget.transformer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/5/19 19:48
 * @Author: 李熙祥
 * @Description: java类作用描述 viewPager 两侧界面显示
 */
public class RotateTransformer implements ViewPager.PageTransformer {
    protected static final float MIN_SCALE = 0.8f;
    protected static final float MIN_ROTATE = 25f;
    protected static final float ALPHA = 0.5f;

    public RotateTransformer() {
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        float rotate = MIN_ROTATE * Math.abs(position);
        page.setPivotY(page.getHeight()/2);
        if (position < -1) {//[-Infinity,-1)
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            page.setAlpha(ALPHA);

            page.setRotationY(-1*MIN_ROTATE);
            page.setPivotX(page.getWidth());
        }  else if (position <= 1) {// [-1,1]
            page.setRotationY(position * MIN_ROTATE);

            if (position < 0) { //左页面 //[0,-1]
                page.setPivotX(page.getWidth() * (MIN_ROTATE + MIN_ROTATE * (-position)));
                page.setPivotX(page.getWidth());

                float scaleA = MIN_SCALE + (1 - MIN_SCALE) * (1 + position);
                float alphaA = ALPHA + (1 - ALPHA) * (1 + position);
                page.setScaleX(scaleA);
                page.setScaleY(scaleA);
                page.setAlpha(alphaA);

            } else { //右页面 //[1,0]
                page.setPivotX(page.getWidth() * MIN_ROTATE * (1 - position));
                page.setPivotX(0);

                float scaleB = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);
                float alphaB = ALPHA + (1 - ALPHA) * (1 - position);
                page.setScaleX(scaleB);
                page.setScaleY(scaleB);
                page.setAlpha(alphaB);
            }

        } else { // (1,+Infinity]
     //       page.setRotationY(rotate);
            page.setRotationY(1*MIN_ROTATE);
            page.setPivotX(0);

            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            page.setAlpha(ALPHA);

        }

    }
}
