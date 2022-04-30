package com.hs.wl.manager;


import com.hs.wl.utils.LruCache;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UrlTransferManager {
	//初始化LRU缓存，并默认大小，超出大小后，删除最近最少使用的元素
	private final LruCache urlCache = new LruCache(1000);
	public final char[] chars = new char[] {
			'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z'
	};

	/**
	 * 将长链接转换为短链接
	 * @param longUrl 长链接
	 * @return
	 */
	public String parseLongUrlToShortUrl(String longUrl) {
		//md5加密获取32位字符串
		String md5Str = DigestUtils.md5Hex(longUrl);

		//将32位md5加密串转换为6位字符短串
		String cacheKey = parseMd5StringToShortKey(md5Str);

		//获取短链接对应的url集合
		List<String> urls = getUrlsByKey(cacheKey);

		//校验短串对应的长链接中是否已包含此次传入的长链接
		int cacheIdx = -1;
		if (urls==null) {
			//短链接未命中长链接，则创建新的长链接集合，定义链接索引为0
			List<String> values = new ArrayList<>();
			values.add(longUrl);
			urlCache.put(cacheKey, values);
			cacheIdx = 0;
		} else {
			//短链接命中了长链接缓存
			//md5加密后的短串碰撞，判断长链接是否已存在于缓存中
			for (int j = 0; j < urls.size(); j++) {
				if (urls.get(j).equals(longUrl)) {
					//长链接已存在于缓存中
					cacheIdx = j;
				}
			}

			//长链接通过md5加密后的字符串与其他长链接产生了碰撞
			if (cacheIdx==-1) {
				//长链接存入缓存，并记录索引
				urls.add(longUrl);
				cacheIdx = urls.size()-1;
			}
		}

		//将 短链接+"_"+索引 拼接，当作当前长链接对外展示的短链接
		return cacheKey + "_" + cacheIdx;
	}

	/**
	 * 通过短链接获取长链接
	 * @param shortUrl
	 * @return
	 */
	public String getLongUrlByShortUrl(String shortUrl) {
		String[] shortUrlValues;
		// 错误的短链接
		if (StringUtils.isEmpty(shortUrl)
				||(shortUrlValues = shortUrl.split("_")).length != 2) {
			return null;
		}

		String key = shortUrlValues[0];
		int idx = Integer.parseInt(shortUrlValues[1]);
		List<String> urls = urlCache.get(key);
		if (urls==null || idx>(urls.size()-1)) {
			return null;
		}

		return urls.get(idx);
	}

	/**
	 * 将32位md5加密串转换为6位字符短串
	 * @param md5Str
	 * @return
	 */
	private String parseMd5StringToShortKey(String md5Str){
		//将32位字符串以8位为一组分割为4组,取第一组数据生成短链接
		String str = md5Str.substring(0, 8);
		//将分割后的八位字符串转为16进制数字，保留30位,舍去前2位 （用于等分拆分6组）
		long longValue = Long.parseLong(str, 16) & 0x3FFFFFFF;

		//将转换后的16进制数拆分6组，继续转换位6个chars索引，得到6位短链接
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < 6; j++) {
			//将转换后的16进制数与 0x0000003D进行位与运算，得到对应的 61以内的数值作为chars索引
			long idx = longValue&0x0000003D;
			sb.append(chars[(int) idx]);
			//将转换后的16进制数向右移5位
			longValue>>>=5;
		}

		return sb.toString();
	}

	/**
	 * 通过缓存key获取对应的长链接数组
	 * @param key
	 * @return
	 */
	private List<String> getUrlsByKey(String key) {
		return urlCache.get(key);
	}

}
