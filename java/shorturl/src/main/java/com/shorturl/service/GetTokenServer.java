package com.shorturl.service;

import com.shorturl.entity.ResponseDto;

/**
 * token获取接口
 * 
 * @author shaochengming
 * @date 2021/10/15
 */
public interface GetTokenServer {

	public ResponseDto getToken(String url);

}
