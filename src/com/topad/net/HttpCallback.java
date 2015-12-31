package com.topad.net;

import com.topad.bean.BaseBean;

import org.json.JSONObject;


public abstract class HttpCallback {

	/**
	 *
	 * @param respStatusCode	接口返回码
	 * @param respErrorMsg		错误信息
	 * @param t    				模板model类
	 * @param <T>
	 */
	public abstract <T> void onModel(int respStatusCode, String respErrorMsg, T t);

	/**
	 * 
	 * @Description: 联网失败返回 
	 * @param @param code    错误码
	 * @return void
	 */
	public abstract void onFailure(final BaseBean base);
	
	public  void onJson(JSONObject obj){
		
	}
	
}
