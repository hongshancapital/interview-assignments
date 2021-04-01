package com.sequoiacap.utils;

import java.security.Key;
import java.security.MessageDigest;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.axis.encoding.Base64;

/**
 * 密码相关的辅助类
 */
public class CryptoHelper
{
	/**
	 * 将字节数组格式化成16进制字符串
	 * 
	 * @param data 字节数组
	 * @return　16进制字符串
	 */
	public static String formatBytes(byte[] data)
	{
		StringBuilder builder = new StringBuilder();

		for(int index = 0; index != data.length; ++index)
		{
			builder.append(String.format("%02x", data[index]));
		}

		return builder.toString();
	}

	/**
	 * 将字符串作摘要并生成Base64字符串
	 * 
	 * @param plaintext　明文字符串
	 * @param encoding　字符串的编码格式
	 * @param algorithm　摘要算法
	 * @return　Base64字符串
	 */
	public static String digestBase64(
		String plaintext, String encoding, String algorithm)
	{
		String data = null;

		try
		{
			data = Base64.encode(
				digest(plaintext.getBytes(encoding), algorithm));
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return data;
	}
	
	/**
	 * 将字符串作摘要并生成16进制字符串
	 * 
	 * @param plaintext　明文字符串
	 * @param encoding　字符串的编码格式
	 * @param algorithm　摘要算法
	 * @return　16进制字符串
	 */
	public static String digest(
		String plaintext, String encoding, String algorithm)
	{
		String data = null;

		try
		{
			data = formatBytes(digest(plaintext.getBytes(encoding), algorithm));
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return data;
	}
	
	/**
	 * 对字节数组生成摘要
	 *
	 * @param plaintext 明文字节数组
	 * @param algorithm　摘要算法
	 * @return　摘要数据
	 */
	public static byte[] digest(byte[] plaintext, String algorithm)
	{
		byte[] data = null;

		try
		{
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			digest.update(plaintext);

			data = digest.digest();
		} catch(Exception e)
		{
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * 加密
	 * 
	 * @param plaintext 明文字符串
	 * @param encoding　字符串的编码
	 * @param keySpec　密钥
	 * @param algorithm　加密算法
	 * @return　Base64格式的密文
	 */
	public static String encrypt(
		String plaintext, String encoding, KeySpec keySpec, String algorithm)
	{
		String data = null;
		
		try
		{
			data = Base64.encode(
				encrypt(plaintext.getBytes(encoding), keySpec, algorithm));
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return data;
	}

	/**
	 * 加密
	 * 
	 * @param plaintext 明文
	 * @param keySpec　密钥
	 * @param algorithm　加密算法
	 * @return　密文
	 */
	public static byte[] encrypt(
		byte[] plaintext, KeySpec keySpec, String algorithm)
	{
		byte[] data = null;
		
		try
		{
			SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
			
			Key key = factory.generateSecret(keySpec);
			
			Cipher cipher = Cipher.getInstance(algorithm);
			
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			data = cipher.doFinal(plaintext);
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 解密
	 * 
	 * @param plaintext Base64格式的密文
	 * @param encoding　字符串的编码
	 * @param keySpec　密钥
	 * @param algorithm　加密算法
	 * @return　明文
	 */
	public static String decrypt(
		String data, String encoding, KeySpec keySpec, String algorithm)
	{
		String plaintext = null;
		
		try
		{
			plaintext =
				new String(decrypt(
					Base64.decode(data), keySpec, algorithm), encoding);
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return plaintext;
	}
	
	/**
	 * 解密
	 * 
	 * @param plaintext 密文
	 * @param keySpec　密钥
	 * @param algorithm　加密算法
	 * @return　明文
	 */
	public static byte[] decrypt(
		byte[] data, KeySpec keySpec, String algorithm)
	{
		byte[] plaintext = null;
		
		try
		{
			SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
			
			Key key = factory.generateSecret(keySpec);
			
			Cipher cipher = Cipher.getInstance(algorithm);
			
			cipher.init(Cipher.DECRYPT_MODE, key);
			
			plaintext = cipher.doFinal(data);
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		return plaintext;
	}

	/**
	 * 生成Des算法的密钥
	 * 
	 * @param payload 密钥数量
	 * @return　Des密钥
	 */
	public static KeySpec desKeySpec(String payload)
	{
		try
		{
			return new DESKeySpec(payload.getBytes("utf-8"));
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
