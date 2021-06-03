package com.zaaach.circularpercentageview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import java.util.Locale;

/**
 * @Author: Zaaach
 * @Date: 2021/5/24
 * @Email: zaaach@aliyun.com
 * @Description: 圆形百分比View
 */
public class CircularPercentageView extends View {
    private static final int DEFAULT_ANIM_DURATION = 600;
    private static final int DEFAULT_START_ANGLE = -90;

    private static final int DEFAULT_BAR_STYLE = 0;
    private static final int BAR_STYLE_ROUND = 0;
    private static final int BAR_STYLE_BUTT = 1;

    //Animation interpolator
    private static final int DEFAULT_ANIMATION_INTERPOLATOR = 0;
    public static final int LINEAR = 0;
    public static final int ACCELERATE = 1;
    public static final int DECELERATE = 2;
    public static final int ACCELERATE_DECELERATE = 3;
    public static final int ANTICIPATE = 4;
    public static final int OVERSHOOT = 5;
    public static final int ANTICIPATE_OVERSHOOT = 6;
    public static final int BOUNCE = 7;
    public static final int FAST_OUT_LINEAR_IN = 8;
    public static final int FAST_OUT_SLOW_IN = 9;
    public static final int LINEAR_OUT_SLOW_IN = 10;

    private float barWidth;
    private int barStyle;
    private int backgroundBarColor;
    private int percentageBarColor;
    private float mPercentage;
    private int animDuration;
    private int startAngle;
    private boolean showText;
    private int textColor;
    private float textSize;

    private Paint backgroundPaint;
    private Paint percentagePaint;
    private Paint textPaint;
    private RectF rectF = new RectF();
    private Paint.Cap barCap;
    private ValueAnimator percentageAnimator;
    private Interpolator animInterpolator;

    public CircularPercentageView(Context context) {
        this(context, null);
    }

