package xyz.lizhuo.gitpath.Activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import xyz.lizhuo.gitpath.R;

/**
 * Created by lizhuo on 16/4/18.
 */
public class WaveView extends View {
    private static final int X_SPEED = 30;
    private int mXOffset = 0;
    private int mViewWidth,mViewHeight;
    private Paint mPaint;
    private DrawFilter mDrawFilter;
    private float[] mPointY;
    private float[] mDynamicPointY;

    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        int textColor = context.obtainStyledAttributes(attrs, R.styleable.WaveViewStyle)
                .getColor(R.styleable.WaveViewStyle_wavecolor,0xFF009688);
        mPaint.setColor(textColor);
        mDrawFilter = new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        long startTime = System.currentTimeMillis();
        mPaint.setXfermode(xfermode);
        canvas.setDrawFilter(mDrawFilter);// mDrawFilter 是用来给 canvas 设置抗锯齿的
        runWave();
        for (int i=0;i<mViewWidth;i++){
            canvas.drawLine(i,mViewWidth-mDynamicPointY[i]-400,i,mViewHeight,mPaint);

        }
        mXOffset +=X_SPEED;
        if (mXOffset>mViewWidth){
            mXOffset = 0;
        }
        long endTime = System.currentTimeMillis();
        int delay = 0;
        if (endTime-startTime<30){
            delay = (int)(30-(endTime-startTime));
        }
        canvas.drawCircle(getWidth()/2, getHeight()/2, getWidth()/2, mPaint);
        mPaint.setXfermode(xfermode);

        postInvalidateDelayed(delay);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        mPointY = new float[w];
        mDynamicPointY = new float[w];
        for (int i=0;i<w;i++){
            mPointY[i] = (float)(30*Math.sin(2*Math.PI*i/w));
        }
    }

    public void runWave(){
        int yInterval = mPointY.length-mXOffset;
        System.arraycopy(mPointY,0,mDynamicPointY,mXOffset,yInterval);
        System.arraycopy(mPointY,yInterval,mDynamicPointY,0,mXOffset);
    }
}
