package com.example.gqapp.widget.video;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/6/1 15:35
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class FullScreenVideoView extends VideoView {

    public FullScreenVideoView(Context context) {
        super(context);
    }

    public FullScreenVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullScreenVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
    }
}