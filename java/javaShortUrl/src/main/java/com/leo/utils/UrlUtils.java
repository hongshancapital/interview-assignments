package com.leo.utils;

//import java.net.URI;
//import java.net.URISyntaxException;

/**
 * url工具类
 * 
 * @author LeoZhang
 *
 */
public class UrlUtils {

	/**
	 * url中是否包含协议头
	 * 
	 * @param url
	 * @return
	 */
	public static boolean hasProtocal(String url) {
		// 是否含 :// 并且不能再最开始出现
		if (url.indexOf("://") > 0) {
			return true;
		}
		return false;
	}

//	/**
//	 * 是否合法的uri
//	 * @param input
//	 * @return
//	 */
//	public static boolean isURI(String input) {
//		try {
//			new URI(input);
//		} catch (URISyntaxException e) {
//			return false;
//		}
//		return true;
//		
//	}

	/**
	 * 判断一个字符串是否为url
	 * 
	 * @param str
	 * @return boolean
	 **/
	public static boolean isURI(String str) {
		// 转换为小写
		str = str.toLowerCase();
		String regex = "^((https|http|ftp|rtsp|mms)?://)" // https、http、ftp、rtsp、mms
				+ "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
				+ "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 例如：199.194.52.184
				+ "|" // 允许IP和DOMAIN（域名）
				+ "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
				+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
				+ "[a-z]{2,6})" // first level domain- .com or .museum
				+ "(:[0-9]{1,5})?" // 端口号最大为65535,5位数
				+ "((/?)|" // a slash isn't required if there is no file name
				+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
		return str.matches(regex);
	}

	/**
	 * 把url的协议和域名部分转小写
	 * 
	 * @param url
	 * @return
	 */
	public static String urlToLowerCase(String uri) {
		int index1 = uri.indexOf("://");
		int index2 = uri.indexOf('/', index1 + 3);
		if (index2 == -1) {
			index2 = uri.length() - 1;
		}
		String formated = uri.substring(0, index1).toLowerCase() + uri.substring(index1, index2).toLowerCase()
				+ uri.substring(index2);

		return formated;
	}

}
