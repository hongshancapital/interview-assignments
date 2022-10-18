package com.link.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.link.utils.SnowFlake;
/**
 * <p>Long short address translation service class
 * @author lizeqiang
 */
@Service
public class LinkService {
	/**
	 * Temporarily simulate ID generator with snowflake algorithm
	 */
	private static SnowFlake snowFlake = new SnowFlake(1L,1L);
	/**
	 * Simulate the Redis interface model
	 */
	private static Map<String, Object> redisCache = new HashMap<String,Object>();
	/**
	 * Suppose a short address domain name template looks like this.
	 */
	private static String SHORT_URL_TPL = "http://link.com/short/%s";
	
	/**
	 * Limit the maximum length to 8 characters
	 */
	private static long MAX_SHORT_LIMIT_TPL = 9999999999999L;
	
	/**
	 * IDs pool
	 */
	private static volatile ConcurrentLinkedQueue<Long> queuePool = new ConcurrentLinkedQueue<Long>();

	public String getShortUrl(String longUrl) {
		Long id = getOneFromIdPool();
		String base62Encode = base62Encode(id);
		redisCache.put(base62Encode, longUrl);
		return String.format(SHORT_URL_TPL, base62Encode);
	}
	
	public String getLongUrl(String shortUrl) {
		String base62EnCode = shortUrl.substring(shortUrl.lastIndexOf("/")+1, shortUrl.length());
		// from redis cache get the short url
		return (String)redisCache.get(base62EnCode);
	}
	
	/**
	 * <p>Get an ID from the ID pool
	 * <p>The ID pool usually uses the self increasing feature of Redis to develop a distributed ID generator and put the generated IDs into the pool in batches for business use. Here, we only use java to do a simulation.
	 * @return
	 */
	public Long getOneFromIdPool() {
		if (queuePool.size() <= 0) {
			synchronized(this) {
				// Redis can return a batch of self increasing IDs, which are only simulated here 
				for (int i = 0;i<100;i++) {
					queuePool.add(snowFlake.nextId()%MAX_SHORT_LIMIT_TPL);
				}
			}
		}
		if (queuePool.size() > 0) {
			return (Long)queuePool.poll();
		}
		return snowFlake.nextId()%MAX_SHORT_LIMIT_TPL;
	}
	
	/**
	 * <p>10 base to 62 base
	 * @param num
	 * @return
	 */
	public String base62Encode(long num) {
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder();
		int remainder = 0;
		int scale = 62;
		while (num > scale - 1) {
			remainder = Long.valueOf(num % scale).intValue();
			sb.append(chars.charAt(remainder));
			num = num / scale;
		}
		sb.append(chars.charAt(Long.valueOf(num).intValue()));
		String value = sb.reverse().toString();
		return StringUtils.leftPad(value, 6, "0");
	}

//	/**
//	 * <p>62 base to 10 base
//	 * @param str
//	 * @return
//	 */
//	public long base62Decode(String str) {
//		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
//		int scale = 62;
//		str = str.replace("^0*", "");
//		long num = 0;
//		int index = 0;
//		for (int i = 0; i < str.length(); i++) {
//			index = chars.indexOf(str.charAt(i));
//			num += (long) (index * (Math.pow(scale, str.length() - i - 1)));
//		}
//		return num;
//	}
	
//	public static void main(String[] args) {
//		LinkService service = new LinkService();
//		String shortUrl = service.getShortUrl("https://www.hszb.com/aaaaaaaaaaa/bbbbbbbbbbbbb/ccccccccccc/dddddddddd/eeeee/ffff/long/url");
//		System.out.println("短地址："+shortUrl);
//		System.out.println("长地址:"+service.getLongUrl(shortUrl));
//	}
}
