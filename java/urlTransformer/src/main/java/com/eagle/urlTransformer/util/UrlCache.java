package com.eagle.urlTransformer.util;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

import lombok.extern.slf4j.Slf4j;

/** 域名映射缓存类
 * @ClassName: UrlCache 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Eagle
 * @date 2022年1月18日 下午6:29:00 
 *  
 */
@Slf4j
public class UrlCache {
	//定义最大容量，已LRU策略维护缓存
	private static Map<String,String> short2LongMap = new ConcurrentLinkedHashMap.Builder<String, String>()
    .maximumWeightedCapacity(Constants.URL_CACHE_SIZE).build();
	
	private static Map<String,String> long2ShortMap = new ConcurrentLinkedHashMap.Builder<String, String>()
		    .maximumWeightedCapacity(Constants.URL_CACHE_SIZE).build();
	
//	private static List<String> shortUrlUseLog = new ArrayList<String>();
	
	/** 
	 * @Title: putUrlPair 
	 * @Description: 将 短链接-长链接 配对存入缓存，依靠concurrentMap及putIfAbsent保证线程安全
	 * @param @param shortUrlArr
	 * @param @param longUrl
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws 
	 */ 
	public static  String putUrlPair(String[] shortUrlArr , String longUrl) {
		if(!StringUtils.hasLength(longUrl) || shortUrlArr == null || shortUrlArr.length == 0) return null;
		String result = null;
		for(String shortUrlItem : shortUrlArr) {
			if(!StringUtils.hasLength(shortUrlItem)) continue;
			if(short2LongMap.containsKey(shortUrlItem)) continue;
			String oldLongUrl = short2LongMap.putIfAbsent(shortUrlItem, longUrl);//如果返回oldLongUrl不为空，只有一种可能：并发情况下，有线程优先插入了这个键值对
			if(oldLongUrl != null) continue;
			result = shortUrlItem;
			break;
		}
		log.debug("=============>shortUrl="+result);
		//返回有可能为null --> TODO 要么就是并发情况，要么就是生成的key都被占用了
		return result;
	}
	
	/** 
	 * @Title: isLongUrlExists 
	 * @Description: 判断是否在缓存里已经存储了此长链接映射
	 * @param @param longUrl
	 * @param @return    设定文件 
	 * @return String    在缓存里已经存储了此长链接映射，则返回对应的短链接，否则返回null
	 * @throws 
	 */ 
	public static String isLongUrlExists(String longUrl) {
		String result = null;
		if(!StringUtils.hasLength(longUrl)) throw new RuntimeException("请输入正确的url字符串");
		if(!short2LongMap.values().contains(longUrl)) return result;
		for(Entry<String, String> entry : short2LongMap.entrySet()) {
			if(entry.getValue().equals(longUrl)) {
				result = entry.getKey();
				break;
			}
		}
		return result;
	}
	
	public static String getLongUrlByShort(String shortUrl) {
		if(!StringUtils.hasLength(shortUrl)) return null;
		return short2LongMap.get(shortUrl);
	}
	
	
	/** 
	 * @Title: putLongUrl 
	 * @Description: 将长链接放入缓存
	 * @param @param longUrl 长链接
	 * @param @return    设定文件 
	 * @return boolean    返回true表示成功将longUrl放入缓存(value是 "")，返回false表示缓存已存在此longUrl
	 * @throws 
	 */ 
	public static boolean putLongUrl(String longUrl) {
		String oldValue = long2ShortMap.putIfAbsent(longUrl, Constants.NULL_STR);
		return oldValue==null?true:false ;
	}
	
	/** 
	 * @Title: putLongUrl 
	 * @Description: 将长、短链接配对 放入缓存
	 * @param @param longUrl 长链接
	 * @param @return    设定文件 
	 * @return String   由于本次插入是替换前期存放的空字符串，因此 返回 Constants.NULL_STR
	 * @throws 
	 */ 
	public static String putLongUrl(String longUrl, String shortUrl) {
		return long2ShortMap.put(longUrl, shortUrl);
		
	}
	
	/** 
	 * @Title: removeLongUrl 
	 * @Description: 从缓存中移除制定的长链接
	 * @param @param longUrl
	 * @param @return    设定文件 
	 * @return boolean    返回类型 
	 * @throws 
	 */ 
	public static boolean removeLongUrl(String longUrl) {
		return long2ShortMap.remove(longUrl, Constants.NULL_STR);
	}
	
	public static String getShortUrlByLongUrl(String longUrl) {
		if(!StringUtils.hasLength(longUrl)) return null;
		return long2ShortMap.get(longUrl);
	}
	
}
