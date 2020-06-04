package com.example.gqapp.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/28 10:48
 * @Author: 李熙祥
 * @Description: java类作用描述 动画工具类
 */
public class AnimationUtil {
    public static final Long ANIMA_TIME = 300L;

    /**
     * 缩放动画 与 平移动画
     *
     * @param view
     * @param fromScale
     * @param toScale
     * @param fromX
     * @param toX
     * @param fromY
     * @param toY
     * @param listener
     */
    public static void startZoomAnim(View view,
                                     // 缩放
                                     float fromScale, float toScale,
                                     // 平移
                                     float fromX, float toX, float fromY, float toY,
                                     // 动画
                                     Animator.AnimatorListener listener) {

        if (view == null) {
            return;
        }
        if (view.getAnimation() != null) {
            view.getAnimation().cancel();
            view.clearAnimation();
            return;
        }

        AnimatorSet localAnimatorSet = new AnimatorSet();
        // ############## scaleX ###############
        float[] scaleX = new float[2];
        scaleX[0] = fromScale;
        scaleX[1] = toScale;
        ObjectAnimator scaleXAnima = ObjectAnimator.ofFloat(view,
                "scaleX", scaleX);
        scaleXAnima.setDuration(ANIMA_TIME);
        scaleXAnima.setInterpolator(new DecelerateInterpolator());
        // ############## scaleY ###############
        float[] scaleY = new float[2];
        scaleY[0] = fromScale;
        scaleY[1] = toScale;
        ObjectAnimator scaleYAnima = ObjectAnimator.ofFloat(view,
                "scaleY", scaleY);
        scaleYAnima.setDuration(ANIMA_TIME);
        scaleYAnima.setInterpolator(new DecelerateInterpolator());
        // #############translationX ##########
        float[] translationX = new float[2];
        translationX[0] = fromX;
        translationX[1] = toX;
        ObjectAnimator translationXAnima = ObjectAnimator.ofFloat(view,
                "translationX", translationX);
        translationXAnima.setDuration(ANIMA_TIME);
        translationXAnima.setInterpolator(new DecelerateInterpolator());
        // ###############translationY##################
        float[] translationY = new float[2];
        translationY[0] = fromY;
        translationY[1] = toY;
        ObjectAnimator translationYAnima = ObjectAnimator.ofFloat(view,
                "translationY", translationY);
        translationYAnima.setDuration(ANIMA_TIME);
        translationYAnima.setInterpolator(new DecelerateInterpolator());

        //
        Animator[] arrayOfAnimator = new Animator[4];
        arrayOfAnimator[0] = scaleXAnima;
        arrayOfAnimator[1] = scaleYAnima;
        arrayOfAnimator[2] = translationXAnima;
        arrayOfAnimator[3] = translationYAnima;
        localAnimatorSet.playTogether(arrayOfAnimator);
        if (listener != null) {
            localAnimatorSet.addListener(listener);
        }
        //
        localAnimatorSet.start();
    }

