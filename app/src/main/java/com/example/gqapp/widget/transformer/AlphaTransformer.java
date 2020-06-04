package com.example.gqapp.widget.transformer;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/5/24 11:49
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class AlphaTransformer implements ViewPager.PageTransformer{
    protected static final float ALPHA = 0f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < -1) {//[-Infinity,-1)
            page.setAlpha(0);
        } else if (position <= 1) { // [-1,1]
            if (position < 0) { //左页面
                // a -> b  ALPHA [1,0.25f]  position[0,-1]
                float alphaA = ALPHA + (1 - ALPHA) * (1 + position);
                page.setAlpha(alphaA);
            } else { //右页面 //[1,0]
                // a-b
                // b , position : (1,0)
                // alphaB  [0.25f,1]
                float alphaB = ALPHA + (1 - ALPHA) * (1 - position);

                //b - a
                // b, position : (0,1)
                // [1,0.25f]
                page.setAlpha(alphaB);
            }
        } else { // (1,+Infinity]
            page.setAlpha(0);
        }
    }
}
