package com.shorturl.service.impl;

import org.springframework.stereotype.Service;

import com.shorturl.entity.ResponseDto;
import com.shorturl.enums.ResponseEnum;
import com.shorturl.service.GetTokenServer;
import com.shorturl.util.ResponseUtil;
import com.shorturl.util.SignUtil;

/**
 * token 获取类
 * @author shaochengming
 * @date 2021/10/15
 */
@Service
public class GetTokenServerImpl implements GetTokenServer {

	@Override
	public ResponseDto getToken(String user) {
		if(user!=null){
			return ResponseUtil.getResponseDto(SignUtil.sign(user));
		}
		return ResponseUtil.getResponseDto(ResponseEnum.FAIL);
	}

}
