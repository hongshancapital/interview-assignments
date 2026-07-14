package com.shorturl.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shorturl.entity.ResponseDto;
import com.shorturl.enums.ResponseEnum;
import com.shorturl.service.GetLongurlServer;
import com.shorturl.util.ConcurrentHashMapCacheUtils;
import com.shorturl.util.ResponseUtil;

/**
 * Longurl 获取类
 * 
 * @author shaochengming
 * @date 2021/10/15
 */
@Service
public class GetLongurlServerImpl implements GetLongurlServer {
	private static Logger LOGGER = LoggerFactory.getLogger(GetLongurlServerImpl.class);
	/**
	 * 直接从缓存中获取对应长链接
	 */
	@Override
	public ResponseDto getLongurl(String shortUrl) {
		Object longUrl = ConcurrentHashMapCacheUtils.getCache(shortUrl);
		if (null != longUrl) {
			LOGGER.info("获取到长域名 :" + longUrl);
			//如果查询到了该缓存，则刷新缓存
			ConcurrentHashMapCacheUtils.setCache(shortUrl, longUrl);
			return ResponseUtil.getResponseDto(longUrl);
		}
		return ResponseUtil.getResponseDto(ResponseEnum.NO_DATA);
	}

}
