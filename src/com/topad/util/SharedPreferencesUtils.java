package com.topad.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * The author 欧瑞强 on 2015/7/27.
 * todo
 */
public class SharedPreferencesUtils {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "wallet_app_sp";
    public static final String ACCOUNT = "account";


    /**
     * 存储数据key字段汇总
     */
    public static String KEY_TOKEN = "token";    //登录后服务端返回的token
    public static String KEY_PURCHASE_FINANCE_TIPS = "purchase_finance_tips";    //预约填写页，页面下方提示
    public static String KEY_FINANCE_RATE = "finance_rate";    //理财产品年化收益率
    public static String KEY_LOGIN_USER_NAME = "user_name";    //登录账号名
    public static String KEY_REAL_NAME = "real_name";    //真实姓名
    public static String KEY_GESTURE_PWD = "gesture_pwd";    //手势密码
    public static String KEY_GESTURE_PWD_SWITCH = "gesture_pwd_switch";    //手势密码开关
    public static String KEY_GESTURE_PWD_ERR_COUNTS = "gesture_pwd_error_count";    //手势密码错误尝试次数
    public static String KEY_APP_LAST_SWITCH_BACKGROUND_TIME = "app_last_switch_background_time";    //app上次切换后台时间
    public static String KEY_APP_IN_BACKGROUND_STATE = "app_in_background_state";    //app在后台状态
    public static String KEY_LOGIN_PWD = "login_pwd";    //登录密码
    public static String KEY_LAUNCH_IMAGE_URL = "launch_image_url";    //launch页图片URL

    public static String KEY_WEALTH_ANIMA = "key_wealth_anima";    //财富页面，提示总收益的动画是否执行过
    public static String KEY_IS_REQUIRED_UPDATE = "isRequiredUpdate";    //检查更新状态


    private static SharedPreferences sp = null;
    private static String curAccount;  //当前账户
    public static String KEY_MARCHANTID = "merchantId";    //merchantId
    public static String KEY_WALLET_SIGN = "wallet_sign";    //钱包签名
    /**
     * 获取数据存储实例句柄
     *
     * @param context 上下文
     * @param account 账户    数据根据账户分文件存储
     *                note : 该方法只应在每次登录成功后调用一次
     */
    public static SharedPreferences getInstance(Context context, @NonNull String account) {
        if (TextUtils.isEmpty(account)) {
            throw new IllegalArgumentException("account is null");
        }

        if (sp == null) {
            synchronized (SharedPreferencesUtils.class) {
                if (sp == null) {
                    SharedPreferences acc_sp = context.getSharedPreferences(ACCOUNT,
                            Context.MODE_PRIVATE);
                    SharedPreferencesCompat.apply(acc_sp.edit().putString("account", account));

                    sp = context.getSharedPreferences(FILE_NAME + "_" + account,
                            Context.MODE_PRIVATE);
                }
            }
        }

        return sp;
    }

    /**
     * 获取数据存储实例句柄
     *
     * @param context 上下文
     */
    private static SharedPreferences getInstance(Context context) {
        if (sp == null) {
            synchronized (SharedPreferencesUtils.class) {
                if (sp == null) {
                    getCurAccount(context);

                    if (!TextUtils.isEmpty(curAccount)) {
                        sp = context.getSharedPreferences(FILE_NAME + "_" + curAccount,
                                Context.MODE_PRIVATE);
                    } else {
                        return context.getSharedPreferences(FILE_NAME,
                                Context.MODE_PRIVATE);
                    }
                }
            }
        }

        return sp;
    }

    public static String getCurAccount(Context context) {
        if (TextUtils.isEmpty(curAccount)) {
            SharedPreferences acc_sp = context.getSharedPreferences(ACCOUNT,
                    Context.MODE_PRIVATE);
            curAccount = acc_sp.getString("account", null);
        }

        return curAccount;
    }


    public static void clearCurAccount(Context context) {
        if (!TextUtils.isEmpty(curAccount)) {
            SharedPreferences acc_sp = context.getSharedPreferences(ACCOUNT,
                    Context.MODE_PRIVATE);
            acc_sp.edit().putString("account", null);
            curAccount = null;
        }

        sp = null;
    }


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, @NonNull Object object) {

        SharedPreferences sp = getInstance(context);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            if (key.equals(KEY_TOKEN)) {
                LogUtil.d("token33:" + (String) object);
                String tokenString = TokenUtils.encrypToken((String) object);
                LogUtil.d("token44:" + tokenString);
                editor.putString(key, tokenString);
            }else{
                editor.putString(key, (String) object);
            }

        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, @NonNull Object defaultObject) {
        SharedPreferences sp = getInstance(context);
//        if(defaultObject != null){
//            return null;
//        }
        if (defaultObject instanceof String) {
            if (key.equals(KEY_TOKEN)) {
                String tokenString = sp.getString(key, (String) defaultObject);
                LogUtil.d("token1:" + tokenString);
                tokenString = TokenUtils.decryToken(tokenString);
                LogUtil.d("token2:" + tokenString);
                return tokenString;
            }
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, @NonNull String key) {
        SharedPreferences sp = getInstance(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = getInstance(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, @NonNull String key) {
        SharedPreferences sp = getInstance(context);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = getInstance(context);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
