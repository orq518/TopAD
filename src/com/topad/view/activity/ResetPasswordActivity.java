package com.topad.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.topad.R;
import com.topad.amap.ToastUtil;
import com.topad.bean.BaseBean;
import com.topad.bean.RegisterBean;
import com.topad.net.HttpCallback;
import com.topad.net.http.RequestParams;
import com.topad.util.Constants;
import com.topad.util.Md5;
import com.topad.util.Utils;
import com.topad.view.customviews.TitleView;

/**
 * ${todo}<重置密码>
 *
 * @author lht
 * @data: on 15/11/3 09:38
 */
public class ResetPasswordActivity  extends BaseActivity implements View.OnClickListener{
    private static final String LTAG = ResetPasswordActivity.class.getSimpleName();
    /** 上下文 **/
    private Context mContext;
    /** 顶部布局 **/
    private TitleView mTitleView;
    /** 验证码 **/
    private EditText mETSmsCode;
    /** 密码 **/
    private EditText mETPassword;
    /** 确认密码 **/
    private EditText mETConfirmPassword;
    /** 重获验证码 **/
    private TextView mTVCodeAgain;
    /** 确认 **/
    private Button mBTConfirm;

    /** 用户名－－手机号 **/
    private String mUserName;
    /** 密码 **/
    private String mPassword;
    /** 确认密码 **/
    private String mConfirmPassword;
    /** 验证码 **/
    private String mSmsCode;

    @Override
    public int setLayoutById() {
        mContext = this;
        return R.layout.activity_reset_password;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }

    @Override
    public void initViews() {
        mTitleView = (TitleView) findViewById(R.id.title);
        mETSmsCode = (EditText) findViewById(R.id.et_sms_code_resetpw);
        mETPassword = (EditText) findViewById(R.id.et_password_resetpw);
        mETConfirmPassword = (EditText) findViewById(R.id.et_confirm_password_resetpw);
        mTVCodeAgain = (TextView) findViewById(R.id.tv_get_verify_code_again_resetpw);
        mBTConfirm = (Button) findViewById(R.id.btn_confirm);

        mBTConfirm.setOnClickListener(this);
        mTVCodeAgain.setOnClickListener(this);
    }

    @Override
    public void initData() {
        showView();
    }

    /**
     * 显示数据
     */
    private void showView() {
        // 设置顶部标题布局
        mTitleView.setTitle("重设密码");
        mTitleView.setLeftClickListener(new TitleLeftOnClickListener());

        setNextBtnState(false);

        // 设置密码
        mETPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String data = getData(mETPassword);
                if (!Utils.isEmpty(data)) {
                    mPassword = data;
                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                String password = getData(mETPassword);
                String confirmPassword = getData(mETConfirmPassword);
                String smsCode = getData(mETSmsCode);

                if (!Utils.isEmpty(password) && password.length() > 5
                        && !Utils.isEmpty(confirmPassword) && confirmPassword.length() > 5
                        && !Utils.isEmpty(smsCode)) {
                    setNextBtnState(true);
                } else {
                    setNextBtnState(false);
                }
            }
        });

        // 设置确认密码
        mETConfirmPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String data = getData(mETConfirmPassword);
                if (!Utils.isEmpty(data)) {
                    mConfirmPassword = data;
                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                String password = getData(mETPassword);
                String confirmPassword = getData(mETConfirmPassword);
                String smsCode = getData(mETSmsCode);

                if (!Utils.isEmpty(password) && password.length() > 5
                        && !Utils.isEmpty(confirmPassword) && password.length() > 5
                        && !Utils.isEmpty(smsCode)) {
                    setNextBtnState(true);
                } else {
                    setNextBtnState(false);
                }
            }
        });

        // 设置验证码
        mETSmsCode.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String data = getData(mETSmsCode);
                if (!Utils.isEmpty(data)) {
                    mSmsCode = data;
                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                String password = getData(mETPassword);
                String confirmPassword = getData(mETConfirmPassword);
                String smsCode = getData(mETSmsCode);

                if (!Utils.isEmpty(password) && password.length() > 5
                        && !Utils.isEmpty(confirmPassword) && confirmPassword.length() > 5
                        && !Utils.isEmpty(smsCode)) {
                    setNextBtnState(true);
                } else {
                    setNextBtnState(false);
                }
            }
        });
    }

    /**
     * 顶部布局--按钮事件监听
     */
    public class TitleLeftOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            finish();
        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            // 验证码
            case R.id.tv_get_verify_code_again_resetpw:
                if(!Utils.isEmpty(mUserName)){
                    // 拼接url
                    StringBuffer sb = new StringBuffer();
                    sb.append(Constants.getCurrUrl()).append(Constants.URL_GETCODE).append("?");
                    String url = sb.toString();
                    RequestParams rp=new RequestParams();
                    rp.add("mobile", mUserName);

                    postWithLoading(url, rp, false, new HttpCallback() {
                        @Override
                        public <T> void onModel(int respStatusCode, String respErrorMsg, T t) {

                        }

                        @Override
                        public void onFailure(BaseBean base) {
                            int status = base.getStatus();// 状态码
                            String msg = base.getMsg();// 错误信息
                            ToastUtil.show(mContext, "status = " + status + "\n"
                                    + "msg = " + msg);
                        }
                    }, BaseBean.class);
                }else{
                    ToastUtil.show(mContext, "手机号不能为空！");
                }
                break;

            // 确认
            case R.id.btn_confirm:
                if(!mPassword.equals(mConfirmPassword)){
                    ToastUtil.show(mContext, "两次密码输入不一致！");
                }
                else{
                    // 拼接url
                    StringBuffer sb = new StringBuffer();
                    sb.append(Constants.getCurrUrl()).append(Constants.URL_RESETPWD).append("?");
                    String url = sb.toString();
                    RequestParams rp=new RequestParams();
                    rp.add("code", mSmsCode);
                    rp.add("mobile", mUserName);
                    rp.add("pwd", Md5.md5s(mPassword));

                    postWithLoading(url, rp, false, new HttpCallback() {
                        @Override
                        public <T> void onModel(int respStatusCode, String respErrorMsg, T t) {
                            Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(BaseBean base) {
                            int status = base.getStatus();// 状态码
                            String msg = base.getMsg();// 错误信息
                            ToastUtil.show(mContext, "status = " + status + "\n"
                                    + "msg = " + msg);
                        }
                    }, RegisterBean.class);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 去除EditText的空格
     *
     * @param et
     * @return
     */
    public String getData(EditText et) {
        String s = et.getText().toString();
        return s.replaceAll(" ", "");
    }

    /**
     * 设置下一步按钮
     *
     * @param flag
     */
    private void setNextBtnState(boolean flag) {
        if (mBTConfirm == null)
            return;
        mBTConfirm.setEnabled(flag);
        mBTConfirm.setClickable(flag);
    }

}
