package com.topad.util;

import android.util.Log;

public class LogUtil {

	public static final boolean showDebug = true;

	private static final String TAG = "TopAD";


	/**
	 * 
	 * Description: 打印默认TAG的日志
	 * 
	 * @param msg
	 *            void
	 */
	public static void d(String msg) {
		if (showDebug) {
			d(TAG, msg);
		}
	}

	public static void d(String tag, String  msg) {
		if (showDebug) {
			Log.d(tag, msg);
		}
	}

}
