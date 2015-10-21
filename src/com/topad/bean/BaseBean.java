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
    protected String respStatusCode;
    /**
     * error信息
     */
    protected String respErrorMsg;
    /**
     * 数据
     */
    protected String respData;

    public void setRespData(String respData) {
        this.respData = respData;
    }

    public void setRespStatusCode(String respStatusCode) {
        this.respStatusCode = respStatusCode;
    }

    public void setRespErrorMsg(String respErrorMsg) {
        this.respErrorMsg = respErrorMsg;
    }

    public String getRespStatusCode() {
        return respStatusCode;
    }

    public String getRespData() {
        return respData;
    }

    public String getRespErrorMsg() {
        return respErrorMsg;
    }
}
