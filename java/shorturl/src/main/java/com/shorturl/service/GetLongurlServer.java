package com.shorturl.service;

import com.shorturl.entity.ResponseDto;

/**
 * 长域名 获取接口
 * 
 * @author shaochengming
 * @date 2021/10/15
 */
public interface GetLongurlServer {
	public ResponseDto getLongurl(String shortUrl);
}
