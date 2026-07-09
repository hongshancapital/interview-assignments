package com.domain.utils;
import java.security.MessageDigest;

/**
 *  MD5工具类
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
public class MD5 {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
		"e", "f" };

	private final static String DEFAULT_CHARSET = "UTF-8";
	/**
	 * 转换字节数组为16进制字串
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * J 转换byte到16进制
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 编码
	 *
	 * @param origin
	 * @return  md5
	 */
	public static String MD5Encode(String origin) {
		return MD5Encode(origin,DEFAULT_CHARSET);
	}
	/**
	 * 编码 指定字符级
	 * @param origin  charsetName
	 * @return  md5
	 */
	public static String MD5Encode(String origin, String charsetName) {
		origin = origin.trim();
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetName)));
		} catch (Exception ex) {
		}
		return resultString;
	}


}  
