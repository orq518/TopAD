package com.topad.util;

import com.topad.net.http.Base64;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class AES {
    private static final String AESTYPE = "AES/ECB/PKCS5Padding";

    /**
     * @param @param str 待加密的源数据
     * @return String[] 加密结果返回，第一个是生成key的时间错，第二个是加密key，第三个是返回的AES密文
     * @Description: AES 加密
     */
    public static String[] aesEncoode(String str) {
        String[] ret = new String[3];
        String key = System.currentTimeMillis()+"";
        String keytemp = long2key(key);
        String tm = keytemp+keytemp;//两个8位数的字符串拼成16的
        ret[0] = key;
        ret[1] = tm;
        try {
            ret[2] = AES_Encrypt(ret[1],str);
        } catch (Exception e) {

        }

        return ret;
    }
    public static String AES_Encrypt(String keyStr,String plainText) {
        byte[] encrypt = null;
        try {
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AESTYPE);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypt = cipher.doFinal(plainText.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String(Base64.encode(encrypt, Base64.DEFAULT));
    }

    public static String AES_Decrypt(String keyString,String encryptData) {
        byte[] decrypt = null;
        try {
            Key key = generateKey(keyString);
            Cipher cipher = Cipher.getInstance(AESTYPE);
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypt = cipher.doFinal(Base64.decode(encryptData.getBytes(), Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(decrypt).trim();
    }

    private static Key generateKey(String key) throws Exception {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            return keySpec;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
    /**
     * 生成秘钥字符串
     */
    private static String long2key(String tm) {

        // 将十位时间戳转为16位字符串,规则为头部加三位,尾部加后三位
        // String str =
        // "1398221442867";//String.valueOf(System.currentTimeMillis());
        // mTm = str;
        String str = tm;
        str = str.substring(str.length() - 8, str.length());

        int lastNum = Integer.parseInt(str.substring(str.length() - 1,
                str.length()));
        int num1 = lastNum;
        int num2 = Integer.parseInt(str.substring(str.length() - 2,
                str.length() - 1));
        int num3 = Integer.parseInt(str.substring(str.length() - 3,
                str.length() - 2));
        int num4 = Integer.parseInt(str.substring(str.length() - 4,
                str.length() - 3));
        lastNum = lastNum % 5;

        int[] firstNum = {7, 2, 4, 9, 0, 6, 5, 8, 1, 3};

        int[] secondNum = {0, 4, 2, 3, 7, 8, 1, 9, 6, 5};

        int[] thirdNum = {3, 1, 8, 2, 5, 6, 9, 4, 7, 0};

        int[] fourthNum = {2, 0, 9, 5, 7, 1, 4, 3, 6, 8};

        int[][] digits = {{0, 2, 3, 6}, {7, 1, 4, 2}, {2, 0, 6, 3},
                {1, 3, 5, 0}, {5, 4, 0, 7}, {3, 6, 1, 4}, {4, 7, 2, 5},
                {3, 5, 4, 1}, {7, 2, 3, 4}, {4, 6, 0, 3}};

        // 字符串数组转byte数组
        byte[] toTransBytes = new byte[str.length()];
        toTransBytes = str.getBytes();

        // 分组交换
        byte[] transBytes = new byte[toTransBytes.length];
        transBytes[0] = toTransBytes[5];
        transBytes[1] = toTransBytes[4];
        transBytes[2] = toTransBytes[7];
        transBytes[3] = toTransBytes[6];
        transBytes[5] = toTransBytes[0];
        transBytes[4] = toTransBytes[1];
        transBytes[7] = toTransBytes[2];
        transBytes[6] = toTransBytes[3];

        // 所有位都转为大小写字母
        for (int i = 0; i < transBytes.length; i++) {
            if (i % 2 == 0)
                transBytes[i] = (byte) ((transBytes[i] << 1) + 1);
            if (i % 4 == 0)
                transBytes[i] = (byte) (transBytes[i] + 1);
            if ((i % 2 != 0)) {
                transBytes[i] = (byte) (transBytes[i] * 3 / 2 - 5);
            }
            transBytes[i] = (byte) (transBytes[i] & 0x0000007f);
        }

        // System.out.println(new String(transBytes));

        // 根据原时间戳最后一位对5的余数进行数字位的插入
        switch (lastNum) {
            case 1:
                transBytes[digits[num1][0]] = (String.valueOf(firstNum[num1])
                        .getBytes())[0];
                break;

            case 2:
                transBytes[digits[num1][0]] = (String.valueOf(firstNum[num1])
                        .getBytes())[0];
                transBytes[digits[num1][1]] = (String.valueOf(secondNum[num2])
                        .getBytes())[0];
                break;

            case 3:
                transBytes[digits[num1][0]] = (String.valueOf(firstNum[num1])
                        .getBytes())[0];
                transBytes[digits[num1][1]] = (String.valueOf(secondNum[num2])
                        .getBytes())[0];
                transBytes[digits[num1][2]] = (String.valueOf(thirdNum[num3])
                        .getBytes())[0];
                break;

            case 4:
                transBytes[digits[num1][0]] = (String.valueOf(firstNum[num1])
                        .getBytes())[0];
                transBytes[digits[num1][1]] = (String.valueOf(secondNum[num2])
                        .getBytes())[0];
                transBytes[digits[num1][2]] = (String.valueOf(thirdNum[num3])
                        .getBytes())[0];
                transBytes[digits[num1][3]] = (String.valueOf(fourthNum[num4])
                        .getBytes())[0];
                break;

            default:
                break;
        }

        String result = null;

        try {
            result = new String(transBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;

    }
//
//    public static void main(String[] args) {
//        String plain = "啊大大";
//        String[] data = aesEncoode(plain);
////        String decrpt = AES.AES_Encrypt(plain);
//        System.out.println("data[0]:"+data[0]);
//        System.out.println("data[1]:"+data[1]);
//        System.out.println("data[2]:"+data[2]);
//        plain = AES.AES_Decrypt(data[1], data[2]);
//        System.out.println(plain);
//
//    }

}