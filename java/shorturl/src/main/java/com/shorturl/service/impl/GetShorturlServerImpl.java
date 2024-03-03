package com.shorturl.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shorturl.entity.ResponseDto;
import com.shorturl.service.GetShorturlServer;
import com.shorturl.util.ConcurrentHashMapCacheUtils;
import com.shorturl.util.ResponseUtil;
import com.shorturl.util.UrlUtil;

/**
 * shorturl 获取类
 * @author shaochengming
 * @date 2021/10/15
 */
@Service
public class GetShorturlServerImpl implements GetShorturlServer{
	
	private static Logger LOGGER = LoggerFactory.getLogger(GetShorturlServerImpl.class);
	
	@Value("${machineId:1}")
	private long machineId;
	@Value("${dataCenterId:1}")
	private long dataCenterId;
	/**
	 * shorturl 获取方法，每次获取新的短url,如果内存中存在该短url则重新获取，否则更新内存
	 */
	@Override
	public ResponseDto getShorturl(String longUrl) {
		//获取shorturl
		String shorturl = this.geturl64(longUrl);
		LOGGER.info("获取到短域名 :" + shorturl);
		//去内存比对，如果内存存在该url则重新获取
		if(null != ConcurrentHashMapCacheUtils.getCache(shorturl)) {
			return getShorturl(longUrl);
		}else {
			ConcurrentHashMapCacheUtils.setCache(shorturl, longUrl);
			return ResponseUtil.getResponseDto(shorturl);
		}
		
	}
	
	/**
	 * shorturl 获取方法
	 */
	public String geturl64(String longUrl) {
		//通过雪花算法获取自增数列，转为64进制
		String url64 = UrlUtil.toNumberSystem62(UrlUtil.getLongnumber(machineId, dataCenterId),64);
		//截取后8位
		if(url64.length()>8) {
			url64 = url64.substring(url64.length() - 8);
		}
		return url64;
	}

}
