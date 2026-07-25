package com.hs.wl.controller;

import com.hs.wl.manager.UrlTransferManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.websocket.server.PathParam;

@Api(tags = "url 长短链接转换服务")
@RestController
@RequestMapping("/url")
public class UrlController {

	@Autowired
	private UrlTransferManager urlTransferManager;

	@GetMapping(value = "/getShortUrl/{url}")
	@ApiOperation(value = "获取短链接", notes = "传入长链接，返回8位短链接")
	public String parseLongUrlToShortUrl(@PathVariable("url") String url) {
		return urlTransferManager.parseLongUrlToShortUrl(url);
	}

	@GetMapping(value = "/getLongUrl/{url}")
	@ApiOperation(value = "获取长链接", notes = "传入短链接，返回长链接")
	public String getLongUrlByShortUrl(@PathVariable("url") String url) {
		return urlTransferManager.getLongUrlByShortUrl(url);
	}

}
