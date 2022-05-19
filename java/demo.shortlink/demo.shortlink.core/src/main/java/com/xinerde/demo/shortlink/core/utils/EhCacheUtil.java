package com.xinerde.demo.shortlink.core.utils;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * EhCache工具类
 * 
 * @since 2022年5月19日
 * @author guihong.zhang
 * @version 1.0
 */
public class EhCacheUtil {

	private static final Logger log = LoggerFactory.getLogger(EhCacheUtil.class);

	private CacheManager manager;
	private static EhCacheUtil ehCache;

	public static final String MY_CACHE = "myCache";

	/**
	 * 获得缓存配置管理
	 * 
	 * @param inputStream
	 */
	private EhCacheUtil(InputStream inputStream) {
		try {
			manager = CacheManager.create(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取配置文件错误：{}", e.getMessage());
		}
	}

	/**
	 * 初始化缓存管理类
	 * 
	 * @return
	 */
	public static EhCacheUtil getInstance() {
		try {
			// 打包部署必须通过流的方式获取配置文件
			ClassPathResource classPathResource = new ClassPathResource("conf/ehcache.xml");
			InputStream inputStream = classPathResource.getInputStream();
			if (ehCache == null) {
				ehCache = new EhCacheUtil(inputStream);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("初始化错误：{}", e.getMessage());
		}
		return ehCache;
	}

	/**
	 * 获取Cache类
	 * 
	 * @param cacheName
	 * @return
	 */
	public Cache getCache(String cacheName) {
		return manager.getCache(cacheName);
	}

	/**
	 * 添加缓存数据
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public boolean put(String cacheName, String key, Object value) {
		try {
			Cache cache = manager.getCache(cacheName);
			Element element = new Element(key, value);
			cache.put(element);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("添加缓存失败：{}", e.getMessage());
			return false;
		}
	}

	/**
	 * 获取缓存数据
	 * 
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public Object get(String cacheName, String key) {
		try {
			Cache cache = manager.getCache(cacheName);
			Element element = cache.get(key);
			return element == null ? null : element.getObjectValue();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取缓存数据失败：{}", e.getMessage());
			return null;
		}
	}

	/**
	 * 删除缓存数据
	 * 
	 * @param cacheName
	 * @param key
	 */
	public boolean delete(String cacheName, String key) {
		try {
			Cache cache = manager.getCache(cacheName);
			cache.remove(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除缓存数据失败：{}", e.getMessage());
			return false;
		}
	}

	/**
	 * 删除所有缓存数据
	 * 
	 * @param cacheName
	 */
	public void delete(String cacheName) {
		try {
			Cache cache = manager.getCache(cacheName);
			cache.removeAll();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除缓存数据失败：{}", e.getMessage());
		}
	}

	public static void main(String[] args) {
		EhCacheUtil ehCacheUtil = EhCacheUtil.getInstance();
		ehCacheUtil.put(EhCacheUtil.MY_CACHE, "name", "zhangsan");

		String name = (String) ehCacheUtil.get(EhCacheUtil.MY_CACHE, "name");
		System.out.println(name);
	}

}