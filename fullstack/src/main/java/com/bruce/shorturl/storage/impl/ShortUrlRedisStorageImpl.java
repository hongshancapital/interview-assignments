package com.bruce.shorturl.storage.impl;

import com.bruce.shorturl.storage.IShortUrlStorage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * 短域名 redis storage
 * @author bruce
 */
@Slf4j
public class ShortUrlRedisStorageImpl implements IShortUrlStorage, InitializingBean {

	@Value("${shorturl.expireDays}")
	private int expireDays;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override public void afterPropertiesSet() throws Exception {
		Assert.notNull(stringRedisTemplate, "stringRedisTemplate can't be null");
	}

	/**
	 * 判断hash是否存在
	 * @param hash
	 * @return
	 */
	@Override public boolean notExists(String hash) {
		boolean result = loadValueByHash(hash)==null;
		return result;
	}

	/**
	 *
	 * @param hash
	 * @return
	 */
	@Override public String loadValueByHash(String hash) {
		if(hash==null || StringUtils.isBlank(hash)){
			return null;
		}
		ValueOperations<String, String> vop = stringRedisTemplate.opsForValue();
		String result = vop.get(hash);
		return result;
	}

	/**
	 *
	 * @param hash
	 * @param value
	 * @return
	 */
	@Override public String putValueByHash(String hash, String value) {
		if(StringUtils.isBlank(hash) || StringUtils.isBlank(value)){
			return null;
		}
		String key = hash;
		ValueOperations<String, String> vop = stringRedisTemplate.opsForValue();
		vop.set(key, value);

		stringRedisTemplate.expire(key, expireDays, TimeUnit.DAYS);
		String result = value;
		return result;
	}

	/**
	 *
	 * @param hash
	 */
	@Override public void remove(String hash) {
		stringRedisTemplate.delete(hash);
	}


	/**
	 * 清除缓存
	 */
	@Override public void clearAll() {

	}


}
