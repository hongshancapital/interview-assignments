package com.liuhuachao.shorturl.service;

import java.util.regex.Pattern;

import com.liuhuachao.shorturl.util.ConvertUtil;
import com.liuhuachao.shorturl.util.IdGeneratorUtil;
import com.liuhuachao.shorturl.util.StoreUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.net.util.URLUtil;

/**
 * 短域名服务 服务类
 * @author liuhuachao
 * @date 2021/12/20
 */
public class ShortUrlService {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(ShortUrlService.class);

	/**
	 * 短域名前缀
	 */
	public static final String PREFIX = "x.y/";

	/**
	 * 短域名最大长度
	 */
	public static final int MAX_LENGTH = 8;

	/**
	 * 获取短链接
	 * @param originUrl
	 * @return
	 */
	public static String getShortUrl(String originUrl) throws Exception {
		checkUrl(originUrl,false);

		String shortUrl = StoreUtil.getShortUrl(originUrl);

		if(StringUtils.isBlank(shortUrl)){
			long id = IdGeneratorUtil.nextId();
			shortUrl = ConvertUtil.convertTo62String(id);
			StoreUtil.store(shortUrl,originUrl);
		}

		/**
		 * 增加前缀
		 */
		shortUrl = PREFIX + shortUrl;

		return shortUrl;
	}

	/**
	 * 获取原始链接
	 * @param shortUrl
	 * @return
	 */
	public static String getOriginUrl(String shortUrl) throws Exception {
		checkUrl(shortUrl,true);

		/**
		 * 去除前缀
		 */
		shortUrl = shortUrl.replace(PREFIX, "");

		String originUrl = StoreUtil.getOriginUrl(shortUrl);
		return originUrl;
	}

	/**
	 * 校验 url
	 * @param url
	 * @throws Exception
	 */
	public static void checkUrl(String url,Boolean isShortUrl) throws Exception {
		if (!isValid(url,isShortUrl)) {
			throw new Exception("不是合法的Url：" + url);
		}
	}

	/**
	 * 是否合法
	 * @param url
	 * @return
	 */
	public static boolean isValid(String url, Boolean isShortUrl) {
		if (StringUtils.isBlank(url)) {
			return false;
		}

		if(isShortUrl){
			if(url.length() > MAX_LENGTH || !url.startsWith(PREFIX)){
				return false;
			}
		}

		return true;
	}
}
