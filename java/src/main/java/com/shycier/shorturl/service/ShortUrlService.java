package com.shycier.shorturl.service;

/**
 * 短域名服务
 */
public interface ShortUrlService {

	/**
	 * 保存长域名 返回短域名
	 * @param originUrl 长域名
	 * @return 短域名
	 */
	String save(String originUrl);

	/**
	 * 通过短域名 获取长域名
	 * @param shortUrl 短域名
	 * @return 长域名
	 */
	String get(String shortUrl);

}
