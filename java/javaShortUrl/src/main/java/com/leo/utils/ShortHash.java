package com.leo.utils;

import com.google.common.hash.Hashing;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * 短hash工具类
 * @author LeoZhang
 *
 */
public class ShortHash {
	/**
	 * 短链中出现字符的集合，共62个字符，即一个短链可以看做一个62进制的数字
	 */
	public static final String SHORT_URL_CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	/**
	 * 短链字符集的第一个字符，左补零的时候需要用
	 */
	public static final char SHORT_URL_CHARSET_ZERO = SHORT_URL_CHARSET.charAt(0);
	/**
	 * 短链中可用字符的个数
	 */
	public static final int SHORT_URL_CHARSET_NUM = SHORT_URL_CHARSET.length();

	/**
	 * 7位短链是一个短hash，短链hash = 原始hash 对 62^7 做取余操作，再转换为62进制数
	 */
	public static final BigInteger SHORT_HASH_DEVIDED = BigInteger.valueOf(62).pow(7);



	/**
	 * 转换为短hash字符串，保证7位
	 * @param originHash
	 * @return
	 */
	public static String toShortHash(String content) {
		//0. 根据内容做hash
		byte[] originHash = Hashing.murmur3_128().hashString(content, StandardCharsets.UTF_8).asBytes();
		
//		byte[] originHash = null;
//		try {
//			originHash = DigestUtils.md5Digest(content.getBytes("utf-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		//1. 保证非负数，否则有问题
		BigInteger originHashNum = new BigInteger(originHash).abs();
		//2. 对 62^7取余，保证小于此值
		BigInteger remainder = originHashNum.remainder(SHORT_HASH_DEVIDED);
		//System.out.println(remainder);
		StringBuilder builder = new StringBuilder(7);
		int index = 0;
		//3. 余数转换为62进制
		while (!remainder.equals(BigInteger.ZERO)) {
			BigInteger[] divideAndReminder = remainder.divideAndRemainder(BigInteger.valueOf(SHORT_URL_CHARSET_NUM));
			builder.append(SHORT_URL_CHARSET.charAt(divideAndReminder[1].intValue()));
			remainder = divideAndReminder[0];
			index++;
		}
		//4. 不足七位左补零
		for (; index < 7; index++) {
			builder.append(SHORT_URL_CHARSET_ZERO);
		}
		//5. 翻转，保证小位在右
		return builder.reverse().toString();
	}

}