package com.topad.view.customviews;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 
 * 
 * <PRE>
 *  类名：UcfToast
 *  
 * 类简介：
 * 			自定义Toast
 *  作者：dengsujun(avin)
 *  
 *  创建时间：2014年6月24日 下午2:12:08 
 * 
 * </PRE>
 */
public class UcfToast {

	public static final int ICON_NONE = -1; // 无图标
	public static final int ICON_DEFAULT = 0; // 默认图标,蓝色
	public static final int ICON_ERROR = 1; // 错误图标
	public static final int ICON_SUCCESS = 2; // 成功图标

	private static Context mContext;
	private Resources mResources;
	private LayoutInflater mInflater;

	private Drawable icon = null;
	private CharSequence message = null;
	private int mDuration = Toast.LENGTH_SHORT;

	public UcfToast(Context context) {
		mContext = context.getApplicationContext();
		mResources = context.getResources();
		mInflater = LayoutInflater.from(context);
	}

	/**
	 * 设置toast显示的图标
	 * 
	 * @param iconDrawable
	 */
	public void setToastIcon(Drawable iconDrawable) {
		icon = iconDrawable;
	}

	/**
	 * 设置toast显示的图标
	 * 
	 */
	public void setToastIcon(int iconDrawableResId) {
		setToastIcon(mResources.getDrawable(iconDrawableResId));
	}

	/**
	 * 设置Toast显示的文字.
	 * 
	 * @param msg
	 */
	public void setToastMsg(CharSequence msg) {
		message = msg;
	}

	/**
	 * 设置Toast显示的文字.
	 * 
	 */
	public void setToastMsg(int msgResId) {
		setToastMsg(mResources.getString(msgResId));
	}

	/**
	 * 设置显示时间 Toast.LENGTH_LONG和Toast.LENGTH_SHORT
	 * 
	 * @param dura
	 */
	public void setDuration(int dura) {
		mDuration = dura;
	}

	/**
	 * 创建Toast对象
	 */
	public Toast create(int yOffset) {
		Toast toast = new Toast(mContext.getApplicationContext());

		TextView view = new TextView(mContext);

			view.setText(message);
		toast.setGravity(Gravity.CENTER, 0, yOffset);
		toast.setView(view);
		toast.setDuration(mDuration);
		return toast;
	}

	/**
	 * 显示这个PadQQToast 默认显示在系统tilte顶部，可调用此方法
	 */
	public void show() {
		int y = (int)(-1*50*mContext.getResources().getDisplayMetrics().density);
		create(y).show();
	}

	/**
	 * 显示Toast
	 * 
	 * @param yOffset
	 *            y轴偏移量 如果现在IphoneTitleBar底部，获取titleBar设置yOffset
	 */
	public Toast show(int yOffset) {
		Toast t = create(yOffset);
		t.show();
		return t;
	}

	/**
	 * 配置一个PadQQToast
	 * 
	 * @param context
	 * @param iconType
	 *            文字左边的图标类型,可能的值为ICON_DEFAULT,ICON_ERROR,ICON_SUCCESS
	 * @param msg
	 * @param duration
	 * @return
	 */
	public static UcfToast makeText(Context context, int iconType,
			CharSequence msg, int duration) {
		UcfToast qqToast = new UcfToast(context);
//		qqToast.setToastIcon(getIconRes(iconType));
		qqToast.setToastMsg(msg);
		qqToast.setDuration(duration);
		return qqToast;
	}

	/**
	 * 配置一个PadQQToast
	 * 
	 * @param context
	 * @param iconType
	 *            文字左边的图标类型,可能的值为ICON_DEFAULT,ICON_ERROR,ICON_SUCCESS
	 * @param msgResId
	 * @param duration
	 * @return
	 */
	public static UcfToast makeText(Context context, int iconType, int msgResId,
			int duration) {
		UcfToast qqToast = new UcfToast(context);
//		qqToast.setToastIcon(getIconRes(iconType));
		qqToast.setToastMsg(msgResId);
		qqToast.setDuration(duration);
		return qqToast;
	}

	/**
	 * 配置一个PadQQToast(默认图标)
	 * 
	 * @param context
	 * @param msg
	 * @param duration
	 * @return
	 */
	public static UcfToast makeText(Context context, CharSequence msg,
			int duration) {
		return makeText(context, ICON_DEFAULT, msg, duration);
	}
	
	public static UcfToast makeText(Context context,CharSequence msg){
		return makeText(context, msg, Toast.LENGTH_SHORT);
	}

	/**
	 * 配置一个PadQQToast(默认图标)
	 * 
	 * @param context
	 * @param msgResId
	 * @param duration
	 * @return
	 */
	public static UcfToast makeText(Context context, int msgResId, int duration) {
		return makeText(context, ICON_DEFAULT, msgResId, duration);
	}
}
