package com.example.gqapp.widget.transformer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import static com.example.gqapp.widget.transformer.VerticalScaleInTransformer.ALPHA;
import static com.example.gqapp.widget.transformer.VerticalScaleInTransformer.MIN_SCALE;
import static com.example.gqapp.widget.transformer.VerticalScaleInTransformer.MIN_SCALE_OFF;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/25 13:59
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class HorizontalTransformer implements ViewPager.PageTransformer{

    @Override
    public void transformPage(@NonNull View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0f);

        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            view.setAlpha(1f);
            view.setTranslationX(0f);
            view.setScaleX(1f);
            view.setScaleY(1f);

        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            view.setAlpha(1 - position);

            // Counteract the default slide transition
            view.setTranslationX(pageWidth * -position);

            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0f);
        }
    }
}
