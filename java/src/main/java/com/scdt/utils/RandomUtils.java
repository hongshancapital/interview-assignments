package com.scdt.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 随机数处理工具类
 */
public class RandomUtils {
	static Random rd = new Random();
	private final static char[] lower = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z' };
	private final static char[] upper = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z' };
	private final static char[] number = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * 根据指定位数生成随机字符串
	 *
	 * @param length
	 * @return
	 */
	public static String getRandom(int length) {
		String s = "";
		for (int i = 0; i < length; i++) {
			int len = i % 3;
			if (len == 0) {
				s += lower[((int) (Math.random() * 26))];
			} else if (len == 1) {
				s += upper[((int) (Math.random() * 26))];
			} else {
				s += getNumberRandom(1);
			}
		}
		return outOfOrder(s);
	}

	/**
	 * 乱序输出
	 *
	 * @param in
	 * @return
	 */
	public static String outOfOrder(String in) {
		List<String> list = Arrays.asList(in.split(""));
		Collections.shuffle(list);
		String out = new String();
		for (String s : list) {
			out += s;
		}
		return out;
	}

	/**
	 * 获取指定位数的数字随机数
	 *
	 * @param length 指定长度
	 * @return
	 */
	public static String getNumberRandom(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			char s = number[rd.nextInt(10)];
			sb.append(s);
		}
		return outOfOrder(sb.toString());
	}
}
