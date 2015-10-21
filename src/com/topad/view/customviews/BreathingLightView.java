package com.topad.view.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.topad.R;
import com.topad.util.LogUtil;

/**
 * Created by sunfeng on 2015/7/30.
 * <p/>
 * File for what:
 * <p/>
 * ps:
 */
public class BreathingLightView extends View {

    private float mMinRadius;//,mMaxRadius;//最小半径,最大半径
    private Integer mExpandTime,mShrinkTime;//放大持续时间，缩小持续时间

    private int mInnerColor,mAnnuleColor,mBgColor;//内部颜色，外部圆环颜色,view背景色


    private Paint mInnerPaint,mAnnulePaint,mBgPaint;
    private int mCenter ;
    private int mRingWidth,mRingMaxWidth;//圆环的宽度,圆环的最大款第
    private int mRefreshTime = 200;//刷新时间间隔

    private int mState = 1;//1:放大，-1:缩小
    /**eg:mExpandTime = 5000,mShrinkTime = 3000;//5秒放大，3秒缩小
     * 则：mExpandRefreshAmount = 5000/200 = 25，
     * mShrinkRefrsshAmount = 3000/200 = 15
     *
     * mExpandWidth = (mMaxRadius-mMinRadius)/25
     * mShrinkWidth = (mMaxRadius-mMinRadius)/15
     *
     */
    private int mExpandRefreshAmount,mShrinkRefrsshAmount;//放大刷新次数，缩小刷新次数
    private int mExpandWidth,mShrinkWidth;//放大/缩小一次的数值
    private int mExpandRemainder,mShrinkRemaider;//得到放大刷新次数的余数，缩小刷新次数的余数

    public BreathingLightView(Context context) {
        super(context);
    }

    public BreathingLightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public BreathingLightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    private void init(Context context,AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BreathingLightView);
        mMinRadius = typedArray.getDimension(R.styleable.BreathingLightView_minRadius, 0);
//        mMaxRadius = typedArray.getDimension(R.styleable.BreathingLightView_maxRadius, 70);

        mRingMaxWidth = (int)typedArray.getDimension(R.styleable.BreathingLightView_ringWidth,mMinRadius);

        mMinRadius = dip2px(context, mMinRadius);
        mRingMaxWidth = dip2px(context,mRingMaxWidth);
//        mMaxRadius = dip2px(context, mMaxRadius);

        mExpandTime = typedArray.getInteger(R.styleable.BreathingLightView_expandTime, 5000);
        mShrinkTime = typedArray.getInteger(R.styleable.BreathingLightView_shrinkTime, 3000);

        mInnerColor = typedArray.getColor(R.styleable.BreathingLightView_innerColor, Color.BLUE);
        mAnnuleColor = typedArray.getColor(R.styleable.BreathingLightView_annuleColor, Color.DKGRAY);
        mBgColor = typedArray.getColor(R.styleable.BreathingLightView_bgColor, Color.TRANSPARENT);

        typedArray.recycle();

        mExpandRefreshAmount = mExpandTime/mRefreshTime;
        mShrinkRefrsshAmount = mShrinkTime/mRefreshTime;

//        mRingMaxWidth = (int)(mMaxRadius-mMinRadius);
        mExpandWidth =   mRingMaxWidth/mExpandRefreshAmount;
        mShrinkWidth =    mRingMaxWidth/mShrinkRefrsshAmount;

        mExpandRemainder =  mRingMaxWidth%mExpandRefreshAmount;
        mShrinkRemaider =    mRingMaxWidth%mShrinkRefrsshAmount;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int)(2*(mMinRadius+mRingMaxWidth)),(int)(2*(mMinRadius+mRingMaxWidth)));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(null == mInnerPaint){
            mInnerPaint = new Paint();
            mCenter = getWidth()/2;
            mInnerPaint = new Paint();

            mInnerPaint.setColor(mInnerColor);
            mInnerPaint.setStrokeWidth(2);
            mInnerPaint.setAntiAlias(true);
            mInnerPaint.setFilterBitmap(true);
        }

        if(null == mBgPaint){
             mBgPaint = new Paint();
            mBgPaint.setColor(mBgColor);

        }


//
        if(null == mAnnulePaint){
            mAnnulePaint = new Paint();
            mAnnulePaint.setColor(mAnnuleColor);
            mAnnulePaint.setStrokeWidth(3);
            mAnnulePaint.setAntiAlias(true);
            mAnnulePaint.setFilterBitmap(true);
        }
        /////绘制圆环
        canvas.drawRect(0, 0, 2*(mMinRadius+mRingMaxWidth),2*(mMinRadius+mRingMaxWidth), mBgPaint);
        canvas.drawCircle(mCenter,mCenter,mMinRadius+getRingWidth(),mAnnulePaint);
        canvas.drawCircle(mCenter,mCenter,mMinRadius,mInnerPaint);

        super.onDraw(canvas);
        postInvalidateDelayed(mRefreshTime);
    }


    private int getRingWidth(){

        if(mState > 0){//放大过程

            if(mRingWidth + mExpandRemainder != mRingMaxWidth){

                    mRingWidth += mExpandWidth;
            }else{//放大到最大,改为缩小

                   mRingWidth = mRingMaxWidth;
                    mState = -1;
//                    mRingWidth -= mShrinkWidth;
            }
            LogUtil.d("放大====="+mRingWidth);
        }else{//缩小过程

            if(mRingWidth - mShrinkRemaider != 0){
                mRingWidth -= mShrinkWidth;
            }else{//缩小到最小，改为放大
                mRingWidth =0;
                mState = 1;
//                mRingWidth += mExpandWidth;
            }
            LogUtil.d("缩小====="+mRingWidth);
        }



        return mRingWidth;
    }



    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (dpValue * scale + 0.5f);
        return (int)dpValue;
    }
}
