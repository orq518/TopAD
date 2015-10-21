package com.topad.util;


public class Constants {
    // 内部版本号
    public static final int INTERNAL_VERSION = 1;
    public static final String T_CHANNEL = "HD_COMMON";

    public static  String softVersionName;
    /*********
     * 接口
     *********/
    // 登录
    public static final String URL_LOGIN = "/user/login";
    // 注册
    public static final String URL_REGISTER = "/register/post";
    // 获取手机绑定的邀请码
    public static final String URL_REGISTER_INVITE_CODE = "/register/invite_code";
    // 邀请首页
    public static final String URL_INVITE_INDEX = "/invite/index";
    // 忘记密码设置登录密码
    public static final String URL_PASSWORD_SET = "/password/set";
    // 注册设置登录密码
    public static final String URL_SET_LOGIN_PASSWORD = "/register/set_password";
    // 忘记密码
    public static final String URL_FORGET_PASSWORD = "/password/find";
    // 首页（理财）
    public static final String URL_INDEX = "/index/index";
    // 理财预约购买
    public static final String URL_PRE_POST = "/pre/post";
    // 理财预约购买确认页
    public static final String URL_PRE_CONFIRM = "/pre/confirm";
    // 赎回
    public static final String URL_REDEEM_POST = "/redeem/post";
    // 发送验证码
    public static final String URL_REGISTET_SEND_CODE = "/register/send_vcode";
    public static final String URL_PASSWORD_SEND_CODE = "/password/send_vcode";
    // 验证验证码
    public static final String URL_REGISTET_CODE_VERIFY = "/register/check_vcode";
    public static final String URL_PASSWORD_CODE_VERIFY = "/password/check_vcode";
    // 充值
    public static final String URL_RECHARGE = "/charge/post";
    // 认购协议
    public static final String URL_TENDER_AGREEMENT = "/tender/agreement";
    // 财富
    public static final String URL_WEALTH = "/account/info";
    // 实名认证
    public static final String URL_AUTH = "/user/auth";
    // 提现
    public static final String URL_WITHDRAW_CASH = "/cash/post";
    // 已购债权
    public static final String URL_TENDER_DEBTS = "/tender/debts";
    // 预约债权
    public static final String URL_PRE_LIST = "/pre/list";
    // 资金记录
    public static final String URL_ACCOUNT_LOG = "/account/log";
    // 修改登录密码
    public static final String URL_CHANGE_LOGIN_PWD = "/user/chg_pwd";
    // 关于我们
    public static final String URL_ABOUT_US = "/about/us";
    // 常见问题
    public static final String URL_ABOUT_FAQ = "/about/faq";
    // 注册协议
    public static final String URL_REGISTER_AGREEMENT = "/register/agreement";
    // 活期详情
    public static final String URL_TENDER_DETAIL = "/tender/detail";
    //launch页图片
    public static final String URL_INDEX_START = "/index/start";
    // 钱包登陆信息
    public static final String URL_WALLET_LOGIN = "/wallet/login";
    // 活动规则
    public static final String URL_INVITE_RULE = "/invite/rule";
    // 我的邀请
    public static final String URL_INVITE_LIST = "/invite/list";
    //业务的url拼接
    public static final String URL_BANK_LINES_LIST = "/bankLimitList.html";
    // 关于活期
    public static final String URL_ABOUT_HUOQI = "/tender/introduce";
    //版本更新
    public static final String URL_VERSION_UPDATE = "/about/version_update";
    /********* END *********/

    /**
     * 测试环境
     */
    public static final String URL_TEST = "http://test3.api.mantoulicai.com";
    /**
     * 生产环境
     */
    public static final String URL_PUBLISH = "http://api.hongdoulicai.com";


    // 当前环境，默认测试环境
    public static String CURR_URL = URL_PUBLISH;

    // 钱包的环境  true 表示线上环境  false 测试环境
    public static boolean WALLET_ONLINE = true;
    // 支付的环境  true 表示线上环境  false 测试环境
    public static boolean PAY_ONLINE = true;

    /**
     * 获取当前环境
     *
     * @return 当前环境URL
     */
    public static String getCurrUrl() {
        // debug状态为false时，返回生产环境
        return (CURR_URL);
    }

    /**
     * 切换正式还是测试环境
     */
    public static void setUrlOnline(boolean isOnline) {
        if (isOnline) {
            CURR_URL = URL_PUBLISH;
            WALLET_ONLINE = true;
            PAY_ONLINE = true;
        } else {
            CURR_URL = URL_TEST;
            WALLET_ONLINE = false;
            PAY_ONLINE = false;
        }
    }


    /**
     * 广播Action
     **/
    public static final String BroadCast_Action_Login = "BroadCast_Action_Login";   //登录
    public static final String BroadCast_Action_LogOut = "BroadCast_Action_Logout"; //登出
    public static final String BroadCast_Action_Clear_Token = "BroadCast_Action_Clear_Token";   //清除token
    public static final String BroadCast_Action_SUCCEED_Login_INDEX = "BroadCast_Action_SUCCEED_Login_INDEX";   //登录成功后跳转的tabindex


}