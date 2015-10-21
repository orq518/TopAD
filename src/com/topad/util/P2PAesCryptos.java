/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.topad.util;


import com.topad.net.http.Base64;

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * 
 * <PRE>
 *  类名：AesCryptos
 *  
 * 类简介：
 * 			与P2P对接的AES加解密
 *  作者：dengsujun(avin)
 *  
 *  创建时间：2014年6月16日 下午6:11:27 
 * 
 * </PRE>
 */
public class P2PAesCryptos {

	private static final String AES = "AES";

	private static final int DEFAULT_AES_KEYSIZE = 128;

//	private static final byte [] AES_KEY = Base64.decode("/trBxSVzokD9vJSY/Fcj6w",Base64.NO_PADDING);
	private static final byte [] AES_KEY = Base64.decode("", Base64.NO_PADDING);

	/**
	 * 使用AES加密原始字符串.
	 * 
	 * @param input 原始输入字符数组
	 */
	public static String aesEncrypt(String input) {
		return Base64.encodeToString(aes(input.getBytes(), AES_KEY, Cipher.ENCRYPT_MODE), Base64.NO_PADDING);
	}


	/**
	 * 使用AES解密字符串, 返回原始字符串.
	 * 
	 * @param input Hex编码的加密字符串
	 */
	public static String aesDecrypt(String input) {
		byte[] decryptResult = aes(Base64.decode(input,Base64.NO_PADDING), AES_KEY, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	
	/**
	 * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 * 
	 * @param input 原始字节数组
	 * @param key 符合AES要求的密钥
	 * @param mode Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	private static byte[] aes(byte[] input, byte[] key, int mode) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, AES);
			Cipher cipher = Cipher.getInstance(AES);
			cipher.init(mode, secretKey);
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 生成AES密钥,返回字节数组, 默认长度为128位(16字节).
	 */
	public static byte[] generateAesKey() {
		return generateAesKey(DEFAULT_AES_KEYSIZE);
	}

	/**
	 * 生成AES密钥,可选长度为128,192,256位.
	 */
	public static byte[] generateAesKey(int keysize) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
			keyGenerator.init(keysize);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

	
	
    
	public static void main(String[] args){
//		byte [] key =  generateAesKey();
//		System.out.println(key);
//		String p2pKey2 = Base64.encodeToString(key, Base64.NO_PADDING);
//		System.out.println(p2pKey2);
//		String d = ProcessMessage.Base64Encode(key);
//		System.out.println(d);
//		byte [] f = ProcessMessage.Base64Decode(d);
//		System.out.println(f);



		String miwen = "vGN7ZgyFrjAAF+jpk4xPtS67tjg8ufVOubCoGGXvMDrKACwumZ/bH09KVDBmcow8";
		String mingwen = P2PAesCryptos.aesDecrypt(miwen);
		System.out.println("mingwen           :" + mingwen);
		
		String input = "hello 世界杯";
		String encryptResult = P2PAesCryptos.aesEncrypt(input);
		String descryptResult = P2PAesCryptos.aesDecrypt(encryptResult);
		System.out.println("aes key in hex            :" + encryptResult);
		System.out.println("aes encrypt in hex result :" + descryptResult);
		//assertThat(descryptResult).isEqualTo(input);
	}
}