package com.topad.bean;

import java.io.Serializable;

/**
 * ${todo}<登录实体>
 *
 * @author lht
 * @data: on 15/10/26 11:06
 */
public class LoginBean implements Serializable {
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 4768927122317982665L;
	/** token **/
	public String token;
	/** 状态码 **/
	protected int status;
	/** error信息 **/
	protected String msg;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}