package hongshan.demo.utils;

/**
 * 文本处理工具类
 * @class hongshan.demo.utils.TextUtils
 * @author 许坤
 * @email klxukun@126.com
 * @time 2021年10月18日 下午10:16:57
 *
 */
public final class TextUtils {

	private TextUtils() {
		
	}
	/**
	 * 判断给定的字符串是否为空串
	 * @param source 字符串
	 * @return 如果为null或者长度为0则，返回true，否则返回false
	 */
	public static boolean empty(String source) {
		return source==null||source.length()==0;
	}
	
}
