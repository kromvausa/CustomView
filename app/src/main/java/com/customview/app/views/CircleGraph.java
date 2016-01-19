package com.customview.app.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;

import com.customview.app.R;

/**
 * @author Mark Alvarez
 */
public class CircleGraph extends View {
    public static final String LOG = CircleGraph.class.getName();
    private static final float START_ANGLE_DEFAULT = 0;
    private static final float END_ANGLE_DEFAULT = 90;
    private static final int DEFAULT_COLOR = Color.BLUE;
    private static final int DEFAULT_DURATION1 = 2000;
    private static final int DEFAULT_DURATION2 = 3000;
    private static final int DEFAULT_DURATION3 = 4000;
    private static final int DEFAULT_ALPHA_VALUE = 60;

    private Paint mPaintToUse;
    private float mStrokeWidthPercentage = 0.065f;
    private float mSize;

    private RectF mCircle1;
    private int mCircleColor1 = DEFAULT_COLOR;
    private float mCircleInitAngle1 = START_ANGLE_DEFAULT;
    private float mCircleEndAngle1 = END_ANGLE_DEFAULT;

    private RectF mCircle2;
    private int mCircleColor2 = DEFAULT_COLOR;
    private float mCircleInitAngle2 = START_ANGLE_DEFAULT;
    private float mCircleEndAngle2 = END_ANGLE_DEFAULT;

    private RectF mCircle3;
    private int mCircleColor3 = DEFAULT_COLOR;
    private float mCircleInitAngle3 = START_ANGLE_DEFAULT;
    private float mCircleEndAngle3 = END_ANGLE_DEFAULT;

    public CircleGraph(Context context) {
        super(context);
        init(null, 0);
    }

    public CircleGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CircleGraph(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        // Load attributes
        try {

            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CircleGraph, defStyle, 0);

            mCircleColor1 = a.getColor(R.styleable.CircleGraph_circle1Color, DEFAULT_COLOR);
            mCircleInitAngle1 = a.getFloat(R.styleable.CircleGraph_circle1InitialAngle, START_ANGLE_DEFAULT);
            mCircleEndAngle1 = a.getFloat(R.styleable.CircleGraph_circle1EndAngle, END_ANGLE_DEFAULT);

            mCircleColor2 = a.getColor(R.styleable.CircleGraph_circle2Color, DEFAULT_COLOR);
            mCircleInitAngle2 = a.getFloat(R.styleable.CircleGraph_circle2InitialAngle, START_ANGLE_DEFAULT);
            mCircleEndAngle2 = a.getFloat(R.styleable.CircleGraph_circle2EndAngle, END_ANGLE_DEFAULT);

            mCircleColor3 = a.getColor(R.styleable.CircleGraph_circle3Color, DEFAULT_COLOR);
            mCircleInitAngle3 = a.getFloat(R.styleable.CircleGraph_circle3InitialAngle, START_ANGLE_DEFAULT);
            mCircleEndAngle3 = a.getFloat(R.styleable.CircleGraph_circle3EndAngle, END_ANGLE_DEFAULT);

            // Recycle
            a.recycle();

        } catch (Exception e) {
            Log.i(LOG, "Error :: " + e);
        }

        mPaintToUse = new Paint();
        mPaintToUse.setAntiAlias(true);
        mPaintToUse.setStyle(Paint.Style.STROKE);

        mCircle1 = new RectF();
        mCircle2 = new RectF();
        mCircle3 = new RectF();

        // Animation Set
        AnimationSet animationSet = new AnimationSet(false);

        // Animation 1
        CircleAnimation animator = new CircleAnimation(this, mCircleEndAngle1, 1);
        animator.setDuration(DEFAULT_DURATION1);
        animationSet.addAnimation(animator);

        // Animation 2
        animator = new CircleAnimation(this, mCircleEndAngle2, 2);
        animator.setDuration(DEFAULT_DURATION2);
        animationSet.addAnimation(animator);

        // Animation 3
        animator = new CircleAnimation(this, mCircleEndAngle3, 3);
        animator.setDuration(DEFAULT_DURATION3);
        animationSet.addAnimation(animator);

        // Run the animation
        startAnimation(animationSet);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw Circle 1
        mPaintToUse.setStrokeWidth(mStrokeWidthPercentage * mSize);
        mPaintToUse.setColor(Color.argb(DEFAULT_ALPHA_VALUE, 0, 0, 0));
        canvas.drawArc(mCircle1, 0, 360, false, mPaintToUse);
        mPaintToUse.setColor(mCircleColor1);
        canvas.drawArc(mCircle1, mCircleInitAngle1, mCircleEndAngle1, false, mPaintToUse);

        // Draw Circle 2
        mPaintToUse.setStrokeWidth((mStrokeWidthPercentage - 0.01f) * mSize);
        mPaintToUse.setColor(Color.argb(DEFAULT_ALPHA_VALUE, 0, 0, 0));
        canvas.drawArc(mCircle2, 0, 360, false, mPaintToUse);
        mPaintToUse.setColor(mCircleColor2);
        canvas.drawArc(mCircle2, mCircleInitAngle2, mCircleEndAngle2, false, mPaintToUse);

