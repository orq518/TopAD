package com.topad.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.topad.R;
import com.topad.TopADApplication;
import com.topad.view.customviews.UcfToast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static Toast mToast;

    /**
     * 判断字符串是否为空
     *
     * @param @param txt
     * @return boolean true:字符串为空，false:非空
     * @Description:
     */
    public static boolean isEmpty(String txt) {
        boolean flag = false;
        if (txt == null || "".equals(txt))
            flag = true;
        return flag;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * Convert byte[] to hex
     * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     *
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            // String hv = Integer.toHexString(v);
            String hv = Integer.toHexString(v).toUpperCase();
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static void writeBinaryData(byte[] data, String path) {
        if (data == null || data.length == 0)
            return;

        File f = new File(path);
        try {
            FileOutputStream out = new FileOutputStream(f);
            out.write(data);
            out.flush();
            out.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void writeToFile(String text, String path) {
        if (text == null || "".equals(text))
            return;

        File f = new File(path);
        try {
            FileOutputStream out = new FileOutputStream(f);
            out.write(text.getBytes());
            out.flush();
            out.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 将Bitmap对象保存为jpg 图片文件
     *
     * @param pathName
     */
    public static boolean saveBitmapToFile(Bitmap bitmap, String pathName) {
        File f = new File(pathName);
        FileOutputStream fOut = null;
        try {
            f.createNewFile();
            fOut = new FileOutputStream(f);
            return bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fOut.flush();
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 校验18位身份证号码是否符合规则
     *
     * @param id 输入的18位身份证号码
     * @return boolean
     */
    public static boolean invalidateID(String id) {
        if (null == id || id.length() != 18) {
            return false;
        }

        id = id.toUpperCase();
        int tSub[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char tRes[] = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int sum = 0, tmpInt = 0;
        try {
            for (int i = 0; i < id.length() - 1; i++) {
                tmpInt = Integer.parseInt(id.substring(i, i + 1));
                tmpInt *= tSub[i];
                sum += tmpInt;
            }
            tmpInt = sum % 11;
            char s = id.charAt(17);
            if (s != tRes[tmpInt]) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static byte[] readFileToBytes(String path) {
        byte[] b = null;
        File f = new File(path);
        if (f.exists()) {
            try {
                FileInputStream fis = new FileInputStream(f);
                b = new byte[(int) f.length()];
                fis.read(b, 0, (int) f.length());
                fis.close();
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    public static String readFileToString(String path) {
        StringBuffer sb = new StringBuffer();
        File f = new File(path);
        if (f.exists()) {
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                fr.close();
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 得到设备信息字符串，供上传服务器拼接参数串里扩展字段使用.
     *
     * @param ctx the ctx
     * @return the device info
     */
    public static String getDeviceInfo(Context ctx) {
        StringBuilder result = new StringBuilder();
        result.append("t_platform=android");
        result.append("&t_edition=" + Constants.INTERNAL_VERSION);
        result.append("&t_location=");
        StringBuilder location = new StringBuilder();
        // t_location
        // 本地信息（device_id|uuid|client_style|os_style|mac_addr|imsi|isroot|)
        TelephonyManager tm = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        // device_id
        String device_id = tm.getDeviceId();
        if (device_id != null && device_id.length() > 0) {
            // location.append(Md5.md5s(device_id));
            location.append(device_id);
        }
        // uuid
        location.append("||");
        // client_style
        location.append(android.os.Build.MODEL);
        // os_style
        location.append("|");
        location.append(android.os.Build.VERSION.RELEASE);
        location.append(",sdk,");
        location.append(android.os.Build.VERSION.SDK_INT);
        // hash mac addr
        location.append("|");

        String mac = getMacAddress(ctx);
        // 若没有wifi mac地址，则直接上传空值
        if (mac != null && mac.length() > 0) {
            // String macWithSalt = device_id + mac;
            // location.append(Md5.md5s(macWithSalt));
            location.append(mac);
        }

        // imsi
        location.append("|");
        try {
            String imsi = tm.getSubscriberId();
            if (imsi != null) {
                // location.append(Md5.md5s(imsi));
                location.append(imsi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // isroot
        location.append("|");
        boolean isRooted = isPhoneRooted(ctx);
        String isRoot = isRooted ? "1" : "0";
        location.append(isRoot);
        location.append("|");
        result.append(Uri.encode(location.toString()));
        result.append("&t_channel=CertPay");
        result.append("&t_exten=");
        LogUtil.d("avin", "location-->" + result.toString());
        return result.toString();
    }

    /**
     * 判断手机是否被root了
     * 在Android中，虽然我们可以通过Runtime.getRuntime().exec("su")的方式来判断一个手机是否Root,
     * 但是该方式会弹出对话框让用户选择是否赋予该应用程序Root权限，有点不友好。
     * 其实我们可以在环境变量$PATH所列出的所有目录中查找是否有su文件来判断一个手机是否Root。
     * 当然即使有su文件，也并不能完全表示手机已经Root，但是实际使用中作为一个初略的判断已经很好了。 出于效率的考虑，在代码中直接把$PATH写死.
     *
     * @param ctx
     * @return
     */
    public static boolean isPhoneRooted(Context ctx) {
        File f = null;
        final String searchPath[] = {"/system/bin/", "/system/xbin/",
                "/system/sbin/", "/sbin/", "/vendor/bin/"};
        try {
            int len = searchPath.length;
            for (int i = 0; i < len; i++) {
                f = new File(searchPath[i] + "su");
                if (f != null && f.exists()) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public static String getMacAddress(Context c) {
        try {
            WifiManager wifiManager = (WifiManager) c
                    .getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String mac = wifiInfo.getMacAddress();
                if (mac == null) {
                    boolean enabled = wifiManager.isWifiEnabled();
                    try {
                        for (int i = 0; i < 3; i++) {
                            try {
                                wifiManager.setWifiEnabled(true);

                                wifiInfo = wifiManager.getConnectionInfo();
                                mac = wifiInfo.getMacAddress();
                                if (mac != null)
                                    return mac;

                            } catch (Exception e) {
                            }
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                            }
                        }
                    } finally {
                        if (!enabled) {
                            try {
                                wifiManager.setWifiEnabled(false);
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * yuan_or_fen <br/>
     * 0: 单位为分 <br/>
     * 1: 单位为元
     */
    public static String transformToMoney(String money, int yuan_or_fen) {
        double d = 0;
        try {
            if (yuan_or_fen == 0) {
                d = (double) Double.parseDouble(money) / 100;
            } else if (yuan_or_fen == 1) {
                d = (double) Double.parseDouble(money);
            }
            return transformToMoney(d, 1);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * yuan_or_fen <br/>
     * 0: 单位为分 <br/>
     * 1: 单位为元
     */
    public static String transformToMoney(double money, int yuan_or_fen) {
        double d = 0;
        if (yuan_or_fen == 0) {
            d = money / 100;
        } else if (yuan_or_fen == 1) {
            d = money;
        }
        // NumberFormat nf = NumberFormat.getInstance();
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.CHINA);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        return nf.format(d);
    }

    // public static String yuan2Fen(String money) {
    // double d = (double) Double.parseDouble(money) * 100;
    // return Integer.toString((int) d);
    // }


    public static String smallYuanToFen(String money) {
        if (money != null && !"".equals(money)) {
            int index = money.indexOf(".");
            money = money.substring(index + 1, money.length());
            if (index > -1 && money.length() == 2) {
                String shiwei = money.substring(0, 1);
                String gewei = money.substring(1, 2);
                int a = ((int) Integer.valueOf(shiwei)) * 10
                        + Integer.valueOf(gewei);
                return Integer.toString(a);
            } else if (index > -1 && money.length() == 1) {
                int a = ((int) Integer.valueOf(money)) * 10;
                return Integer.toString(a);
            }
        }
        return "";
    }

    /**
     * 元转成分
     *
     * @param money
     * @return
     */
    public static String yuanToFen(String money) {
        double d = (double) Double.parseDouble(money) * 100;
        if (d < 100d) {
            return smallYuanToFen(money);
        }
        return Integer.toString((int) d);
    }

    /**
     * \
     *
     * @param @param path 路径字符串
     * @return boolean 是否创建成功
     * @Description: 创建指定路径的目录结构
     */
    public static boolean createPathIfNotExist(String path) {
        // 目录不存在则创建
        try {
            File targetPath = new File(path);
            if (!targetPath.exists()) {
                targetPath.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

//	public static void notifyToExit(Context ctx) {
//		Intent i = new Intent(Constants.BROADCAST_INTENT_ACTION_EXIT);
//		LocalBroadcastManager.getInstance(ctx).sendBroadcastSync(i);
//	}

    /**
     * @param @param context
     * @return boolean true:有网络，false:无网络
     * @Description: 检测是否有网络
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
//			Toast.makeText(
//					context,
//					context.getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
            UcfToast.makeText(context, context.getString(R.string.no_connection));
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
//			Toast.makeText(
//					context,
//					context.getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
            UcfToast.makeText(context, context.getString(R.string.no_connection));
            return false;
        }
        return true;
    }


    /**
     * 对EditText设置当点击时，隐藏系统弹出的软键盘
     *
     * @param edit
     */
    public static void setNoSystemInputOnEditText(EditText edit) {
        // EditText点击时，隐藏系统弹出的软键盘，显示出光标
        // Android 3.0 以下时可以用EditText.setInputType(InputType.TYPE_NULL)实现
        // 3.0以上上述方法有副作用，即把光标也隐藏了。只能调用隐藏方法setShowInputOnFocus(false)实现。
        if (android.os.Build.VERSION.SDK_INT < 11) {
            edit.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            try {
                Method setShowSoftInputOnFocus = cls.getMethod(
                        "setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(edit, false);
            } catch (NoSuchMethodException e) {
                try {
                    Method setShowSoftInputOnFocus = cls.getMethod(
                            "setSoftInputShownOnFocus", boolean.class);
                    setShowSoftInputOnFocus.setAccessible(true);
                    setShowSoftInputOnFocus.invoke(edit, false);
                } catch (Exception e1) {
                    edit.setInputType(InputType.TYPE_NULL);
                    e1.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static String formatDate(String time) {
        if (time == null || time.equals("")) {
            return "";
        }
        if (time.length() < 10) {
            return time;
        }
        // 服务器格式有两种 20141024092333 和 2014-10-24 09:23:33
        time = time.replace(" ", "");
        time = time.replace("-", "");
        time = time.replace(":", "");
        try {
            String month = time.substring(4, 6);
            if (month != null && month.startsWith("0")) {
                month = month.substring(1);
            }
            String day = time.substring(6, 8);
            if (day != null && day.startsWith("0")) {
                day = day.substring(1);
            }
            String hour = time.substring(8, 10);
            if (hour != null && hour.startsWith("0")) {
                hour = hour.substring(1);
            }
            String minute = time.substring(10, 12);
            return month + "月" + day + "日 " + hour + ":" + minute;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return time;
        }
    }


    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy年MM月dd日  HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 根据给定的格式返回时间
     *
     * @param ms
     * @param format（yyyy年MM月dd日 HH:mm）
     * @return
     */
    public static String getDateByFormat(Long ms, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String date;
        if (null == ms) {
            ms = System.currentTimeMillis();
            date = dateFormat.format(new Date(ms));
        } else {
            date = dateFormat.format(new Date(ms * 1000L));
        }
        if (null == date || "".equalsIgnoreCase(date)) {
            date = "0000-00-00 00:00";
        }
        return date;// 0000-00-00 00:00
    }


//	public static String rsaEncodeByPublicKey(String text) {
//		try {
//			byte[] rsaPw = RSAUtil.encrptByPublicKey(text.getBytes(),
//					RSAUtil.PUBLIC_KEY_JAVA);
//			return new String(Base64.encode(rsaPw, Base64.NO_PADDING));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "";
//	}


    /**
     * 从短信字符串中截取第一个连续6位数字字符串
     *
     * @param text
     * @return
     */
    public static String getVerifyCode(String text) {
        String ret = "";
        if (!TextUtils.isEmpty(text)) {
            Pattern p = Pattern.compile("\\d{6}");
            Matcher m = p.matcher(text);
            if (m.find()) {
                ret = m.group(0);
            }
        }
        return ret;
    }

    /**
     * 开启activity
     */
    public static void launchActivity(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }

    public static void launchActivityForResult(Activity context,
                                               Class<?> activity, int requestCode) {
        Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeybord(Activity activity) {

        if (null == activity) {
            return;
        }
        try {
            final View v = activity.getWindow().peekDecorView();
            if (v != null && v.getWindowToken() != null) {
                InputMethodManager imm = (InputMethodManager) activity
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception e) {

        }
    }

    /**
     * 判断是否为合法的json
     *
     * @param jsonContent 需判断的字串
     */
    public static boolean isJsonFormat(String jsonContent) {
        try {
            new JsonParser().parse(jsonContent);
            return true;
        } catch (JsonParseException e) {
            LogUtil.d("bad json: " + jsonContent);
            return false;
        }
    }

    /**
     * 判断字符串是否为空
     *
     * @param text
     * @return true null false !null
     */
    public static boolean isNull(String text) {
        if (text == null || "".equals(text.trim()) || "null".equals(text))
            return true;
        return false;
    }

//	/**
//	 * 抖动动画
//	 */
//	public static void startShakeAnim(Context context, View view) {
//		Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
//		view.startAnimation(shake);
//	}

    /**
     * 判断网络是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 显示进度
     *
     * @param context
     * @param title
     * @param message
     * @param indeterminate
     * @param cancelable
     * @return
     */
    public static ProgressDialog showProgress(Context context,
                                              CharSequence title, CharSequence message, boolean indeterminate,
                                              boolean cancelable) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIndeterminate(indeterminate);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(false);
        // dialog.setDefaultButton(false);
        dialog.show();
        return dialog;
    }

    public static String softVersion(Context context) {
        if (isEmpty(Constants.softVersionName)) {
            PackageInfo info = TopADApplication.getSelf().getPackageInfo();
            if (info == null) {
                try {
                    info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            Constants.softVersionName = info.versionName;
        }
        if(isEmpty(Constants.softVersionName)){
            Constants.softVersionName="";
        }
        return Constants.softVersionName;
    }

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        if (info == null) {
            try {
                info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return info;
    }

    public static int getImageHeight(Activity act) {
        float fz = 206;
        float fm = 640;
        return (int) (getScreenWidth(act) * (fz / fm));// 图片的大小为640*206
    }

    public static int getScreenWidth(Activity act) {
        DisplayMetrics dm = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕分辨率
     *
     * @param context
     * @return
     */
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        int result[] = {width, height};
        return result;
    }

    /**
     * 全局Toast
     *
     * @param ct
     * @param textId
     */
    public static void showToast(Context ct, int textId) {
        if (null == mToast) {
            mToast = Toast.makeText(ct, "", Toast.LENGTH_SHORT);
        }
        LayoutInflater inflater = LayoutInflater.from(ct);
        View layout = inflater.inflate(R.layout.toast_layout, null);
        TextView mTv = (TextView) layout.findViewById(R.id.tv_content);
        mTv.setText(ct.getResources().getString(textId));
        mToast.setView(layout);

//        mToast.setText(ct.getResources().getString(textId));
        mToast.show();

    }

    public static void showToast(Context ct, String text) {
        if (null == mToast) {
            mToast = Toast.makeText(ct, "", Toast.LENGTH_SHORT);
        }
        LayoutInflater inflater = LayoutInflater.from(ct);
        View layout = inflater.inflate(R.layout.toast_layout, null);
        TextView mTv = (TextView) layout.findViewById(R.id.tv_content);
        mTv.setText(text);
        mToast.setView(layout);

//        mToast.setText(text);
        mToast.show();
    }

    public static void hideToast() {
        if (null != mToast)
            mToast.cancel();
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNum(String mobiles) {
        /*
        移动：134、135、136、137、138、139、14X、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）
		网信：137
		总结起来就是第一位必定为1，第二位必定为3或5或7或8，其他位置的可以为0-9
		*/
//		String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1]\\d{10}";//"[1]"代表第1位为数字1，"\\d{9}"代表后面是可以是0～9的数字，有10位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 将手机号码 用星号遮掩
     */
    public static String getProtectedMobile(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 11) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(phoneNumber.subSequence(0, 3));
        builder.append("****");
        builder.append(phoneNumber.subSequence(7, 11));
        return builder.toString();
    }

    /**
     * 银行卡图标
     *
     * @param ctx
     * @param code
     * @return
     */
    public static int getBankDrawableIdForCard(Context ctx, String code) {
        int drawableId;
        if (Utils.isEmpty(code.toLowerCase())) {
            return 0;
        }

        try {
            drawableId = ctx.getResources().getIdentifier(code.toLowerCase(), "drawable", ctx.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            drawableId = ctx.getResources().getIdentifier("bank_branch_icon", "drawable", ctx.getPackageName());
        }

        // 不能识别code,设置为默认银行卡图标
        if (drawableId == 0) {
            drawableId = ctx.getResources().getIdentifier("bank_branch_icon", "drawable", ctx.getPackageName());
        }
        return drawableId;
    }


    /**
     * 获得带有逗号分隔的金额字符串
     *
     * @param moneyText
     * @return
     */
    public static String getMoneyTextWithComma(String moneyText) {

        if (TextUtils.isEmpty(moneyText)) return "";

        DecimalFormat df = new DecimalFormat("#,###");
        if (moneyText.contains(".")) {
            String intStr = moneyText.substring(0, moneyText.indexOf("."));
            String floatStr = moneyText.substring(moneyText.indexOf("."));

            return df.format(Double.parseDouble(intStr)) + floatStr;
        } else {
            return df.format(Double.parseDouble(moneyText));

        }
    }

    public static int dp2px(int dp, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 从URL中根据key获取值
     * @param url
     * @return
     */
    public static String getValueFromUrl(String url,String key){
        if(TextUtils.isEmpty(url)|| TextUtils.isEmpty(key) || !url.contains("?")) return "";
        if(!TextUtils.isEmpty((url.split("\\?"))[1])){
            String[] keyV = (url.split("\\?"))[1].split("&");
            for(int i=0;i<keyV.length;i++){
                if(!TextUtils.isEmpty(keyV[i]) && keyV[i].contains(key)){
                    return (keyV[i].split("="))[1];
                }
            }
        }
        return "";
    }
}
