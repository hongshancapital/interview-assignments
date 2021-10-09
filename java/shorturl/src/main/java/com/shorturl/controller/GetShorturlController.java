package com.shorturl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shorturl.entity.ResponseDto;
import com.shorturl.service.GetShorturlServer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 获取短域名
 * 
 * @author:shaochengming
 * @date:2021/10/15
 */
@Api
@Controller
@RequestMapping("/shorturl")
public class GetShorturlController {
	@Autowired
	private GetShorturlServer getShorturlServer;

	@ApiOperation(value = "get short domain name")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseDto getShorturl(@ApiParam(value = "longurl", required = true) @RequestParam("url") String url) {
		return getShorturlServer.getShorturl(url);
	}
}
