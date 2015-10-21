package com.topad.model;

/**
 * The author 欧瑞强 on 2015/7/23.
 * todo
 */
public interface NetCallBack {
    /**
     * 成功时回调
     */
   <T> void onSuccess(T t);
    /**
     * 失败时回调
     */
    <T> void onFail(T t);
}
