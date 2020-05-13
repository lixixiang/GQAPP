package com.example.gqapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.gqapp.R;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/23 8:17
 * @Author: 李熙祥
 * @Description: java类作用描述 微信底部凸出部分
 */
public class WeChatARCView extends View {
    private int mWidth;
    private int mHeight;

    /**
     * 弧形高度
     *
     * @param context
     */
    private int mArcHeight;

    /**
     * 前景颜色
     *
     * @param context
     */
    private int mBgColor;
    private Paint mPaint;
    private Context mContext;

    public WeChatARCView(Context context) {
        this(context, null);
    }

    public WeChatARCView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeChatARCView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcView);
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcView_arcHeight, 0);
        mBgColor = typedArray.getColor(R.styleable.ArcView_bgColor, Color.parseColor("#868686"));

        mContext = context;
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mBgColor);

        Rect rect = new Rect(0, mHeight - mArcHeight, mWidth, mHeight);
        canvas.drawRect(rect, mPaint);


        Path path = new Path();
        path.moveTo(0, mHeight- mArcHeight);
        path.quadTo(mWidth / 2, getHeight() - 2 * mArcHeight, mWidth, mHeight - mArcHeight);
        canvas.drawPath(path, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }
}














