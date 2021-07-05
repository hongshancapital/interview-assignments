package com.example.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import com.example.demo.common.CommonUtil;
import com.example.demo.common.Constants;
import com.example.demo.common.RuleException;
import com.example.demo.dto.Repository;

@Service
public class UrlService {

	private static Map<String, Repository> repositoryMap = new HashMap<>();

	public String getNormalUrl(String shortUrl) {
		CommonUtil.validateShortUrlFormat(shortUrl);
		return queryNormalUrl(shortUrl);
	}

	public String getShortUrl(String normalUrl) {
		CommonUtil.validateUrlFormat(normalUrl);
		return createShortUrl(normalUrl);
	}

	private String createShortUrl(String normalUrl) {
		String key = CommonUtil.createKey(normalUrl);
		String shortUrl = StringUtils.join(Constants.BASE_DOMAIN, key);
		putRepository(key, normalUrl, shortUrl);
		return shortUrl;
	}

	private void putRepository(String key, String normalUrl, String shortUrl) {
		Repository repository = new Repository();
		repository.setNormalUrl(normalUrl);
		repository.setShortUrl(shortUrl);
		repository.setExpires(DateFormatUtils.format(new Date(), Constants.DATE_FORMAT));
		repositoryMap.put(key, repository);
	}

	private String queryNormalUrl(String shortUrl) {
		Repository repository = repositoryMap.get(StringUtils.substringAfterLast(shortUrl, "/"));
		if (repository == null) {
			throw new RuleException(Constants.ErrorCode.URL_MAPPING_NOT_EXIST);
		}
		return CommonUtil.urlDecode(repository.getNormalUrl());
	}
}
