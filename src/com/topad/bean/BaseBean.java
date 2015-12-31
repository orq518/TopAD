package com.topad.bean;

import java.io.Serializable;

/**
 * 联网返回的要继承这个类  方便gson解析
 */
public class BaseBean implements Serializable {

    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     */
    private static final long serialVersionUID = 3778927128017982665L;

    /**
     * 状态码
     */
    protected int status;
    /**
     * error信息
     */
    protected String msg;

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
