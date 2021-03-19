package com.jinchunhai.shorturl.service;

public interface ShortUrlService {
		
	/**
	 * @Description 生成短域名
	 * @author JinChunhai
	 * @param longUrl 长域名
	 * @return 短域名
	 */
	String createShortUrl(String longUrl);
	
	/**
	 * @Description 获取长域名
	 * @author JinChunhai
	 * @param shortUrl 短域名
	 * @return 长域名
	 */
	String getLongUrl(String shortUrl);
	
}
