package cn.scdt.shorturl.utils;

import cn.hutool.core.lang.hash.MurmurHash;

/**
 * @Description: URL hash并转换base62
 * @Author: Felix
 * @Date: 2022-03-20
 */
public class HashUtils {
	private static char[] CHARS = new char[]{
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
	};
	private static int SIZE = CHARS.length;

	private static String convertDecToBase62(long num) {
		StringBuilder sb = new StringBuilder();
		while (num > 0 && sb.length() < 8 ) {
			int i = (int) (num % SIZE);
			sb.append(CHARS[i]);
			num /= SIZE;
		}
		return sb.reverse().toString();
	}

	public static String hashToBase62(String str) {
		int i = MurmurHash.hash32(str);
		long num = i < 0 ? Integer.MAX_VALUE - (long) i : i;
		return convertDecToBase62(num);
	}
}