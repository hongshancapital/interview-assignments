package com.url.service.demo.service.impl;

import com.url.service.demo.constant.UrlServiceConstant;
import com.url.service.demo.service.UrlTransferService;
import com.url.service.demo.util.UrlServiceUtil;
import org.springframework.stereotype.Service;

@Service
public class UrlTransferServiceImpl implements UrlTransferService {

	@Override
	public String saveUrl(String fullUrl) {
		if (fullUrl == null || "".equals(fullUrl.trim()) || !(fullUrl.startsWith("https://") || fullUrl.startsWith("http://"))) {
			return "invalid input";
		}
		return UrlServiceUtil.saveUrl(fullUrl);
	}

	@Override
	public String getFullUrl(String shortUrl) {
		String fullUrl = UrlServiceConstant.URL_MAP.get(shortUrl);
		return fullUrl == null ? "not found" : fullUrl;
	}
}
