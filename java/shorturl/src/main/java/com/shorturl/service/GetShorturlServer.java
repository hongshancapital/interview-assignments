package com.shorturl.service;

import com.shorturl.entity.ResponseDto;

/**
 * 短域名获取接口
 * 
 * @author shaochengming
 * @date 2021/10/15
 */
public interface GetShorturlServer {
	public ResponseDto getShorturl(String longUrl);
}
