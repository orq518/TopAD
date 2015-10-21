package com.topad.presenter;

import android.content.Context;

import com.topad.model.SettingMode;
import com.topad.view.interfaces.ISettingView;

/**
 * Created by sunfeng on 2015/9/24.
 * <p>
 * File for what: 设置页面
 * <p>
 * ps:
 */
public class SettingPresenter implements NetCallBack {

    private Context ctx;
    private SettingMode mMode;
    private ISettingView mView;


    public SettingPresenter(Context ctx,ISettingView mView){
        this.ctx = ctx;
        this.mView = mView;
        mMode = new SettingMode();

    }

    public void getData(String token){
        mMode.getNetData(ctx,token,this);
    }

    @Override
    public <T> void onSuccess(T t) {


        mView.showSuccess(t);
    }

    @Override
    public <T> void onFail(T t) {
            mView.showFail(t);
    }
}
