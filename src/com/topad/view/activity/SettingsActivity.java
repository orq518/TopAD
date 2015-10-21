/*
package com.ucfwallet.view.activity;


import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ucfwallet.view.customviews.SlideSwitch;
import com.ucfwallet.view.customviews.WalletTitleView;

*/
/**
 * ${todo}<设置页面>
 *
 * @author lht
 * @data: on 2015/7/27 15:01
 *//*

public class SettingsActivity extends BaseActivity implements View.OnClickListener, SlideSwitch.SlideListener {
    */
/** 类名 **//*

    private static final String mClassName = "SettingsActivity";
    */
/** 上下文对象 **//*

    private Context mContext;

    */
/** 实名认证状态 **//*

    private TextView mTVVerifiedState;
    */
/** 交易密码状态 **//*

    private TextView mTVPasswordState;
    */
/** 手势密码状态 **//*

    private TextView mTVGesturePasswordState;

    */
/** 实名认证布局 **//*

    private RelativeLayout mLayVerified;
    */
/** 交易密码布局 **//*

    private RelativeLayout mLayPassword;
    */
/** 修改交易密码布局 **//*

    private RelativeLayout mLayChangePassword;
    */
/** 忘记交易密码布局**//*

    private RelativeLayout mLayForgetPassword;
    */
/** 手势密码布局 **//*

    private RelativeLayout mLayGesturePassword;
    */
/** 修改手势密码布局 **//*

    private RelativeLayout mLayChangeGesturePassword;
    */
/** 忘记手势密码布局 **//*

    private RelativeLayout mLayForgetGesturePassword;
    */
/** 退出登录布局 **//*

    private RelativeLayout mLayOut;
    */
/** 开关布局 **//*

    private LinearLayout mLaySwitch;
    */
/** title布局 **//*

    private WalletTitleView mTitle;

    */
/** 实名认证状态值 **//*

    private String mVerifiedState;
    */
/** 交易密码状态值 **//*

    private String mPasswordState;
    */
/** 手势密码状态值 **//*

    private String mGesturePasswordState;

    */
/** 开关 **//*

    private SlideSwitch mSwitchBtn;

    @Override
    public int setLayoutById() {
        return R.layout.activity_settings;
    }

    @Override
    public View setLayoutByView() {
        return null;
    }

    @Override
    public void initViews() {
        // 顶部布局
        mTitle = (WalletTitleView) findViewById(R.id.title);

        mLayVerified = (RelativeLayout)findViewById(R.id.layout_settings_verified);
        mLayPassword = (RelativeLayout)findViewById(R.id.layout_settings_password);
        mLayChangePassword = (RelativeLayout)findViewById(R.id.layout_settings_change_password);
        mLayForgetPassword = (RelativeLayout)findViewById(R.id.layout_settings_forget_password);
        mLayGesturePassword = (RelativeLayout)findViewById(R.id.layout_settings_gesture_password);
        mLayChangeGesturePassword = (RelativeLayout)findViewById(R.id.layout_settings_change_gesture_password);
        mLayForgetGesturePassword = (RelativeLayout)findViewById(R.id.layout_settings_forget_gesture_password);
        mLayOut = (RelativeLayout)findViewById(R.id.layout_settings_out);
        mLaySwitch = (LinearLayout)findViewById(R.id.layout_settings_switch);

        mTVVerifiedState = (TextView)findViewById(R.id.tv_settings_verified);
        mTVPasswordState = (TextView)findViewById(R.id.tv_settings_password);
        mTVGesturePasswordState = (TextView)findViewById(R.id.tv_settings_gesture_password);

        mSwitchBtn = (SlideSwitch)findViewById(R.id.switch_btn);

        // 注册监听
        mLayVerified.setOnClickListener(this);
        mLayPassword.setOnClickListener(this);
        mLayChangePassword.setOnClickListener(this);
        mLayForgetPassword.setOnClickListener(this);
        mLayGesturePassword.setOnClickListener(this);
        mLayChangeGesturePassword.setOnClickListener(this);
        mLayForgetGesturePassword.setOnClickListener(this);
        mLayOut.setOnClickListener(this);
        mSwitchBtn.setSlideListener(this);

        // 设置顶部布局
        mTitle.setTitle(getString(R.string.settings_title));
        mTitle.setLeftClickListener(new TitleLeftOnClickListener());
        mTitle.setRightClickListener(new TitleRightOnClickListener(), getString(R.string.help_title));
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }

    */
/**
     * 点击事件
     * @param view
     *//*

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 实名认证布局
            case R.id.layout_settings_verified:

                break;
            // 交易密码布局
            case R.id.layout_settings_password:

                break;
            // 修改交易密码布局
            case R.id.layout_settings_change_password:

                break;
            // 忘记交易密码布局
            case R.id.layout_settings_forget_password:

                break;
            // 手势密码布局
            case R.id.layout_settings_gesture_password:

                break;
            // 修改手势密码布局
            case R.id.layout_settings_change_gesture_password:

                break;
            // 忘记手势密码布局
            case R.id.layout_settings_forget_gesture_password:

                break;
            // 退出登录布局
            case R.id.layout_settings_out:

                break;
        }
    }

    */
/**
     * 顶部布局--左按钮事件监听
     *//*

    public class TitleLeftOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            finish();
        }

    }

    */
/**
     * 顶部布局--右按钮事件监听
     *//*

    public class TitleRightOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

        }

    }

    */
/**
     * 开
     *//*

    @Override
    public void open() {

    }

    */
/**
     * 关
     *//*

    @Override
    public void close() {

    }
}
*/
