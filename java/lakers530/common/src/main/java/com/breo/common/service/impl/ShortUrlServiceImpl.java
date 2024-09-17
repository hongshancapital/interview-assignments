package com.breo.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.breo.common.helper.CacheManagerHelper;
import com.breo.common.helper.SnowFlakeGidHelper;
import com.breo.common.service.ShortUrlService;
import com.breo.common.service.request.GenShortUrlRequest;
import com.breo.common.service.request.GetLongUrlRequest;
import com.breo.common.service.response.GenShortUrlResponse;
import com.breo.common.service.response.GetLongUrlResponse;
import com.breo.common.utils.NumericConvertUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ShortUrlServiceImpl implements ShortUrlService{

	@Autowired
	private SnowFlakeGidHelper snowFlakeGidHelper;
	
	/**
	 * 生成短域名
	 * 
	 * @param request
	 * @return
	 */
	public GenShortUrlResponse genShortUrl(GenShortUrlRequest request) {
		
		GenShortUrlResponse response = new GenShortUrlResponse();
		
		String shortCode = NumericConvertUtil.toOtherNumberSystem(snowFlakeGidHelper.nextId(), 62);
		CacheManagerHelper.put(shortCode, request.getLongUrl());
		
		log.info("longUrl:" + request.getLongUrl() + " -> " + shortCode);
		response.setShortCode(shortCode);
		return response;
		
	}
	
	/**
	 * 获取长域名
	 * 
	 * @param request
	 * @return
	 */
	public GetLongUrlResponse getLongUrl(GetLongUrlRequest request) {
		GetLongUrlResponse response = new GetLongUrlResponse();
		
		String longUrl = (String) CacheManagerHelper.get(request.getShortCode());
		
		log.info("shortCode:" + request.getShortCode() + " -> " + longUrl);
		
		if(longUrl == null) {
			response.setErrorResponse("100", "not found");
			return response;
		}
		
		response.setLongUrl(longUrl);
		return response;
	}
	
	
	
}
