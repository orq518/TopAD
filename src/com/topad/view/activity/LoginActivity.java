package com.topad.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.topad.R;
import com.topad.util.Utils;
import com.topad.view.customviews.TitleView;

/**
 * ${todo}<登录页面>
 *
 * @author lht
 * @data: on 15/11/2 16:35
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private static final String LTAG = LoginActivity.class.getSimpleName();
    /** 上下文 **/
    private Context mContext;
    /** 顶部布局 **/
    private TitleView mTitleView;
    /** 用户名 **/
    private EditText mETUserName;
    /** 密码 **/
    private EditText mETPassword;
    /** 登录 **/
    private Button mBTLogin;

    /** 用户名 **/
    private String mUserName;
    /** 密码 **/
    private String mPassword;

    @Override
    public int setLayoutById() {
        mContext = this;
        return R.layout.activity_login;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }

    @Override
    public void initViews() {
        mTitleView = (TitleView) findViewById(R.id.title);
        mETUserName = (EditText) findViewById(R.id.et_username);
        mETPassword = (EditText) findViewById(R.id.et_password);
        mBTLogin = (Button) findViewById(R.id.btn_login);

        mBTLogin.setOnClickListener(this);
        // 设置登录按钮
        setNextBtnState(false);
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
        mTitleView.setTitle("登录");
        mTitleView.setLeftVisiable(false);
        mTitleView.setRightVisiable(false);
        mTitleView.setRightClickListener(new TitleRightOnClickListener(), "注册");

        // 设置手机号
        mETUserName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String data = getData(mETUserName);
                if (!Utils.isEmpty(data)) {
                    mUserName = data;
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

                String phone = getData(mETUserName);
                String password = getData(mETPassword);

                if (!Utils.isEmpty(phone) && phone.length() > 10
                        && !Utils.isEmpty(password) && password.length() > 5) {
                    setNextBtnState(true);
                } else {
                    setNextBtnState(false);
                }

            }
        });

        // 设置手机号
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

                String phone = getData(mETUserName);
                String password = getData(mETPassword);

                if (!Utils.isEmpty(phone) && phone.length() > 10
                        && !Utils.isEmpty(password) && password.length() > 5) {
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
    public class TitleRightOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            // 登录
            case R.id.btn_login:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
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
        if (mBTLogin == null)
            return;
        mBTLogin.setEnabled(flag);
        mBTLogin.setClickable(flag);
    }
}
