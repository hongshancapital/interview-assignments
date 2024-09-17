package com.breo.common.service;

import com.breo.common.service.request.GenShortUrlRequest;
import com.breo.common.service.request.GetLongUrlRequest;
import com.breo.common.service.response.GenShortUrlResponse;
import com.breo.common.service.response.GetLongUrlResponse;

public interface ShortUrlService {

	/**
	 * 生成短域名
	 * 
	 * @param request
	 * @return
	 */
	public GenShortUrlResponse genShortUrl(GenShortUrlRequest request);
	
	/**
	 * 获取长域名
	 * 
	 * @param request
	 * @return
	 */
	public GetLongUrlResponse getLongUrl(GetLongUrlRequest request);
	
}
