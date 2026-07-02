package com.breo.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breo.common.service.ShortUrlService;
import com.breo.common.service.request.GenShortUrlRequest;
import com.breo.common.service.request.GetLongUrlRequest;
import com.breo.common.service.response.GenShortUrlResponse;
import com.breo.common.service.response.GetLongUrlResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/common/shortUrl")
@Api("短链接域名服务")	
public class ShortUrlController {

    
	@Autowired
	public ShortUrlService shortUrlService;
	
	
	/**
	 * 生成短域名
	 * 
	 * @param longUrl
	 * @return
	 */
    @GetMapping("/genShortUrl")
    @ApiOperation(value = "生成短域名接口")
	public GenShortUrlResponse genShortUrl(GenShortUrlRequest request) {
		return shortUrlService.genShortUrl(request);
	}
	
	/**
	 * 获取长域名
	 * 
	 * @param longUrl
	 * @return
	 */
    @GetMapping("/getLongUrl")
    @ApiOperation(value = "获取长域名接口")
	public GetLongUrlResponse getLongUrl(GetLongUrlRequest request) {
    	return shortUrlService.getLongUrl(request);
	}
	
    
    
}
