package hongshan.demo.storage;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hongshan.demo.storage.fvn.FNV1a32;

/**
 * 域名映射内存管理类
 * 
 * @class hongshan.demo.storage.DomainCache
 * @author 许坤
 * @email klxukun@126.com
 * @time 2021年10月18日 下午10:17:25
 *
 */
public final class DomainCache {

	private static Logger logger = LoggerFactory.getLogger(DomainCache.class);

	/**
	 * 短域名映射
	 * 
	 * *
	 */
	private final static Map<String, String> shortMap;
	/**
	 * 长域名映射
	 */
	private final static Map<String, String> longMap;
	/**
	 * 字符表。一个包含数字，大、小写英文字符的数组，通过该数组提取构成短域名的字符
	 */
	private final static char[] charTable = new char[64];

	static {
		logger.info("正在初始化缓冲区");
		longMap = new ConcurrentHashMap<String, String>();
		shortMap = new ConcurrentHashMap<String, String>();

		logger.info("正在初始化字符表");
		createCharTable();
		logger.info("初始化完成");
	}

	private DomainCache() {
		
	}
	/**
	 * 构造字符表，可用字符包括26个英文字母的大、小写；10个阿拉伯字符以及$和!符号
	 */
	private static void createCharTable() {
		int index = 0;
		for (int num = 48; num <= 57; num++)
			charTable[index++] = (char) num;
		for (int ui = 65; ui <= 90; ui++)
			charTable[index++] = (char) ui;
		for (int li = 97; li <= 122; li++)
			charTable[index++] = (char) li;
		charTable[index++] = '!';
		charTable[index] = '$';
	}

	/**
	 * 根据给定的长域名通过计算获取短域名<br/>
	 * 使用的hash算法有微量的碰撞，需要对重复数进行处理，作为demo，这里忽略
	 * 
	 * @param uri 长域名
	 * @return 返回经过计算得到的短域名
	 * @throws NoSuchAlgorithmException
	 */
	public static String longToShort(String uri) throws NoSuchAlgorithmException {

		logger.debug("正在计算长域名的短域名，长域名：" + uri);
		FNV1a32 fnv = new FNV1a32();
		fnv.init(uri);
		long value = fnv.getHash();

		byte[] bytes = new byte[8];
		for (int i = 0; i < 8; i++)
			bytes[i] = (byte) ((value >> (i * 6) & 0x03f));

		int len = 8;
		for (int i = 8; i > 0; i--) {
			if (bytes[i - 1] > 0)
				break;
			len--;
		}

		String id = "";
		for (int i = 0; i < len; i++)
			id += charTable[bytes[i]];

		logger.debug(String.format("计算完成，短域名ID：%s； 长域名：%s", id, uri));

		return id;
	}

	/**
	 * 根据给定的短域名，获取长域名
	 * 
	 * @param shortUri 短域名
	 * @return 返回映射的长域名，如果没有返回null
	 */
	public static String getLongUri(String shortUri) {
		return shortMap.get(shortUri);
	}

	/**
	 * 根据给定的长域名获取短域名<br/>
	 * 通过长域名映射表提取短域名，如果没有，则根据长域名计算出短域名并存到域名映射表中
	 * 
	 * @param longUri 长域名
	 * @return 返回得到的短域名
	 * @throws NoSuchAlgorithmException
	 */
	public static String getShortUri(String longUri) throws NoSuchAlgorithmException {
		String ret = longMap.get(longUri);
		if (ret != null)
			return ret;

		logger.debug("长域名未收录，计算长域名的短域名ID");
		ret = longToShort(longUri);
		shortMap.put(ret, longUri);
		longMap.put(longUri, ret);

		return ret;
	}

//	public static void main(String[] args) {
//		try {
//			longToShort("http://www.baidu.com/abc");
//			longToShort("http://www.alangyun.com/system/id=123456");
//			longToShort("http://zx.jiaju.sina.com.cn/anli/177404.html");
//			longToShort("https://finance.sina.com.cn/stock/hkstock/marketalerts/2021-10-18/doc-iktzqtyu2103945.shtml");
//		}catch(Exception ex) {
//			ex.printStackTrace();
//		}
//	}
}
