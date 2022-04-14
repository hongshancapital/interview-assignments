package com.shycier.shorturl.service.impl;

import com.shycier.shorturl.service.Generator;
import com.shycier.shorturl.service.ShortUrlService;
import com.shycier.shorturl.service.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 短域名服务
 */
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

	/**
	 * 短域名生成器
	 */
	@Autowired Generator generator;

	/**
	 * 域名映射存储器
	 */
	@Autowired
	Store<String, String> store;

	/**
	 * 根据长域名生成短域名
	 * 存储长/短域名映射关系
	 * 返回短域名
	 * @param longUrl 长域名
	 * @return 短域名
	 */
	@Override
	public String save(String longUrl) {
		//生成短域名
		String shortUrl = generator.generate();
		//存储短域名
		store.save(shortUrl, longUrl);
		//返回短域名
		return shortUrl;
	}

	/**
	 * 根据短域名获取对应长域名
	 * @param shortUrl 短域名
	 * @return 长域名
	 */
	@Override
	public String get(String shortUrl) {
		//从存储中获取长域名并返回
		return store.get(shortUrl);
	}

}
