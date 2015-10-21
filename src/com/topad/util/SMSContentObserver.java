package com.topad.util;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;

/**
 * <PRE>
 * 简介:
 *     监听短信，截取验证码回传
 * 
 * 创建时间: 2013-11-26
 * </PRE>
 * 
 * @author erikchen
 */
public class SMSContentObserver extends ContentObserver {

	private static final String KEY_1 = "网信金融";
	private static final String KEY_2 = "验证码";

	protected Handler mHandler;
	protected Context mContext;

	public SMSContentObserver(Handler handler) {
		super(handler);
		mHandler = handler;
	}

	public SMSContentObserver(Handler handler, Context ctx) {
		super(handler);
		mHandler = handler;
		mContext = ctx.getApplicationContext();
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		if (mContext != null && mHandler != null) {
			final Uri uri = Uri.parse("content://sms/inbox");
			final String[] projection = { "_id", "body" };
			Cursor c = null;
			try {
				c = mContext.getContentResolver().query(uri, projection,
						null, null, "date desc");

				if (c != null) {
					if (c.moveToNext()) {
						String smsBody = c.getString(c.getColumnIndex("body"));
						if (!TextUtils.isEmpty(smsBody)) {
							// Todo: 正式上线前可加上同时检验KEY_1
							if (smsBody.contains(KEY_2)) {
								String verifyCode = Utils
										.getVerifyCode(smsBody);
								if (!TextUtils.isEmpty(verifyCode)) {
									mHandler.obtainMessage(0, verifyCode)
											.sendToTarget();
								}
							}
						}
					}					
				}
			} catch (Exception e) {
				// do nothing
			} finally {
				if ( c != null){
					try {
						c.close();
					} catch (Exception e){					
					}
				}
			}
		}
	}
}
