package scdt.interview.java.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import lombok.extern.slf4j.Slf4j;
import scdt.interview.java.common.algorithm.Numberic;
import scdt.interview.java.common.utils.LRUMapCache;
import scdt.interview.java.exception.ShortenException;

@Service
@Slf4j
public class ShortenService {

	private static final int RAD_SET = 62;
	@Resource
	private LRUMapCache<String, String> shortCacheMap;

	@Resource
	private LRUMapCache<String, String> longMD5CacheMap;

	@Resource
	private SequenceService sequenceService;

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * 生成短域名 步骤：检查长域名是否重复，由长域名MD5作为key； 生成短域名
	 * 
	 * @param longURL (String, 长域名)
	 * @return shortURL (String, 短域名)
	 */
	public String generate(String longUrl) {
		try{
			URL url = new URL(longUrl);
		}catch (MalformedURLException e) {
			throw new ShortenException(RAD_SET, "不合法");
		}
		
		String longMD5key = DigestUtils.md5DigestAsHex(longUrl.getBytes());
		try {
			lock.writeLock().lock();
			String shortKey = longMD5CacheMap.get(longMD5key);
			if (StringUtils.isNotBlank(shortKey)) {
				return shortKey;
			}

			shortKey = getShortKey();
			shortCacheMap.put(shortKey, longUrl);
			longMD5CacheMap.put(longMD5key, shortKey);
			return shortKey;
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 
	 * @param shortUrl
	 * @return
	 */
	public String getLongURL(String shortKey) {
		try {
			lock.readLock().lock();
			return shortCacheMap.get(shortKey);
		} finally {
			lock.readLock().unlock();
		}
	}

	private String getShortKey() {
		long seq = sequenceService.getNextSeq();
		String shortKey = Numberic.getRadStr(seq, RAD_SET, true);
		return shortKey;
	}
}
