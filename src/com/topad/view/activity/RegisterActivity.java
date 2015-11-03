package com.topad.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.topad.R;
import com.topad.view.customviews.TitleView;

/**
 * ${todo}<注册页>
 *
 * @author lht
 * @data: on 15/11/2 18:06
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private static final String LTAG = LoginActivity.class.getSimpleName();
    /** 上下文 **/
    private Context mContext;
    /** 顶部布局 **/
    private TitleView mTitleView;

    /** 用户名 **/
    private EditText mETUsername;
    /** 密码 **/
    private EditText mETPassword;
    /** 确认密码 **/
    private EditText mETConfirmPassword;
    /** 验证码 **/
    private EditText mETSmsCode;
    /** 重获验证码 **/
    private TextView mTVCodeAgain;
    /** 注册 **/
    private Button mBTRegister;

    @Override
    public int setLayoutById() {
        mContext = this;
        return R.layout.activity_register;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }

    @Override
    public void initViews() {
        mTitleView = (TitleView) findViewById(R.id.title);
        mETUsername = (EditText) findViewById(R.id.et_username_register);
        mETPassword = (EditText) findViewById(R.id.et_password_register);
        mETConfirmPassword = (EditText) findViewById(R.id.et_confirm_password_register);
        mETSmsCode = (EditText) findViewById(R.id.et_sms_code);
        mTVCodeAgain = (TextView) findViewById(R.id.tv_get_verify_code_again);
        mBTRegister = (Button) findViewById(R.id.btn_register);

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
        mTitleView.setTitle("注册");
        mTitleView.setLeftVisiable(false);
        mTitleView.setRightVisiable(false);
        mTitleView.setRightClickListener(new TitleRightOnClickListener(), "登录");
    }

    /**
     * 顶部布局--按钮事件监听
     */
    public class TitleRightOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // 登录
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            // 注册
            case R.id.btn_register:

                break;
            default:
                break;
        }
    }
}
