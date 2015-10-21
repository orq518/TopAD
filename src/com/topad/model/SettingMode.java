package com.topad.model;

import android.content.Context;

import com.topad.bean.BaseBean;
import com.topad.net.HttpCallback;
import com.topad.net.http.RequestParams;
import com.topad.util.Constants;

/**
 * Created by sunfeng on 2015/9/24.
 * <p>
 * File for what: 设置页面mode
 *
 * <p>
 * ps: 因为没有此页面的单独接口，现在请求的是用户信息的接口
 */
public class SettingMode extends BaseModel {

    public SettingMode() {
    }

    /**
     * @param context
     * @param callBack
     */
    public void getNetData(final Context context, String token, final NetCallBack callBack) {
        // 拼接url
        StringBuffer sb = new StringBuffer();
        sb.append(Constants.getCurrUrl()).append(Constants.URL_WEALTH).append("?");
        String url = sb.toString();
        RequestParams rp = new RequestParams();
        rp.add("token", token);

        postWithLoading(context, url, rp, true, new HttpCallback() {

            @Override
            public <T> void onModel(String respStatusCode, String respErrorMsg, T t) {


                callBack.onSuccess(t);
            }

            @Override
            public void onFailure(BaseBean base) {
                callBack.onFail(base);
            }

        }, BaseBean.class, true);
    }
}
