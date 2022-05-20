package com.url.service.demo.util;

import com.url.service.demo.constant.UrlServiceConstant;

public class UrlServiceUtil {

	private UrlServiceUtil() {
		throw new IllegalStateException("Utility class");
	}


	// 使用md5或随机算法
	public static String saveUrl(String fullUrl) {
		String md5Url = getMd5Url(fullUrl);
		return md5Url == null ? getRandomUrl(fullUrl) : md5Url;
	}

	public static String getMd5Url(String fullUrl) {
		// 尝试将shortUrl作为key插入URL_MAP
		for (String shortUrl : AlgorithmUtil.md5Url(fullUrl.trim())) {
			if (UrlServiceConstant.URL_MAP.get(shortUrl) == null) {
				UrlServiceConstant.URL_MAP.put(shortUrl, fullUrl);
				return UrlServiceConstant.URL_PRE + shortUrl;
			} else if (UrlServiceConstant.URL_MAP.get(shortUrl).equals(fullUrl)) {
				return UrlServiceConstant.URL_PRE + shortUrl;
			}
		}
		return null;
	}

	// 一直尝试随机直到不冲突
	public static String getRandomUrl(String fullUrl) {
		int count = 0;
		while (true) {
			String shortUrl = AlgorithmUtil.randomUrl();
			// 由于返回值, 不能改为Map.computeIfAbsent()
			if (!UrlServiceConstant.URL_MAP.containsKey(shortUrl)) {
				UrlServiceConstant.URL_MAP.put(shortUrl, fullUrl.trim());
				return UrlServiceConstant.URL_PRE + shortUrl;
			}
			count++;
			if (count == Integer.MAX_VALUE) {
				break;
			}
		}
		return "save failed";
	}


}
