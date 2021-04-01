package com.sequoiacap.cache.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis缓存工具类
 */
public class CacheUtils
{
	/**
	 * 自解式自旋争抢分布式锁
	 */
	public static <K, V>
		Boolean lockSpinning(
			Runnable runable,
			RedisTemplate<K, V> template, K key, V value, 
			Long timeout, TimeUnit unit, Long spiningTime)
	{
		if (!lockSpinning(template, key, value, timeout, unit, spiningTime))
			return false;

		try
		{
			runable.run();
		} finally
		{
			template.delete(key);
		}

		return true;
	}

	/**
	 * 自旋争抢分布式锁
	 */
	public static <K, V>
		Boolean lockSpinning(
			RedisTemplate<K, V> template, K key, V value, 
			Long timeout, TimeUnit unit, Long spiningTime)
	{
		if (spiningTime == null)
			spiningTime = Long.MAX_VALUE;

		long startTime = System.currentTimeMillis();

		while(true)
		{
			if (lock(template, key, value, timeout, unit))
				return true;

			long time = System.currentTimeMillis() - startTime;
			if (time >= spiningTime)
				break;
		}

		return false;
	}

	/**
	 * 争抢分布式锁
	 *
	 * @param template redis对象
	 * @param key 锁的名字
	 * @param value 争抢成功后，赋给锁的值
	 * @param timeout 锁存在的有效时长. 可以为空
	 * @param unit 锁存在的有效时长单位
	 *
	 * @return 是否争抢成功
	 */
	public static <K, V>
		Boolean lock(
			RedisTemplate<K, V> template, K key, V value, 
			Long timeout, TimeUnit unit)
	{
		if (!template.opsForValue().setIfAbsent(key, value))
			return false;

		if (timeout != null && unit != null)
			template.expire(key, timeout, unit);
		
		return true;
	}
	
	/**
	 * 争抢分布式锁
	 *
	 * @param template redis对象
	 * @param key 锁的名字
	 * @param value 争抢成功后，赋给锁的值
	 *
	 * @return 是否争抢成功
	 */
	public static <K, V>
		Boolean lock(
			RedisTemplate<K, V> template, K key, V value)
	{
		if (!template.opsForValue().setIfAbsent(key, value))
			return false;

		return true;
	}
}
