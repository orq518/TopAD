package com.topad.net;

import android.content.Context;
import android.text.TextUtils;

import com.topad.R;
import com.topad.bean.BaseBean;
import com.topad.net.http.AsyncHttpClient;
import com.topad.net.http.JsonHttpResponseHandler;
import com.topad.net.http.LoadingDialogCalback;
import com.topad.net.http.RequestParams;
import com.topad.util.AES;
import com.topad.util.Constants;
import com.topad.util.ErrorCode;
import com.topad.util.JSONUtils;
import com.topad.util.LogUtil;
import com.topad.util.P2PAesCryptos;
import com.topad.util.RSATools;
import com.topad.util.Utils;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HttpUtil {

    private static HttpUtil mUtil;
    //    private static final String TAG = "HttpUtil";
    public static AsyncHttpClient mClient = new AsyncHttpClient();
//	public final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCk1OIBhm7gtG/CQGmvy92ol4Xi15ywx8NLDFgb3jdlVSvaZNpET1a6TivpE+ld4YUPDX1x8Khq2hO5GFDSYu4qTfI1HCk02pszguJ8/tz4cN8QYGM6dT/J5DTEIRjkDlOMo/EI5lYfB1C1v+EBC6pPXMUmGc2nWhbZ0W4ySJGMBQIDAQAB";

    // server.jsp old key
    // public final String
    // PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/umqos8HU4WaH31WLdeg79zfLFXHVtZWHqW0Vf5Q+3Oq2Z6pZXjhGFnTItFd/tjcfEmZtGla1a59+B6XmxkcyYQY2e5Pwn6B2sfhoMmeSyd+1CWrC+q/H6wxwOJYd17Cr2xz7Fi2/9I4/vnRWLow2L4tVsE1mMase7Ec9V89QfQIDAQAB";

    private HttpUtil() {

    }

    public static HttpUtil getInstance() {
        if (mUtil == null)
            mUtil = new HttpUtil();

        return mUtil;
    }

    public AsyncHttpClient getHttpClient() {
        return HttpUtil.mClient;
    }

    /**
     * get请求使用方法
     *
     * @param @param url 请求地址
     * @return void
     * @Description:get请求
     */
    public void getJsonData(String url) {
        LogUtil.d("-----------------get----------------");
        mClient.get(url, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject obj) {
                LogUtil.d("statusCode->" + statusCode);
                for (int i = 0; i < headers.length; i++) {
                    Header h = headers[i];
                    LogUtil.d("get:" + h.getName() + ":" + h.getValue());
                }
                LogUtil.d("get:response json--->" + obj.toString());
            }
        });
        LogUtil.d("-----------------get end ----------------");
    }

    private List<NameValuePair> getListMap(String value) {
        if (value != null && value.length() > 0) {
            String[] s_map = value.split("&");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (int i = 0; i < s_map.length; i++) {
                int index = s_map[i].indexOf("=");
                if (index > 0 && index < s_map[i].length() - 1) {
                    NameValuePair nvp = new BasicNameValuePair(
                            s_map[i].substring(0, index),
                            s_map[i].substring(index + 1));

                    list.add(nvp);
                }
            }
            return list;
        } else {
            return null;
        }
    }
    /**
     * @param ctx
     * @param url
     * @param rp
     * @param needEncript
     * @param callback
     * @param clazz
     * @param loadingDialogCallback
     * @param needResultCode
     * @param <T>
     * @return
     */
    public <T> boolean post(final Context ctx, String url, RequestParams rp,
                            final boolean needEncript, final HttpCallback callback,
                            final Class<T> clazz,
                            final LoadingDialogCalback loadingDialogCallback,
                            final boolean needResultCode) {

//        rp.add("t_channel", Constants.T_CHANNEL);

        LogUtil.d("-------------post--------------------");

        LogUtil.d("url->" + url);
        LogUtil.d("rp.toString()->" + rp.toString());

        int index = url.indexOf("?");
        String uri = "";
        String data = rp.toString();
        String tm = "";
        String key = "";
        if (index > -1) {
            uri = url.substring(0, index);
        }
        long aes_begin = System.currentTimeMillis();
        LogUtil.d("###加密前data:" + data);

        if (needEncript) {
            String[] ret = AES.aesEncoode(data);
            if (ret.length > 1) {
                key = ret[1];
                data = ret[2];
                try {
                    LogUtil.d("key:"+key);
                    tm = RSATools.encryptByPublic(key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            rp.clear();
            rp.add("tm", tm);
            rp.add("data", data);

            LogUtil.d("###tm:" + tm);
            LogUtil.d("###加密后data:" + data);

        }
//        rp.add("ver", Utils.softVersion(ctx));
        LogUtil.d("###地址参数->：" + rp.toString());

        final long aes_end = System.currentTimeMillis();
        LogUtil.d("post elapse", "aes elapse time is: " + (aes_end - aes_begin));
        final String tmpKey = key;
        mClient.post(ctx, uri, rp, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, String obj) {
                if (!Utils.isEmpty(obj)) {
                    String decodedStr = obj;
                    LogUtil.d("返回数据received data->" + obj);
                    long success = System.currentTimeMillis();
                    LogUtil.d("post elapse", "post success time is: " + (success - aes_end));

                    try {
                        if (needEncript) {
                            // aes解密
//                            decodedStr = aesDecode(tmpKey, decodedStr);
//                            decodedStr=MCrypt.decrypt(tmpKey, decodedStr);
                            decodedStr= AES.AES_Decrypt(tmpKey, decodedStr);
                            LogUtil.d("decodedStr->" + decodedStr);
                        }
                    } catch (Exception e) {
                        LogUtil.d("decode failed");
                        loadingDialogCallback.closeDialog();
                    }

                    long decode = System.currentTimeMillis();
                    LogUtil.d("post elapse", "decode time is: " + (decode - success));

                    // 解析错误码请求
                    int status = 0;// 状态码
                    String msg = null;// 错误信息

                    try {
                        final JSONObject respObj = new JSONObject(decodedStr);
                        status = respObj.optInt("status");// 状态码
                        msg = respObj.optString("msg");// 错误信息

                        T model = JSONUtils.fromJson(decodedStr, clazz);

                        // Json解析时出错
                        if (null == model) {
                            BaseBean b = new BaseBean();
                            b.setStatus(-1);
                            b.setMsg("数据解析错误");
                            callback.onFailure(b);
                        }

                        // 成功
                        if (status == 10000) {
                            loadingDialogCallback.closeDialog();
                            callback.onModel(status, msg, model);
                        }
                        // 失败
                        else {
                            loadingDialogCallback.closeDialog();

                            String finalRespErrorMsg = msg;
                            if (Utils.isEmpty(msg)) {
                                finalRespErrorMsg = "未知错误";
                            }

                            final String finalRespErrorMsg1 = finalRespErrorMsg;
                            final BaseBean b = new BaseBean();
                            b.setStatus(status);
                            b.setMsg(finalRespErrorMsg1);

                            // 失败时，返回code码
                            if (needResultCode) {
                                loadingDialogCallback.closeDialog();
                                callback.onFailure(b);
                            }
                            // 不返回code，弹出统一对话框
                            else {
                                loadingDialogCallback.closeDialog();
                                callback.onFailure(b);
                                Utils.showToast(ctx, msg);
                            }
                        }
                        long json_time = System.currentTimeMillis();
                        LogUtil.d("post elapse", "create json time is: " + (json_time - decode));
                    } catch (Exception e) {
                        loadingDialogCallback.closeDialog();
                    }

                } else {
                    LogUtil.d("obj is null or empty!");
                }

            }

            public void onFailure(final int statusCode, Header[] headers,
                                  final String responseString, Throwable throwable) {
                LogUtil.d("failed code->" + statusCode);
                LogUtil.d("responseString->" + responseString);
                String rString = "";
                if (Utils.isEmpty(responseString)) {
                    rString = ctx.getString(R.string.service_busy);
                } else {
                    rString = responseString;
                }
                if (Utils.isEmpty(rString)) {
                    rString = "未知错误";
                }
                final String tmpStr = rString;
                BaseBean b = new BaseBean();
                b.setStatus(statusCode);
                b.setMsg(tmpStr);
                loadingDialogCallback.closeDialog();
                callback.onFailure(b);
                Utils.showToast(ctx, tmpStr);
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject obj) {
                LogUtil.d("ERR_SERVER_ERROR failed code->" + statusCode);
                if (ctx != null) {
                    String msg = ctx.getString(R.string.connect_error);
                    BaseBean b = new BaseBean();
                    b.setStatus(ErrorCode.ERR_SERVER_ERROR);
                    b.setMsg(ctx.getString(R.string.connect_error));
                    loadingDialogCallback.closeDialog();
                    callback.onFailure(b);
                    Utils.showToast(ctx, msg);
                }
            }

            public void onTimeout() {
                if (ctx != null) {
                    String msg = ctx.getString(R.string.connect_timeout);
                    BaseBean b = new BaseBean();
                    b.setStatus(ErrorCode.ERR_HTTP_TIMEOUT);
                    b.setMsg(ctx.getString(R.string.connect_timeout));
                    loadingDialogCallback.closeDialog();
                    callback.onFailure(b);
                    Utils.showToast(ctx, msg);
                }
            }
        });
        LogUtil.d("------------- post end --------------------");
        return true;
    }

    private String aesEncodeForOnline(String str) {
        return P2PAesCryptos.aesEncrypt(str);
    }

    private String aesDecodeForOnline(String encodedStr) {
        return P2PAesCryptos.aesDecrypt(encodedStr);
    }

    /**
     * @return void
     * @Description: 取消http请求
     */
    public void cancel() {
        mClient.cancelAllRequests(true);
    }



    public static void setContentType(String type) {
        mClient.setContentType(type);
    }

}