package com.eagle.urlTransformer.util;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.springframework.util.StringUtils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import lombok.extern.slf4j.Slf4j;
/** 域名映射缓存类
 * @ClassName: UrlCache 
 * @Description: 缓存的Caffeine实现版本
 * @author Eagle
 * @date 2022年1月18日 下午6:29:00 
 *  
 */
@Slf4j
public class UrlCaffeineCache {
	//定义最大容量，已LRU策略维护缓存
	private static Cache<String, String> short2LongMap = Caffeine.newBuilder()
		    // 设置最后一次写入经过600s过期
		    .expireAfterAccess(600, TimeUnit.SECONDS)
		    // 初始的缓存空间大小
		    .initialCapacity(Constants.URL_CACHE_SIZE/100)
		    // 缓存的最大条数
		    .maximumSize(Constants.URL_CACHE_SIZE)
		    .build();;
	private static Cache<String, String> long2ShortMap = Caffeine.newBuilder()
			// 设置最后一次写入经过600s过期
			.expireAfterAccess(600, TimeUnit.SECONDS)
			// 初始的缓存空间大小
			.initialCapacity(Constants.URL_CACHE_SIZE/100)
			// 缓存的最大条数
			.maximumSize(Constants.URL_CACHE_SIZE)
			.build();;
				
	/** 
	 * @Title: putUrlPair 
	 * @Description: 将 短链接-长链接 配对存入缓存，依靠concurrentMap及putIfAbsent保证线程安全
	 * @param @param shortUrlArr
	 * @param @param longUrl
	 * @param @return    设定文件  如果返回为空 ，表示shortUrlArr中的备选key 在缓存里已经全部存在了
	 * @return String    返回类型 
	 * @throws 
	 */ 
	public static  String putUrlPair(String[] shortUrlArr , String longUrl) {
		if(!StringUtils.hasLength(longUrl) || shortUrlArr == null || shortUrlArr.length == 0) return null;
		String result = null;
		for(String shortUrlItem : shortUrlArr) {
			if(!StringUtils.hasLength(shortUrlItem)) continue;
			if(short2LongMap.getIfPresent(shortUrlItem) != null) continue;
			String oldLongUrl =short2LongMap.get(shortUrlItem, new Function<String, String>() {
				@Override
				public String apply(String shortUrlItem) {
					short2LongMap.put(shortUrlItem, longUrl);
					return Constants.CACHE_NEW_PUT;//表示此处为新存入的键值对
				}
			});
//			String oldLongUrl = short2LongMap.putIfAbsent(shortUrlItem, longUrl);//如果返回oldLongUrl不为空，只有一种可能：并发情况下，有线程优先插入了这个键值对
			if(!Constants.CACHE_NEW_PUT.equals(oldLongUrl)) continue; //非新存入，表示此key之前已经存在于缓存中
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
		if(!StringUtils.hasLength(longUrl)) throw new RuntimeException("请输入正确的url字符串");
		return long2ShortMap.getIfPresent(longUrl);
	}
	
	public static String getLongUrlByShort(String shortUrl) {
		if(!StringUtils.hasLength(shortUrl)) return null;
		return short2LongMap.getIfPresent(shortUrl);
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
		String oldValue = long2ShortMap.get(longUrl, new Function<String, String>() {
			@Override
			public String apply(String longUrl) {
				short2LongMap.put(longUrl, Constants.NULL_STR);
				return Constants.CACHE_NEW_PUT;//表示此处为新存入的键值对
			}
		});
		return Constants.CACHE_NEW_PUT.equals(oldValue)?true:false ;
	}
	
	/** 
	 * @Title: putLongUrl 
	 * @Description: 将长、短链接配对 放入缓存
	 * @param @param longUrl 长链接
	 * @param @return    设定文件 
	 * @return boolean    
	 * @throws 
	 */ 
	public static void putLongUrl(String longUrl, String shortUrl) {
		 long2ShortMap.put(longUrl, shortUrl);
		
	}
	
	/** 
	 * @Title: removeLongUrl 
	 * @Description: 从缓存中移除制定的长链接
	 * @param @param longUrl
	 * @param @return    设定文件 
	 * @return boolean    返回类型 
	 * @throws 
	 */ 
	public static void removeLongUrl(String longUrl) {
		if(!StringUtils.hasLength(longUrl)) return ;
		 long2ShortMap.invalidate(longUrl);
	}
	
	public static String getShortUrlByLongUrl(String longUrl) {
		if(!StringUtils.hasLength(longUrl)) return null;
		return long2ShortMap.getIfPresent(longUrl);
	}
	
	
}
