package com.liuhuachao.shorturl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 短域名服务 转换类
 * @author liuhuachao
 * @date 2021/12/20
 */
public class ConvertUtil {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(ConvertUtil.class);

	/**
	 * 初始化 62 进制数据
	 */
	private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	/**
	 * 62 进制
	 */
	private static final int SCALE = 62;

	/**
	 * 将数字转为 62 进制字符串
	 * @param num    Long 型数字
	 * @return 62进制字符串
	 */
	public static String convertTo62String(long num) {
		StringBuilder sb = new StringBuilder();
		int remainder = 0;

		while (num > SCALE - 1) {
			remainder = Long.valueOf(num % SCALE).intValue();
			sb.append(CHARS.charAt(remainder));
			num = num / SCALE;
		}

		sb.append(CHARS.charAt(Long.valueOf(num).intValue()));
		String returnStr = sb.reverse().toString();
		return returnStr;
	}

	/**
	 * 将 62 进制字符串转为数字
	 * @param str 编码后的 62进制字符串
	 * @return 解码后的 10 进制字符串
	 */
	public static long convertToDecimalNumber(String str) {
		long num = 0;
		int index = 0;
		for (int i = 0; i < str.length(); i++) {
			/**
			 * 查找字符的索引位置
			 */
			index = CHARS.indexOf(str.charAt(i));
			/**
			 * 索引位置代表字符的数值
			 */
			num += (long) (index * (Math.pow(SCALE, str.length() - i - 1)));
		}

		return num;
	}
}
