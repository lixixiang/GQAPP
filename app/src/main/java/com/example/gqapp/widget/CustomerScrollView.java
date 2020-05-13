package com.example.gqapp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.example.gqapp.utils.Utils;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/26 10:42
 * @Author: 李熙祥
 * @Description: java类作用描述 底部上拉线
 */
public class CustomerScrollView extends ScrollView  {

    // Drag the size of the size = 4 only allows to drag the screen 1/4
    private static final int size = 8;
    private View inner;
    private float y;
    private Rect normal = new Rect();
    private boolean next = false;

    public interface ScrollViewChangeListener {
        void onScrollChanged(CustomerScrollView scrollView, int x, int y,
                             int oldx, int oldy);
    }

    private ScrollViewChangeListener changeListener = null;

    public void setChangeListener(ScrollViewChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public interface ScrollViewListener {
        void isBottom(boolean isbottom);
    }

    private ScrollViewListener scrollViewListener = null;

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public CustomerScrollView(Context context) {
        super(context);
    }

    public CustomerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            inner = getChildAt(0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner == null) {
            return super.onTouchEvent(ev);
        } else {
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    public void commOnTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                next = false;
                y = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (isNeedAnimation()) {
                    // Log.v("yuanjl", "will up and animation");
                    animation();
                    if (scrollViewListener != null) {
                        scrollViewListener.isBottom(next);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                final float preY = y;
                float nowY = ev.getY();
                /**
                 * Size = 4 indicates that the distance to the drag is 1/4 of the height of the screen
                 */
                int deltaY = (int) (preY - nowY) / size;
                // scroll
                // scrollBy(0, deltaY);
                y = nowY;
                // When the scroll to the top or the most when it will not scroll, then move the layout.
                if (isNeedMove()) {
                    if (normal.isEmpty()) {
                        // Save the normal layout position
                        normal.set(inner.getLeft(), inner.getTop(),
                                inner.getRight(), inner.getBottom());
                        return;
                    }
                    int yy = inner.getTop() - deltaY;
                    if (yy < -100) {
                        next = true;
                    } else {
                        next = false;
                    }
                    Log.d("yy", yy + "");
                    // Move the layout
                    inner.layout(inner.getLeft(), yy, inner.getRight(),
                            inner.getBottom() - deltaY);
                }
                break;
            default:
                break;
        }
    }

    // Turn on animation to move
    public void animation() {
        TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
                normal.top);
        ta.setDuration(300);
        inner.startAnimation(ta);
        // Set back to the normal layout position
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);
        normal.setEmpty();
    }

    // Whether you need to turn on animation
    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    // Whether you need to move the layout
    public boolean isNeedMove() {
        int offset = inner.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (changeListener != null) {
            changeListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
}
