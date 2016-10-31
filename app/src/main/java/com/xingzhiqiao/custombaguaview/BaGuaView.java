package com.xingzhiqiao.custombaguaview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xingzhiqiao on 2016/10/31.
 */

public class BaGuaView extends View {

    /**
     * 黑色画笔
     */
    private Paint mBlackPaint;

    /**
     * 白色画笔
     */
    private Paint mWhitePaint;

    /**
     * 背景颜色
     */
    private int mBgColor;

    /**
     * 八卦半径
     */
    private int mRadius;



    public BaGuaView(Context context) {
        this(context, null);
    }

    public BaGuaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaGuaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BaGuaView, defStyleAttr, 0);

        try {
            mBgColor = array.getColor(R.styleable.BaGuaView_bgColor, Color.GRAY);//默认为棕色
            mRadius = array.getInteger(R.styleable.BaGuaView_radius, 100);//默认半径为100
        } finally {
            array.recycle();
        }

        mWhitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBlackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


        mWhitePaint.setColor(Color.WHITE);
        mBlackPaint.setColor(Color.BLACK);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        int height = 0;

        //设定宽度
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = getPaddingLeft() + getPaddingRight() + widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = getPaddingLeft() + getPaddingRight() + mRadius * 2;
        }

        //设定高度
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(widthMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            height = getPaddingBottom() + getPaddingTop() + heightSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            height = getPaddingBottom() + getPaddingTop() + mRadius * 2;
        }

        setMeasuredDimension(width, height);


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    //设置旋转角度
    private float degrees = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        //将Canvas坐标移动到画布中心
        canvas.translate(getWidth() / 2, getHeight() / 2);
        //设置背景色
        canvas.drawColor(mBgColor);

        //设置完背景色之后让Canvas旋转
        degrees = degrees + 3;
        canvas.rotate(degrees);

        //设置绘制区域，这里是以画布中心为坐标原点
        RectF rectF = new RectF(-mRadius, -mRadius, mRadius, mRadius);
        //画左边黑色半圆
        canvas.drawArc(rectF, 90, 180, true, mBlackPaint);
        //画右边白色半圆
        canvas.drawArc(rectF, -90, 180, true, mWhitePaint);

        //绘制两个小圆
        canvas.drawCircle(0, -mRadius / 2, mRadius / 2, mBlackPaint);
        canvas.drawCircle(0, mRadius / 2, mRadius / 2, mWhitePaint);
//
        //绘制两个鱼眼，画两个更小的圆，还以刚鱼头的圆心为圆心，半径为原半径的1/6,这个值可以自己设定
        canvas.drawCircle(0, -mRadius / 2, mRadius / 6, mWhitePaint);
        canvas.drawCircle(0, mRadius / 2, mRadius / 6, mBlackPaint);

        postInvalidateDelayed(15);
    }

}
