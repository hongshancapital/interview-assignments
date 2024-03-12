package com.liuhuachao.shorturl.util;

import java.util.LinkedHashMap;
import java.util.Map;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.Weighers;

/**
 * 短域名服务 存储类
 * @author liuhuachao
 * @date 2021/12/20
 */
public class StoreUtil {

	/**
	 * 短域名到原始域名映射表最大数量
	 * 为防止 JVM 内存溢出，单机部署时限制最大存储数量
	 * 如果是分布式部署，可使用 MySQL 等关系型数据和 Redis 等分布式内存数据库存储
	 */
	private static final int MAX_STORE_COUNT = 1000000;

	/**
	 * 原始域名到短域名映射表最大数量
	 */
	private static final int MAX_CACHE_COUNT = 10000;

	/**
	 * 短域名到原始域名的映射表
	 * 使用了 google 团队提供的一个容器，可以用来实现一个基于LRU策略的缓存。
	 */
	public static final Map<String, String> SHORT_ORIGIN_URL_MAP = new ConcurrentLinkedHashMap.Builder<String, String>()
			.maximumWeightedCapacity(MAX_STORE_COUNT).weigher(Weighers.singleton()).build();

	/**
	 * 原始域名到短域名的映射表
	 * 用于过滤重复的原始域名
	 * LRU算法：数量超过后移除最先存储的
	 */
	public static final Map<String, String> ORIGIN_SHORT_URL_MAP = new LinkedHashMap() {
		@Override
		protected boolean removeEldestEntry(Map.Entry eldest) {
			return size() > MAX_CACHE_COUNT;
		}
	};

	/**
	 * 存储
	 * @param shortUrl
	 * @param originUrl
	 */
	public static void store(String shortUrl,String originUrl){
		SHORT_ORIGIN_URL_MAP.put(shortUrl, originUrl);
		ORIGIN_SHORT_URL_MAP.put(originUrl, shortUrl);
	}

	/**
	 * 获取短域名
	 * @param originUrl 原始域名
	 * @return 短域名
	 */
	public static String getShortUrl(String originUrl){
		String shortUrl = ORIGIN_SHORT_URL_MAP.get(originUrl);
		return shortUrl;
	}

	/**
	 * 获取原始域名
	 * @param shortUrl 短域名
	 * @return 原始域名
	 */
	public static String getOriginUrl(String shortUrl){
		String originUrl = SHORT_ORIGIN_URL_MAP.get(shortUrl);
		return originUrl;
	}

}
