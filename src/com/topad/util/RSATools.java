package com.topad.util;

/**
 * The author 欧瑞强 on 2015/8/30.
 * todo
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import android.util.Base64;

/**
 * @author
 * @version 1.0
 * @created
 */
public class RSATools {

    //    public static final String RSA_PUBLICE =
//            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPZ1Ju6zzYUoL7HYzLgneROyDCQf/NANWGO42FfXwQinYVSpqnmtukGLsOhVuwdNH6aRFE0ms3bkpp/WL4cfVDgnCO+W9J6vRVpuTuD/iqfJd9TNacLCd3Jvg3HhjqxbJeO74fYnYqo/mmyJSeLE5iZg4IZm5LPWBZWUp3ULCAZQIDAQAB";
    public static final String RSA_PUBLICE = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDidaBrIcV6rOqXSQ5xkiCZE64O" + "\r" + "opQVLEWBeqFp8aNpJoVIK81y7++Tj4lAYel+AHll1N8RqUaHwke3fb+PCgfUKdpi" + "\r" + "5992pYebf3ypM3fX5xclfoZ3Aw4VV1tsChYS+tXagNhbC6YEiEjLCz4Tmoejcsu5" + "\r" + "Sh2EIlpMS5f6oWL6ZwIDAQAB";
    private static final String ALGORITHM = "RSA";

    /**
     * 得到公钥
     *
     * @param algorithm
     * @param bysKey
     * @return
     */
    private static PublicKey getPublicKeyFromX509(String algorithm,
                                                  String bysKey) throws NoSuchAlgorithmException, Exception {
        if(Utils.isEmpty(algorithm)||Utils.isEmpty(bysKey)){
            return null;
        }
        byte[] decodedKey = Base64.decode(bysKey, Base64.DEFAULT);
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodedKey);

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(x509);
    }

    /**
     * 使用公钥加密
     *
     * @param content
     * @return
     */
    public static String encryptByPublic(String content) {
        if(Utils.isEmpty(content)){
            return null;
        }
        try {
            PublicKey pubkey = getPublicKeyFromX509(ALGORITHM, RSA_PUBLICE);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubkey);

            byte plaintext[] = content.getBytes("UTF-8");
            byte[] output = cipher.doFinal(plaintext);

            String s = new String(Base64.encode(output, Base64.DEFAULT));

            return s;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 使用公钥解密
     *
     * @param content 密文
     * @return 解密后的字符串
     */
    public static String decryptByPublic(String content) {
        if(Utils.isEmpty(content)){
            return null;
        }
        try {
            PublicKey pubkey = getPublicKeyFromX509(ALGORITHM, RSA_PUBLICE);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, pubkey);
            InputStream ins = new ByteArrayInputStream(Base64.decode(content, Base64.DEFAULT));
            ByteArrayOutputStream writer = new ByteArrayOutputStream();
            byte[] buf = new byte[128];
            int bufl;
            while ((bufl = ins.read(buf)) != -1) {
                byte[] block = null;
                if (buf.length == bufl) {
                    block = buf;
                } else {
                    block = new byte[bufl];
                    for (int i = 0; i < bufl; i++) {
                        block[i] = buf[i];
                    }
                }
                writer.write(cipher.doFinal(block));
            }
            return new String(writer.toByteArray(), "utf-8");
        } catch (Exception e) {
            return null;
        }
    }

}