        // Draw Circle 3
        mPaintToUse.setStrokeWidth((mStrokeWidthPercentage - 0.02f) * mSize);
        mPaintToUse.setColor(Color.argb(DEFAULT_ALPHA_VALUE, 0, 0, 0));
        canvas.drawArc(mCircle3, 0, 360, false, mPaintToUse);
        mPaintToUse.setColor(mCircleColor3);
        canvas.drawArc(mCircle3, mCircleInitAngle3, mCircleEndAngle3, false, mPaintToUse);
    }

    /**
     * Measure the circles and position them on the available area.
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);

            // If there is no change, skip the calculation
            if (!changed) return;

            // Make all the calculations
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int paddingRight = getPaddingRight();
            int paddingBottom = getPaddingBottom();

            int contentWidth = getWidth() - paddingLeft - paddingRight;
            int contentHeight = getHeight() - paddingTop - paddingBottom;

            mSize = Math.min(contentWidth, contentHeight);

            // Calculate the base left, top
            float mLeft;
            float mTop;
            float mRight;
            float mBottom;
            float padding = mStrokeWidthPercentage * mSize;
            if (contentHeight > contentWidth) {
                mLeft = padding / 2f;
                mTop = (contentHeight - contentWidth + padding) / 2f;
                mRight = contentWidth - padding / 2f;
                mBottom = contentHeight - mTop;
            } else if (contentWidth > contentHeight) {
                mLeft = (contentWidth - contentHeight + padding) / 2f;
                mTop = padding / 2f;
                mRight = (contentWidth + contentHeight - padding) / 2f;
                mBottom = contentHeight - padding / 2f;
            } else {
                mLeft = padding / 2f;
                mTop = padding / 2f;
                mRight = contentWidth - padding / 2f;
                mBottom = contentHeight - padding / 2f;
            }

            // Draw Circle 1
            mCircle1.left = mLeft + paddingLeft;
            mCircle1.top = mTop + paddingTop;
            mCircle1.right = mRight + paddingRight;
            mCircle1.bottom = mBottom + paddingBottom;

            // Draw Circle 2
            mCircle2.left = mCircle1.left + 3 * padding;
            mCircle2.top = mCircle1.top + 3 * padding;
            mCircle2.right = mCircle1.right - 3 * padding;
            mCircle2.bottom = mCircle1.bottom - 3 * padding;

            // Draw Circle 3
            mCircle3.left = mCircle1.left + 5 * padding;
            mCircle3.top = mCircle1.top + 5 * padding;
            mCircle3.right = mCircle1.right - 5 * padding;
            mCircle3.bottom = mCircle1.bottom - 5 * padding;
        }

    // Measure the custom view to the specified size
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        // Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            // Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            // Can't be bigger than...
            width = widthSize;
        } else {
            // Be whatever you want
            width = widthSize;
        }

        // Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            // Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            // Can't be bigger than...
            height = heightSize;
        } else {
            // Be whatever you want
            height = heightSize;
        }

        // MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    /**
     * @return returns the first circle's initial angle
     */
    public float getCircleInitAngle1() {
        return mCircleInitAngle1;
    }

    /**
     * @param circleInitAngle1 update the initial angle of the first circle and update the UI
     */
    public void setCircleInitAngle1(float circleInitAngle1) {
        this.mCircleInitAngle1 = circleInitAngle1;
        invalidate();
    }

    /**
     * @return returns the first circle's end angle
     */
    public float getCircleEndAngle1() {
        return mCircleEndAngle1;
    }

    /**
     * @param circleEndAngle1 update the end angle of the first circle and update the UI
     */
    public void setCircleEndAngle1(float circleEndAngle1) {
        this.mCircleEndAngle1 = circleEndAngle1;
        invalidate();
    }

    /**
     * @return returns the second circle's initial angle
     */
    public float getCircleInitAngle2() {
        return mCircleInitAngle2;
    }

    /**
     * @param circleInitAngle2 update the initial angle of the second circle and update the UI
     */
    public void setCircleInitAngle2(float circleInitAngle2) {
        this.mCircleInitAngle2 = circleInitAngle2;
        invalidate();
    }

    /**
     * @return returns the second circle's end angle
     */
    public float getCircleEndAngle2() {
        return mCircleEndAngle2;
    }

    /**
     * @param circleEndAngle2 update the end angle of the second circle and update the UI
     */
    public void setCircleEndAngle2(float circleEndAngle2) {
        this.mCircleEndAngle2 = circleEndAngle2;
        invalidate();
    }

    /**
     * @return returns the third circle's initial angle
     */
    public float getCircleInitAngle3() {
        return mCircleInitAngle3;
    }

    /**
     * @param circleInitAngle3 update the initial angle of the third circle and update the UI
     */
    public void setCircleInitAngle3(float circleInitAngle3) {
        this.mCircleInitAngle3 = circleInitAngle3;
        invalidate();
    }

    /**
     * @return returns the third circle's end angle
     */
    public float getCircleEndAngle3() {
        return mCircleEndAngle3;
    }

    /**
     * @param circleEndAngle3 update the end angle of the third circle and update the UI
     */
    public void setCircleEndAngle3(float circleEndAngle3) {
        this.mCircleEndAngle3 = circleEndAngle3;
        invalidate();
    }

}
