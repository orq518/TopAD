package com.topad.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import com.topad.util.SystemBarTintManager;
import com.topad.R;
import com.topad.bean.BaseBean;
import com.topad.net.HttpCallback;
import com.topad.net.HttpUtil;
import com.topad.net.http.LoadingDialogCalback;
import com.topad.net.http.RequestParams;
import com.topad.util.ActivityCollector;
import com.topad.util.Utils;
import com.topad.view.customviews.CircleProgressDialog;

/**
 * orq--kb2
 */

public abstract class BaseActivity extends FragmentActivity implements OnClickListener {
    public BaseBean base;
    public CircleProgressDialog mReadingProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        ActivityCollector.addActivity(this);
        int layoutId = setLayoutById();
        if (layoutId != 0) {
            setContentView(layoutId);
        } else {
            setContentView(setLayoutByView());
        }

        initViews();

        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 通过layout的id
     *
     * @return
     */
    public abstract int setLayoutById();

    /**
     * 通过layout的view
     *
     * @return
     */
    public abstract View setLayoutByView();

    /**
     * 初始化view
     */
    public abstract void initViews();

    /**
     * 初始化数据
     */
    public abstract void initData();

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    /**
     * 处理返回事件
     */
    public void onBack() {
    }


    /**
     * 联网，显示联网对话框
     *
     * @param url         地址
     * @param needEncript 是否加密
     * @param callback    回调对象
     * @param clazz       model类，json字符串映射到model
     * @param <T>
     */
    public <T> void postWithLoading(String url, RequestParams rp, boolean needEncript,
                                    HttpCallback callback, Class<T> clazz) {

        if (!Utils.isNetworkConnected(this)){
            if(base == null){
                base = new BaseBean();
            }
            base.setStatus(-1);
            callback.onFailure(base);

            Utils.showToast(this, getString(R.string.no_connection));
            return ;
        }
        showProgressDialog(true);
        HttpUtil.getInstance().post(this, url, rp, needEncript, callback, clazz, new LoadingDialogCalback() {
            @Override
            public void closeDialog() {
                closeProgressDialog();
            }
        }, false);
    }

    /**
     * 联网，显示联网对话框
     *
     * @param url               地址
     * @param needEncript       是否加密
     * @param callback          回调对象
     * @param clazz             model类，json字符串映射到model
     * @param <T>
     * @param needResultCode    访问失败时，是否返回返回码
     */
    public <T> void postWithLoading( String url, RequestParams rp, boolean needEncript,
                                    HttpCallback callback, Class<T> clazz, boolean needResultCode) {
        if (!Utils.isNetworkConnected(this)){
            if(base == null){
                base = new BaseBean();
            }
            Utils.showToast(this, this.getString(R.string.no_connection));
            base.setStatus(-1);
            callback.onFailure(base);
            return ;
        }
        showProgressDialog(true);
        HttpUtil.getInstance().post(this, url, rp, needEncript, callback, clazz, new LoadingDialogCalback() {
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
    public <T> void postWithoutLoading( String url, RequestParams rp, boolean needEncript, HttpCallback callback, Class<T> clazz) {
        if (!Utils.isNetworkConnected(this)){
            if(base == null){
                base = new BaseBean();
            }
            closeProgressDialog();
            Utils.showToast(this, getString(R.string.no_connection));
            base.setStatus(-1);
            base.setMsg(getString(R.string.no_connection));
            callback.onFailure(base);
            return ;
        }
        HttpUtil.getInstance().post(this, url, rp, needEncript, callback, clazz, new LoadingDialogCalback() {
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
    public void showProgressDialog( String msg, boolean cancelAble) {
        if (mReadingProgress == null || !mReadingProgress.isShowing()) {
            if (mReadingProgress == null) {
                mReadingProgress = new CircleProgressDialog(this, R.style.loading_dialog);
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
    public void showProgressDialog(boolean allowCancel) {
        if (mReadingProgress == null || !mReadingProgress.isShowing()) {
            if (mReadingProgress == null) {
                mReadingProgress = new CircleProgressDialog(this, R.style.loading_dialog);
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
    public void showProgressDialog( String msg) {
        if (mReadingProgress == null || !mReadingProgress.isShowing()) {
            if (mReadingProgress == null) {
                mReadingProgress = new CircleProgressDialog(this, R.style.loading_dialog);
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
    public void showProgressDialog() {
        if (mReadingProgress == null || !mReadingProgress.isShowing()) {
            if (mReadingProgress == null) {
                mReadingProgress = new CircleProgressDialog(this, R.style.loading_dialog);
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
