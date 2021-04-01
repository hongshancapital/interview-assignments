package com.sequoiacap.cache.utils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

public class JedisUtil
{
	@Autowired
	protected RedisTemplate redisTemplate;

	@Resource(name = "redisTemplate")
	protected ValueOperations<String, String> stringOp;

	@Resource(name = "redisTemplate")
	protected ValueOperations<String, Object> objectOpt;

	@Resource(name = "redisTemplate")
	protected SetOperations<String, String> watchKeys;

	private static final ThreadLocal<String> currentKey =
		new ThreadLocal<String>();

	public static final String PREFIX_LOCK = "lock";
	public static final String PREFIX_CHANNEL = "channel";

	/**
	 * 将键值关联到监控列表中。调用reset方法可将指定监控列表中的键值清空
	 * 
	 * @param names 若干监控列表的名称
	 * @return
	 * 
	 * 将test, test1, test2添到了watch1监控列表中
	 * jedisUtil.setValue("test", "hello world!").watch("watch1");
	 * jedisUtil.setValue("test1", "hello world!").watch("watch1");
	 * jedisUtil.setValue("test2", "hello world!").watch("watch1");
	 * 
	 * 清空监控列表watch1中的键值, test, test1, test2都将得到清空
	 * jedisUtil.reset("watch1");
	 */
	public JedisUtil watch(String ... names)
	{
		for(String name: names)
		{
			watchKey(name);
		}

		return this;
	}

	public void reset(String ... names)
	{
		for(String name: names)
		{
			resetKey(name);
		}
	}

	public void post(String channelName, Object data)
	{
		redisTemplate.convertAndSend(
			String.format("%s-%s", PREFIX_CHANNEL, channelName), data);

		objectOpt.set(
			String.format("%s-%s-message", PREFIX_CHANNEL, channelName),
			data, 1l, TimeUnit.HOURS);
	}

	public Object poll(String channelName)
	{
		return objectOpt.get(
			String.format("%s-%s-message", PREFIX_CHANNEL, channelName));
	}
	
	public String getValue(String key)
	{
		return stringOp.get(key);
	}

	public String getValue(String key, String defVal)
	{
		String value = stringOp.get(key);
		
		if (value == null)
			value = defVal;

		return value;
	}

	public JedisUtil setValue(String key, String value)
	{
		stringOp.set(key, value);
		
		return storeKey(key);
	}

	public JedisUtil setValue(String key, String value, int sec)
	{
		stringOp.set(key, value, sec, TimeUnit.SECONDS);
		
		return storeKey(key);
	}

	public Object getObject(Object key)
	{
		return objectOpt.get(key);
	}

	public JedisUtil setObject(String key, Object value)
	{
		objectOpt.set(key, value);
		
		return storeKey(key);
	}

	public JedisUtil setObject(String key, Object value, int sec)
	{
		objectOpt.set(key, value, sec, TimeUnit.SECONDS);
		
		return storeKey(key);
	}
	
	public byte[] getBuffer(Object key)
	{
		byte[] rawKey = redisTemplate.getKeySerializer().serialize(key);
		
		RedisConnection conn =
			redisTemplate.getConnectionFactory().getConnection();
		
		byte[] rawValue = conn.get(rawKey);
		
		conn.close();
		
		return rawValue;
	}

	public JedisUtil setBuffer(String key, byte[] value)
	{
		byte[] rawKey = redisTemplate.getKeySerializer().serialize(key);
		
		RedisConnection conn =
			redisTemplate.getConnectionFactory().getConnection();
		
		conn.set(rawKey, value);
		
		conn.close();
		
		return storeKey(key);
	}

	public JedisUtil setBuffer(String key, byte[] value, int sec)
	{
		byte[] rawKey = redisTemplate.getKeySerializer().serialize(key);

		RedisConnection conn =
			redisTemplate.getConnectionFactory().getConnection();

		conn.set(rawKey, value);
		conn.expire(rawKey, sec);

		conn.close();

		redisTemplate.expire(key, sec, TimeUnit.SECONDS);

		return storeKey(key);
	}
	
	public <T> void del(T key)
	{
		redisTemplate.delete(key);
	}
	
	
	private JedisUtil storeKey(String key)
	{
		currentKey.set(key);

		return this;
	}

	private void watchKey(String name)
	{
		String key = currentKey.get();

		watchKeys.add(name, key);
		
		redisTemplate.expire(name, 1, TimeUnit.DAYS);
	}

	private void resetKey(String name)
	{
		Set<String> keys = watchKeys.members(name);
		del(name);

		for(String key: keys)
			del(key);
	}
}
