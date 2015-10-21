package com.topad.model;

import android.content.Context;
import android.content.DialogInterface;

import com.topad.R;
import com.topad.bean.BaseBean;
import com.topad.net.HttpCallback;
import com.topad.net.HttpUtil;
import com.topad.net.http.LoadingDialogCalback;
import com.topad.net.http.RequestParams;
import com.topad.util.Utils;
import com.topad.view.customviews.CircleProgressDialog;

/**
 * The author 欧瑞强 on 2015/7/23.
 *      联网失败，返回－1
 *
 * todo
 */
public abstract class BaseModel {
    public BaseBean base;
    public CircleProgressDialog mReadingProgress;

    /**
     * 联网，显示联网对话框
     *
     * @param context     上下文
     * @param url         地址
     * @param needEncript 是否加密
     * @param callback    回调对象
     * @param clazz       model类，json字符串映射到model
     * @param <T>
     */
    public <T> void postWithLoading(Context context, String url, RequestParams rp, boolean needEncript,
                                    HttpCallback callback, Class<T> clazz) {

        if (!Utils.isNetworkConnected(context)){
            if(base == null){
                base = new BaseBean();
            }
            base.setRespStatusCode("-1");
            callback.onFailure(base);

            Utils.showToast(context, context.getString(R.string.no_connection));
            return ;
        }
        showProgressDialog(context, true);
        HttpUtil.getInstance().post(context, url, rp, needEncript, callback, clazz, new LoadingDialogCalback() {
            @Override
            public void closeDialog() {
                closeProgressDialog();
            }
        }, false);
    }

    /**
     * 联网，显示联网对话框
     *
     * @param context           上下文
     * @param url               地址
     * @param needEncript       是否加密
     * @param callback          回调对象
     * @param clazz             model类，json字符串映射到model
     * @param <T>
     * @param needResultCode    访问失败时，是否返回返回码
     */
    public <T> void postWithLoading(Context context, String url, RequestParams rp, boolean needEncript,
                                    HttpCallback callback, Class<T> clazz, boolean needResultCode) {
        if (!Utils.isNetworkConnected(context)){
            if(base == null){
                base = new BaseBean();
            }
            Utils.showToast(context, context.getString(R.string.no_connection));
            base.setRespStatusCode("-1");
            callback.onFailure(base);
            return ;
        }
        showProgressDialog(context, true);
        HttpUtil.getInstance().post(context, url, rp, needEncript, callback, clazz, new LoadingDialogCalback() {
            @Override
            public void closeDialog() {
                closeProgressDialog();
            }
        }, true);
    }

    /**
     * 不弹出联网对话框
     *
     * @param @param url            请求Url,参数拼接在url后面
     * @param @param needEncript    是否需要加密，true：需要；false:不需要
     * @param @param callback       回调接口
     * @param @param clazz          model类，json字符串映射到model
     * @return void
     */
    public <T> void postWithoutLoading(Context context, String url, RequestParams rp, boolean needEncript, HttpCallback callback, Class<T> clazz) {
        if (!Utils.isNetworkConnected(context)){
            if(base == null){
                base = new BaseBean();
            }
            closeProgressDialog();
            Utils.showToast(context, context.getString(R.string.no_connection));
            base.setRespStatusCode("-1");
            base.setRespErrorMsg(context.getString(R.string.no_connection));
            callback.onFailure(base);
            return ;
        }
        HttpUtil.getInstance().post(context, url, rp, needEncript, callback, clazz, new LoadingDialogCalback() {
            @Override
            public void closeDialog() {

            }
        }, true);
    }

    /**
     * @param @param msg
     * @param @param cancelAble
     *               true:could cancel,false:can't cancel
     * @return void
     * @Description: 可以取消的loading对话框
     */
    public void showProgressDialog(Context context, String msg, boolean cancelAble) {
        if (mReadingProgress == null || !mReadingProgress.isShowing()) {
            if (mReadingProgress == null) {
                mReadingProgress = new CircleProgressDialog(context, R.style.loading_dialog);
            }
            mReadingProgress.setCancelable(cancelAble);
            mReadingProgress.setMessage(msg);
            mReadingProgress.show();
        }
    }

    /**
     * 可取消的进度条
     *
     * @param allowCancel true:could cancel,false:can't cancel
     */
    public void showProgressDialog(Context context, boolean allowCancel) {
        if (mReadingProgress == null || !mReadingProgress.isShowing()) {
            if (mReadingProgress == null) {
                mReadingProgress = new CircleProgressDialog(context, R.style.loading_dialog);
            }
            mReadingProgress.setCancelable(allowCancel);
            mReadingProgress.show();
            if (allowCancel) {
                mReadingProgress.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        cancel();
                    }
                });
            }
        }
    }

    /**
     * @param @param msg    loading文案
     * @return void
     * @Description: 显示loading对话框
     */
    public void showProgressDialog(Context context, String msg) {
        if (mReadingProgress == null || !mReadingProgress.isShowing()) {
            if (mReadingProgress == null) {
                mReadingProgress = new CircleProgressDialog(context, R.style.loading_dialog);
            }
            mReadingProgress.setMessage(msg);
            mReadingProgress.setCancelable(false);
            mReadingProgress.show();
        }
    }

    /**
     * @param
     * @return void
     * @Description: 显示loading对话框，使用默认文案
     */
    public void showProgressDialog(Context context) {
        if (mReadingProgress == null || !mReadingProgress.isShowing()) {
            if (mReadingProgress == null) {
                mReadingProgress = new CircleProgressDialog(context, R.style.loading_dialog);
            }
            mReadingProgress.setCancelable(false);
            mReadingProgress.show();
        }
    }

    /**
     * 关闭loading对话框
     *
     * @param
     * @return void
     * @Description:
     */
    public void closeProgressDialog() {
        if (mReadingProgress != null) {
            mReadingProgress.dismiss();
            mReadingProgress = null;
        }
    }

    /**
     * @param
     * @return void
     * @Description: 发送取消http请求
     */
    protected void cancel() {
        HttpUtil.getInstance().cancel();
    }

}
