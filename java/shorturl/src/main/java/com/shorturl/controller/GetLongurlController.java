package com.shorturl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shorturl.entity.ResponseDto;
import com.shorturl.service.GetLongurlServer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 获取长域名
 * 
 * @author:shaochengming
 * @date:2021/10/15
 */
@Api
@Controller
@RequestMapping("/longurl")
public class GetLongurlController {
	@Autowired
	private GetLongurlServer getLongurlServer;

	@ApiOperation(value = "get long domain name")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseDto getLongurl(@ApiParam(value = "shorturl", required = true) @RequestParam("url") String url) {
		return getLongurlServer.getLongurl(url);
	}
}