    public CircularPercentageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularPercentageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircularPercentageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircularPercentageView);
        barWidth = array.getDimensionPixelSize(R.styleable.CircularPercentageView_cpv_bar_width, 24);
        barStyle = array.getInt(R.styleable.CircularPercentageView_cpv_percentage_bar_style, DEFAULT_BAR_STYLE);
        backgroundBarColor = array.getColor(R.styleable.CircularPercentageView_cpv_background_bar_color, Color.parseColor("#e6e6e6"));
        percentageBarColor = array.getColor(R.styleable.CircularPercentageView_cpv_percentage_bar_color, Color.parseColor("#22DE6A"));
        mPercentage = array.getFloat(R.styleable.CircularPercentageView_cpv_percentage, 0);
        animDuration = array.getInt(R.styleable.CircularPercentageView_cpv_animation_duration, DEFAULT_ANIM_DURATION);
        startAngle = array.getInt(R.styleable.CircularPercentageView_cpv_start_angle, DEFAULT_START_ANGLE);
        int interpolator = array.getInt(R.styleable.CircularPercentageView_cpv_interpolator, DEFAULT_ANIMATION_INTERPOLATOR);
        showText = array.getBoolean(R.styleable.CircularPercentageView_cpv_show_text, false);
        textColor = array.getColor(R.styleable.CircularPercentageView_cpv_text_color, Color.LTGRAY);
        textSize = array.getDimensionPixelSize(R.styleable.CircularPercentageView_cpv_text_size, 36);
        array.recycle();

        if (mPercentage < 0) mPercentage = 0;
        if (mPercentage > 1) mPercentage = 1;

        startAngle %= 360;

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundBarColor);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(barWidth);

        percentagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        percentagePaint.setColor(percentageBarColor);
        percentagePaint.setStyle(Paint.Style.STROKE);
        percentagePaint.setStrokeWidth(barWidth);
        switch (barStyle){
            case BAR_STYLE_ROUND:
                barCap = Paint.Cap.ROUND;
                break;
            case BAR_STYLE_BUTT:
                barCap = Paint.Cap.BUTT;
                break;
        }
        percentagePaint.setStrokeCap(barCap);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);

        percentageAnimator = ValueAnimator.ofFloat(0, mPercentage)
                .setDuration(animDuration);
        switch (interpolator){
            case LINEAR:
                animInterpolator = new LinearInterpolator();
                break;
            case ACCELERATE:
                animInterpolator = new AccelerateInterpolator();
                break;
            case DECELERATE:
                animInterpolator = new DecelerateInterpolator();
                break;
            case ACCELERATE_DECELERATE:
                animInterpolator = new AccelerateDecelerateInterpolator();
                break;
            case ANTICIPATE:
                animInterpolator = new AnticipateInterpolator();
                break;
            case OVERSHOOT:
                animInterpolator = new OvershootInterpolator();
                break;
            case ANTICIPATE_OVERSHOOT:
                animInterpolator = new AnticipateOvershootInterpolator();
                break;
            case BOUNCE:
                animInterpolator = new BounceInterpolator();
                break;
            case FAST_OUT_LINEAR_IN:
                animInterpolator = new FastOutLinearInInterpolator();
                break;
            case FAST_OUT_SLOW_IN:
                animInterpolator = new FastOutSlowInInterpolator();
                break;
            case LINEAR_OUT_SLOW_IN:
                animInterpolator = new LinearOutSlowInInterpolator();
                break;
        }
        percentageAnimator.setInterpolator(animInterpolator);
        percentageAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPercentage = (float) animation.getAnimatedValue();
                if (mPercentage < 0) mPercentage = 0;
                if (mPercentage > 1) mPercentage = 1;
                postInvalidateOnAnimation();
            }
        });
        if (mPercentage > 0){
            percentageAnimator.start();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        float cx = width >> 1;
        float cy = cx;
        float radius = cx - barWidth / 2;
        canvas.drawCircle(cx, cy, radius, backgroundPaint);

        if (mPercentage > 0) {
            rectF.set(cx - radius, cy - radius, cx + radius, cy + radius);
            percentagePaint.setStrokeCap(mPercentage == 1 ? Paint.Cap.BUTT : barCap);
            canvas.drawArc(rectF, startAngle, 360f * mPercentage, false, percentagePaint);
        }

        if (showText) {
            String text = String.format(Locale.getDefault(), "%.0f%%", mPercentage * 100);
            Paint.FontMetrics fm = textPaint.getFontMetrics();
            float baseline = cy + (fm.descent - fm.ascent) / 2 - fm.descent;
            canvas.drawText(text, cx - textPaint.measureText(text) / 2, baseline, textPaint);
        }
    }

    public void setPercentage(@FloatRange(from = 0, to = 1) float percentage){
        setPercentage(percentage, true);
    }

    public void setPercentage(@FloatRange(from = 0, to = 1) float percentage, boolean animate){
        if (mPercentage == percentage) return;
        if (animate){
            cancelAnimator();
            updateAnimator(percentage);
        }else {
            mPercentage = percentage;
            postInvalidate();
        }
    }

    public void setAnimationInterpolator(Interpolator interpolator){
        if (percentageAnimator != null){
            percentageAnimator.setInterpolator(interpolator);
        }
    }

    public void setBackgroundBarColor(int color){
        backgroundBarColor = color;
    }

    public void setPercentageBarColor(int color){
        percentageBarColor = color;
    }

    public void setBarWidth(float width){
        barWidth = width;
    }

    public void setShowText(boolean show){
        showText = show;
    }

    public void setBarStyle(int style){
        if (style != BAR_STYLE_ROUND && style != BAR_STYLE_BUTT){
            throw new IllegalArgumentException("it must be BAR_STYLE_ROUND or BAR_STYLE_BUTT");
        }
        barStyle = style;
        switch (style){
            case BAR_STYLE_ROUND:
                barCap = Paint.Cap.ROUND;
                break;
            case BAR_STYLE_BUTT:
                barCap = Paint.Cap.BUTT;
                break;
        }
    }

    private void cancelAnimator(){
        if (percentageAnimator != null && percentageAnimator.isRunning()){
            percentageAnimator.cancel();
        }
    }

    private void updateAnimator(float value){
        if (percentageAnimator != null){
            percentageAnimator.setFloatValues(mPercentage, value);
            percentageAnimator.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAnimator();
    }
}
