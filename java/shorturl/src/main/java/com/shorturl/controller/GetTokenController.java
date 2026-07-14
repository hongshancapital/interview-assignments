package com.shorturl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shorturl.entity.ResponseDto;
import com.shorturl.service.GetTokenServer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author:shaochengming
 * @date:2021/10/15
 */
@Api
@Controller
@RequestMapping("/get_token")
public class GetTokenController {
	@Autowired
	private GetTokenServer getTokenServer;

	@ApiOperation(value = "get token")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseDto getShorturl(@ApiParam(value = "user name", required = true) @RequestParam("url") String url) {
		return getTokenServer.getToken(url);
	}
}
