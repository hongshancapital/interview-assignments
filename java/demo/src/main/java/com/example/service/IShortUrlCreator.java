package com.example.service;

/**
 *   @author wenbin
 */
public interface IShortUrlCreator {
	/**
	 * 通过长地址生成短地址
	 * @param longUrl
	 * @return
	 */
	String createShortUrl(String longUrl);
	/**
	 * 通过短地址获取对应长地址
	 * @param shortUrl 短地址
	 * @return
	 */
	String getLongUrl(String shortUrl);
	
}
