package com.example.gqapp.utils;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/30 17:36
 * @Author: 李熙祥
 * @Description: java类作用描述 改变左右显示方向工具
 */
public class AnimOrientation {

    public static void leftRotation90(View view) {
        PropertyValuesHolder ivSeekSet = PropertyValuesHolder.ofFloat("rotation", 90f);
        PropertyValuesHolder ivSeekSet2 = PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f);
        ObjectAnimator.ofPropertyValuesHolder(view, ivSeekSet, ivSeekSet2).start();
    }

    public static void rightRotation90(View view) {
        PropertyValuesHolder ivSeekSet = PropertyValuesHolder.ofFloat("rotation", -90f);
        PropertyValuesHolder ivSeekSet2 = PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f);
        ObjectAnimator.ofPropertyValuesHolder(view, ivSeekSet, ivSeekSet2).start();
    }
}
