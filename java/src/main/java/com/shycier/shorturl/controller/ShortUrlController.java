package com.shycier.shorturl.controller;

import com.shycier.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 短域名服务
 */
@Api(value = "short url service")
@RestController
@RequestMapping("shortUrl")
public class ShortUrlController {

	@Autowired ShortUrlService shortUrlService;

	/**
	 * 保存长域名 返回短域名
	 * @param originUrl 长域名
	 * @return 短域名
	 */
	@ApiOperation(value = "save origin url and return short url")
	@GetMapping("/save")
	public String save(@ApiParam(value = "origin url", required = true) @RequestParam String originUrl) {
		return shortUrlService.save(originUrl);
	}

	/**
	 * 通过短域名 获取长域名
	 * @param shortUrl 短域名
	 * @return 长域名
	 */
	@ApiOperation(value = "get origin url by short url")
	@GetMapping("/get")
	public String get(@ApiParam(value = "short url", required = true) @RequestParam String shortUrl) {
		return shortUrlService.get(shortUrl);
	}

}
