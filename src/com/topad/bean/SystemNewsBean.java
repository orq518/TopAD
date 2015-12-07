package com.topad.bean;

import java.io.Serializable;

/**
 * ${todo}<系统消息实体>
 *
 * @author lht
 * @data: on 15/10/26 11:06
 */
public class SystemNewsBean implements Serializable {
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     */
    private static final long serialVersionUID = 4768927122317982665L;
    public String icon;
    public String content;
    public String time;
}