    /**
     * 开启Alpha 动画
     *
     * @param view
     * @param fromAlpha
     * @param toAlpha
     */
    public static void startAlphaAnima(View view,
                                       float fromAlpha, float toAlpha, int duration) {
        if (view == null) {
            return;
        }
        if (view.getAnimation() != null) {
            view.getAnimation().cancel();
            view.clearAnimation();
            return;
        }
        //-------Alpaha--------
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(view, "alpha", fromAlpha, toAlpha));
        set.setDuration(duration);
        set.setInterpolator(new DecelerateInterpolator());
        set.start();
    }

    /**
     * alpha 变化
     * 由暗变亮，再由亮变暗 做循环播用
     */
    public static void startAlphaSet(List<ImageView> images, float fromAlpha, float toAlpha, int duration) {
        if (images == null) {
            return;
        }
        List<ObjectAnimator> objAnim = new ArrayList<>();
        List<ObjectAnimator> objAnim2 = new ArrayList<>();
        List<ObjectAnimator> newAlphaAnim2 = new ArrayList<>();
        //-------Alpha--------
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(images.get(0), "alpha", fromAlpha, toAlpha - 0.10f);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(images.get(1), "alpha", fromAlpha, toAlpha - 0.20f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(images.get(2), "alpha", fromAlpha, toAlpha - 0.30f);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(images.get(3), "alpha", fromAlpha, toAlpha - 0.40f);
        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(images.get(4), "alpha", fromAlpha, toAlpha - 0.50f);

        objectAnimator.setDuration(duration / 5);
        objectAnimator1.setDuration(duration / 4);
        objectAnimator2.setDuration(duration / 3);
        objectAnimator3.setDuration(duration / 2);
        objectAnimator4.setDuration(duration / 1);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator1.setInterpolator(new DecelerateInterpolator());
        objectAnimator2.setInterpolator(new DecelerateInterpolator());
        objectAnimator3.setInterpolator(new DecelerateInterpolator());
        objectAnimator4.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(images.get(0), "scale", 1.0f, 1.5f);
        ObjectAnimator scaleAnimator1 = ObjectAnimator.ofFloat(images.get(1), "scale", 1.0f, 3f);
        ObjectAnimator scaleAnimator2 = ObjectAnimator.ofFloat(images.get(2), "scale", 1.0f, 4.5f);
        ObjectAnimator scaleAnimator3 = ObjectAnimator.ofFloat(images.get(3), "scale", 1.0f, 6.0f);
        ObjectAnimator scaleAnimator4 = ObjectAnimator.ofFloat(images.get(4), "scale", 1.0f, 7.5f);

        scaleAnimator.setDuration(duration / 5 + 500 * 1);
        scaleAnimator1.setDuration(duration / 4 + 500 * 2);
        scaleAnimator2.setDuration(duration / 3 + 500 * 3);
        scaleAnimator3.setDuration(duration / 2 + 500 * 4);
        scaleAnimator4.setDuration(duration / 1 + 500 * 5);

        scaleAnimator.setInterpolator(new DecelerateInterpolator());
        scaleAnimator1.setInterpolator(new DecelerateInterpolator());
        scaleAnimator2.setInterpolator(new DecelerateInterpolator());
        scaleAnimator3.setInterpolator(new DecelerateInterpolator());
        scaleAnimator4.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator newAlpha = ObjectAnimator.ofFloat(images.get(0), "alpha", toAlpha - 0.10f, fromAlpha);
        ObjectAnimator newAlpha1 = ObjectAnimator.ofFloat(images.get(1), "alpha", toAlpha - 0.20f, fromAlpha);
        ObjectAnimator newAlpha2 = ObjectAnimator.ofFloat(images.get(2), "alpha", toAlpha - 0.30f, fromAlpha);
        ObjectAnimator newAlpha3 = ObjectAnimator.ofFloat(images.get(3), "alpha", toAlpha - 0.40f, fromAlpha);
        ObjectAnimator newAlpha4 = ObjectAnimator.ofFloat(images.get(4), "alpha", toAlpha - 0.50f, fromAlpha);

        newAlpha.setDuration(duration / 5 + 500 * 1);
        newAlpha1.setDuration(duration / 4 + 500 * 2);
        newAlpha2.setDuration(duration / 3 + 500 * 3);
        newAlpha3.setDuration(duration / 2 + 500 * 4);
        newAlpha4.setDuration(duration / 1 + 500 * 5);

        newAlpha.setInterpolator(new DecelerateInterpolator());
        newAlpha1.setInterpolator(new DecelerateInterpolator());
        newAlpha2.setInterpolator(new DecelerateInterpolator());
        newAlpha3.setInterpolator(new DecelerateInterpolator());
        newAlpha4.setInterpolator(new DecelerateInterpolator());

        AnimatorSet set = new AnimatorSet();
        set.playTogether(objectAnimator, objectAnimator1, objectAnimator2, objectAnimator3, objectAnimator4
                 , scaleAnimator, newAlpha, scaleAnimator1, newAlpha1, scaleAnimator2, newAlpha2, scaleAnimator3, newAlpha3, scaleAnimator4, newAlpha4
        );
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(images.get(0), "alpha", fromAlpha, toAlpha - 0.10f);
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(images.get(1), "alpha", fromAlpha, toAlpha - 0.20f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(images.get(2), "alpha", fromAlpha, toAlpha - 0.30f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(images.get(3), "alpha", fromAlpha, toAlpha - 0.40f);
                ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(images.get(4), "alpha", fromAlpha, toAlpha - 0.50f);

                objectAnimator.setDuration(duration / 5);
                objectAnimator1.setDuration(duration / 4);
                objectAnimator2.setDuration(duration / 3);
                objectAnimator3.setDuration(duration / 2);
                objectAnimator4.setDuration(duration / 1);

                objectAnimator.setInterpolator(new DecelerateInterpolator());
                objectAnimator1.setInterpolator(new DecelerateInterpolator());
                objectAnimator2.setInterpolator(new DecelerateInterpolator());
                objectAnimator3.setInterpolator(new DecelerateInterpolator());
                objectAnimator4.setInterpolator(new DecelerateInterpolator());

                ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(images.get(0), "scale", 1.0f, 1.5f);
                ObjectAnimator scaleAnimator1 = ObjectAnimator.ofFloat(images.get(1), "scale", 1.0f, 3f);
                ObjectAnimator scaleAnimator2 = ObjectAnimator.ofFloat(images.get(2), "scale", 1.0f, 4.5f);
                ObjectAnimator scaleAnimator3 = ObjectAnimator.ofFloat(images.get(3), "scale", 1.0f, 6.0f);
                ObjectAnimator scaleAnimator4 = ObjectAnimator.ofFloat(images.get(4), "scale", 1.0f, 7.5f);

                scaleAnimator.setDuration(duration / 5 + 500 * 1);
                scaleAnimator1.setDuration(duration / 4 + 500 * 2);
                scaleAnimator2.setDuration(duration / 3 + 500 * 3);
                scaleAnimator3.setDuration(duration / 2 + 500 * 4);
                scaleAnimator4.setDuration(duration / 1 + 500 * 5);

                scaleAnimator.setInterpolator(new DecelerateInterpolator());
                scaleAnimator1.setInterpolator(new DecelerateInterpolator());
                scaleAnimator2.setInterpolator(new DecelerateInterpolator());
                scaleAnimator3.setInterpolator(new DecelerateInterpolator());
                scaleAnimator4.setInterpolator(new DecelerateInterpolator());

                ObjectAnimator newAlpha = ObjectAnimator.ofFloat(images.get(0), "alpha", toAlpha - 0.10f, fromAlpha);
                ObjectAnimator newAlpha1 = ObjectAnimator.ofFloat(images.get(1), "alpha", toAlpha - 0.20f, fromAlpha);
                ObjectAnimator newAlpha2 = ObjectAnimator.ofFloat(images.get(2), "alpha", toAlpha - 0.30f, fromAlpha);
                ObjectAnimator newAlpha3 = ObjectAnimator.ofFloat(images.get(3), "alpha", toAlpha - 0.40f, fromAlpha);
                ObjectAnimator newAlpha4 = ObjectAnimator.ofFloat(images.get(4), "alpha", toAlpha - 0.50f, fromAlpha);

                newAlpha.setDuration(duration / 5 + 500 * 1);
                newAlpha1.setDuration(duration / 4 + 500 * 2);
                newAlpha2.setDuration(duration / 3 + 500 * 3);
                newAlpha3.setDuration(duration / 2 + 500 * 4);
                newAlpha4.setDuration(duration / 1 + 500 * 5);

                newAlpha.setInterpolator(new DecelerateInterpolator());
                newAlpha1.setInterpolator(new DecelerateInterpolator());
                newAlpha2.setInterpolator(new DecelerateInterpolator());
                newAlpha3.setInterpolator(new DecelerateInterpolator());
                newAlpha4.setInterpolator(new DecelerateInterpolator());

                set.playTogether(objectAnimator, objectAnimator1, objectAnimator2, objectAnimator3, objectAnimator4
                           , scaleAnimator, newAlpha, scaleAnimator1, newAlpha1, scaleAnimator2, newAlpha2, scaleAnimator3, newAlpha3, scaleAnimator4, newAlpha4
                );
                set.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

//        for (int i = 0; i < images.size(); i++) {
//            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(images.get(i), "alpha", fromAlpha, toAlpha - ((1 + i) * 0.10f));
//            objectAnimator.setDuration(duration -(1000* i));
//            objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
//            objectAnimator.setInterpolator(new DecelerateInterpolator());
//            objAnim.add(objectAnimator);
//        }

//        for (int i = 0; i < images.size(); i++) {
//            ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(images.get(i), "scale", 1.0f, 1.5f * (i + 1));
//            ObjectAnimator newAlpha = ObjectAnimator.ofFloat(images.get(i), "alpha", toAlpha - ((1 + i) * 0.10f), fromAlpha);
//            scaleAnimator.setDuration(4000 + duration * i );
//            newAlpha.setDuration(4000 + duration * i);
//            scaleAnimator.setInterpolator(new DecelerateInterpolator());
//            newAlpha.setInterpolator(new DecelerateInterpolator());
//            objAnim2.add(scaleAnimator);
//            newAlphaAnim2.add(newAlpha);
//        }

        //从亮变暗
//        AnimatorSet set = new AnimatorSet();
//        set.playTogether(objAnim.get(0), objAnim.get(1), objAnim.get(2), objAnim.get(3), objAnim.get(4)
//                , objAnim2.get(0),newAlphaAnim2.get(0),
//                objAnim2.get(1),newAlphaAnim2.get(1),
//                objAnim2.get(2),newAlphaAnim2.get(2),
//                objAnim2.get(3),newAlphaAnim2.get(3),
//                objAnim2.get(4),newAlphaAnim2.get(4)
//                );
//                set.start();
    }

    /**
     * 清理目标View的动画
     *
     * @param paramView
     */
    public static void clearAnimation(View paramView) {
        if (paramView == null)
            return;

        if (paramView.getAnimation() == null) {
            return;
        }

        paramView.getAnimation().cancel();
        paramView.clearAnimation();
    }

    /**
     * 旋转动画
     *
     * @param paramView
     * @param paramInt  时间
     */
    public static void startRotateAnimation(View paramView, int paramInt) {
        float[] arrayOfFloat = new float[2];
        arrayOfFloat[0] = 0.0F;
        arrayOfFloat[1] = 360.0F;
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramView,
                "rotation", arrayOfFloat);
        localObjectAnimator.setDuration(paramInt);
        localObjectAnimator.setInterpolator(null);
        localObjectAnimator.setRepeatCount(-1);
        localObjectAnimator.start();
    }

    /**
     * 放大动画
     *
     * @param paramView             目标View
     * @param paramFloat            放大的比例
     * @param paramAnimatorListener
     * @return
     */
    public static AnimatorSet startScaleToBigAnimation(View paramView,
                                                       float paramFloat, Animator.AnimatorListener paramAnimatorListener) {
        if (paramView.getAnimation() != null)
            paramView.getAnimation().cancel();
        paramView.clearAnimation();
        AnimatorSet localAnimatorSet = new AnimatorSet();
        float[] arrayOfFloat1 = new float[2];
        arrayOfFloat1[0] = 1.0F;
        arrayOfFloat1[1] = paramFloat;
        ObjectAnimator localObjectAnimator1 = ObjectAnimator.ofFloat(paramView,
                "scaleY", arrayOfFloat1);
        localObjectAnimator1.setDuration(240L);
        localObjectAnimator1.setInterpolator(new DecelerateInterpolator());
        float[] arrayOfFloat2 = new float[2];
        arrayOfFloat2[0] = 1.0F;
        arrayOfFloat2[1] = paramFloat;
        ObjectAnimator localObjectAnimator2 = ObjectAnimator.ofFloat(paramView,
                "scaleX", arrayOfFloat2);
        localObjectAnimator2.setDuration(240L);
        localObjectAnimator2.setInterpolator(new DecelerateInterpolator());
        Animator[] arrayOfAnimator = new Animator[2];
        arrayOfAnimator[0] = localObjectAnimator1;
        arrayOfAnimator[1] = localObjectAnimator2;
        localAnimatorSet.playTogether(arrayOfAnimator);
        if (paramAnimatorListener != null)
            localAnimatorSet.addListener(paramAnimatorListener);
        localAnimatorSet.start();
        return localAnimatorSet;
    }

    /**
     * 缩小动画
     *
     * @param paramView
     * @param paramFloat            缩小的比例
     * @param paramAnimatorListener
     * @return
     */
    public static AnimatorSet startScaleToSmallAnimation(View paramView,
                                                         float paramFloat, Animator.AnimatorListener paramAnimatorListener) {
        if (paramView.getAnimation() != null)
            paramView.getAnimation().cancel();
        paramView.clearAnimation();
        AnimatorSet localAnimatorSet = new AnimatorSet();
        float[] arrayOfFloat1 = new float[2];
        arrayOfFloat1[0] = paramFloat;
        arrayOfFloat1[1] = 1.0F;
        ObjectAnimator localObjectAnimator1 = ObjectAnimator.ofFloat(paramView,
                "scaleY", arrayOfFloat1);
        localObjectAnimator1.setDuration(140L);
        localObjectAnimator1.setInterpolator(new DecelerateInterpolator());
        float[] arrayOfFloat2 = new float[2];
        arrayOfFloat2[0] = paramFloat;
        arrayOfFloat2[1] = 1.0F;
        ObjectAnimator localObjectAnimator2 = ObjectAnimator.ofFloat(paramView,
                "scaleX", arrayOfFloat2);
        localObjectAnimator2.setDuration(140L);
        localObjectAnimator2.setInterpolator(new DecelerateInterpolator());
        Animator[] arrayOfAnimator = new Animator[2];
        arrayOfAnimator[0] = localObjectAnimator1;
        arrayOfAnimator[1] = localObjectAnimator2;
        localAnimatorSet.playTogether(arrayOfAnimator);
        localAnimatorSet.start();
        return localAnimatorSet;
    }

    /**
     * 从上往下移动的位移动画
     *
     * @param paramView
     * @return
     */
    public static AnimatorSet startTranlateDownAnimation(View paramView) {
        if (paramView.getAnimation() != null)
            paramView.getAnimation().cancel();
        paramView.clearAnimation();
        AnimatorSet localAnimatorSet = new AnimatorSet();
        float[] arrayOfFloat = new float[2];
        arrayOfFloat[0] = -80.0F;
        arrayOfFloat[1] = 1.0F;
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramView,
                "translationY", arrayOfFloat);
        localObjectAnimator.setDuration(240L);
        localObjectAnimator.setInterpolator(new DecelerateInterpolator());
        localAnimatorSet.play(localObjectAnimator);
        localAnimatorSet.start();
        return localAnimatorSet;
    }

    /**
     * 从下网上移动的位移动画
     *
     * @param paramView
     * @return
     */
    public static AnimatorSet startTranlateUpAnimation(View paramView) {
        if (paramView.getAnimation() != null)
            paramView.getAnimation().cancel();
        paramView.clearAnimation();
        AnimatorSet localAnimatorSet = new AnimatorSet();
        float[] arrayOfFloat = new float[2];
        arrayOfFloat[0] = 1.0F;
        arrayOfFloat[1] = -80.0F;
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramView,
                "translationY", arrayOfFloat);
        localObjectAnimator.setDuration(240L);
        localObjectAnimator.setInterpolator(new DecelerateInterpolator());
        localAnimatorSet.play(localObjectAnimator);
        localAnimatorSet.start();
        return localAnimatorSet;
    }
}
