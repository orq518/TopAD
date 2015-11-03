package com.topad.view.activity;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.topad.R;
import com.topad.view.customviews.TitleView;

/**
 * ${todo}<重置密码>
 *
 * @author lht
 * @data: on 15/11/3 09:38
 */
public class ResetPasswordActivity  extends BaseActivity implements View.OnClickListener{
    private static final String LTAG = LoginActivity.class.getSimpleName();
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
            // 确认
            case R.id.btn_confirm:

                break;
            default:
                break;
        }
    }
}
