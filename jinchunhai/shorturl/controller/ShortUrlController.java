package com.jinchunhai.shorturl.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jinchunhai.shorturl.common.LongUrlRequest;
import com.jinchunhai.shorturl.common.Response;
import com.jinchunhai.shorturl.service.ShortUrlService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description 短域名服务
 * @version 1.0
 * @author JinChunhai
 * @time 2021年03月19日
 */

@Api(tags = "短域名服务接口")
@RestController
@RequestMapping("/api/short/url")
public class ShortUrlController {
	
	@Autowired
	private ShortUrlService shortUrlService;
	
	@ApiOperation("短域名存储接口")
	@ResponseBody
	@RequestMapping("/create")
	public Object createShortUrl(@RequestBody LongUrlRequest longReq) {
		Response response = new Response();
		try {
			String createShortUrl = shortUrlService.createShortUrl(longReq.getLongUrl());
			response.setStatus(Response.SUCCESS);
			response.setBody(createShortUrl);
		} catch (Exception e) {
			response.setStatus(Response.ERROR);
			response.setMessage("生成失败:" + e.getMessage());
		}
		return response;
	}
	
	@ApiOperation("接收短域名返回长域名接口")
	@RequestMapping("/s/{id}")
	public void shortUrlCustom(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect(shortUrlService.getLongUrl(id));
	}
	
}
