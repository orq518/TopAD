package com.topad.view.customviews.clip;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
/**
 * ${todo}<头像裁剪页面>
 *
 * @author lht
 * @data: on 15/9/24 16:22
 */
public class ClipImageBorderView extends View {
	/**
	 * 水平方向与View的边距
	 */
	private int mHorizontalPadding;
	/**
	 * 边框的宽度 单位dp
	 */
	private int mBorderWidth = 2;

	private Paint mPaint;

	public ClipImageBorderView(Context context)
	{
		this(context, null);
	}

	public ClipImageBorderView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public ClipImageBorderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mBorderWidth = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, mBorderWidth, getResources()
						.getDisplayMetrics());
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 绘制边框
		mPaint.setColor(Color.parseColor("#FFFFFF"));
		mPaint.setStrokeWidth(mBorderWidth);
		mPaint.setStyle(Style.STROKE);
		//方形边框
//		canvas.drawRect(mHorizontalPadding, mVerticalPadding, getWidth()- mHorizontalPadding, getHeight() - mVerticalPadding, mPaint);
		//圆形边框
//		canvas.drawCircle( getWidth()/2, getHeight()/2, getWidth()/2-mHorizontalPadding, mPaint);

		Path path = new Path();
		path.addCircle(getWidth()/2, getHeight()/2, getWidth()/2 - mHorizontalPadding, Path.Direction.CCW);
		canvas.clipPath(path, Region.Op.DIFFERENCE);
		canvas.drawColor(0x55222222);

		// 虚线
//		DashPathEffect dashStyle = new DashPathEffect(new float[] { 10,5, 5, 5 }, 2);
		Paint mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.STROKE);
		mPaint.setColor(0xFFFFFFFF);
		mPaint.setStrokeWidth(6);
//		mPaint.setPathEffect(dashStyle);
		canvas.drawPath(path, mPaint);

	}

	public void setHorizontalPadding(int mHorizontalPadding) {
		this.mHorizontalPadding = mHorizontalPadding;

	}

}
