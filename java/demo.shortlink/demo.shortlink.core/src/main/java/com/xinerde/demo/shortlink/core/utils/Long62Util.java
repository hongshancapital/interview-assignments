package com.xinerde.demo.shortlink.core.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 十进制转62进制工具类
 * 
 * @since 2022年5月19日
 * @author guihong.zhang
 * @version 1.0
 */
public class Long62Util {
	private static char[] chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

	/**
	 * 初始化 62 进制数据，索引位置代表字符的数值，比如 A代表10，Z代表61等
	 */
	private static int scale = 62;

	private static char ZERO = '0';

	public static String to62String(long num) {
		return to62String(num, null);
	}

	/**
	 * 将数字转为62进制
	 */
	public static String to62String(long num, Integer length) {
		StringBuilder sb = new StringBuilder();
		int remainder = 0;
		while (num > scale - 1) {
			/** * 对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转（reverse）字符串 */
			remainder = (int) (num % scale);
			sb.append(chars[remainder]);
			num = num / scale;
		}
		sb.append(chars[(int) num]);
		String value = sb.reverse().toString();
		if (length != null) {
			return StringUtils.leftPad(value, length, ZERO);
		} else {
			return value;
		}
	}

	private static int getIndex(char c) {
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == c) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 62进制字符串转为数字
	 */
	public static long longValue(String str) {
		/** * 将 0 开头的字符串进行替换 */
		str = str.replace("^0*", "");
		long num = 0;
		int index = 0;
		for (int i = 0; i < str.length(); i++) {
			/** * 查找字符的索引位置 */
			index = getIndex(str.charAt(i));
			/** * 索引位置代表字符的数值 */
			num += (long) (index * (Math.pow(scale, str.length() - i - 1)));
		}
		return num;
	}
}
