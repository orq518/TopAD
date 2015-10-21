package com.topad.util;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.topad.TopADApplication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * The author 欧瑞强 on 2015/8/18.
 * todo
 */
public class TokenUtils {

    private final static String HEX = "0123456789ABCDEF";

    public static String encrypToken(String tokenString) {
        // 加密密码
        String password = getTokenKey();

        String miwen = "";
        try {
            // 非空判断
            if (tokenString != null && tokenString.length() > 0) {
                // 对token加密
                byte[] result = desCrypto(tokenString.getBytes(), password);
                // 加密后的二进制转成字符串
                miwen = toHex(result);
            }
        } catch (Exception e) {

        }
        return miwen;
    }

    public static String decryToken(String miwen) {
        String token = "";
        try {
            // 解密密码
            String password = getTokenKey();
            // 非空判断
            if (miwen != null && miwen.length() > 0) {
                // 将密文转成二进制
                byte[] miwenbyte = toByte(miwen);
                // 解密
                try {
                    byte[] decryResult = decrypt(miwenbyte, password);
                    token = new String(decryResult);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {

        }


        return token;
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    private static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    // des加密
    private static byte[] desCrypto(byte[] datasource, String password) {
        try {
            SecureRandom random = new SecureRandom();

            DESKeySpec desKey = new DESKeySpec(password.getBytes());

            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

            SecretKey securekey = keyFactory.generateSecret(desKey);

            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");

            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);

            // 现在，获取数据并加密
            // 正式执行加密操作
            return cipher.doFinal(datasource);
        } catch (Throwable e) {

            e.printStackTrace();

        }

        return null;

    }

    private static byte[] decrypt(byte[] src, String password) throws Exception {

        // DES算法要求有一个可信任的随机数源

        SecureRandom random = new SecureRandom();

        // 创建一个DESKeySpec对象

        DESKeySpec desKey = new DESKeySpec(password.getBytes());

        // 创建一个密匙工厂

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

        // 将DESKeySpec对象转换成SecretKey对象

        SecretKey securekey = keyFactory.generateSecret(desKey);

        // Cipher对象实际完成解密操作

        Cipher cipher = Cipher.getInstance("DES");

        // 用密匙初始化Cipher对象

        cipher.init(Cipher.DECRYPT_MODE, securekey, random);

        // 真正开始解密操作

        return cipher.doFinal(src);

    }


    private static String getTokenKey() {
        return getMD5UUID(TopADApplication.getContext());
    }

    private static String getMD5UUID(Context context) {
        final TelephonyManager teleManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        final String deviceId, simSerial, androidId;
        deviceId = "" + teleManager.getDeviceId();
        simSerial = "" + teleManager.getSimSerialNumber();
        androidId = ""
                + android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) deviceId.hashCode() << 32) | simSerial.hashCode());
        return getMD5(deviceUuid.toString().toUpperCase() + "WXLC");
    }

    // md5
    private static String getMD5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }
}
