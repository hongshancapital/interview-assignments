package com.sxg.shortUrl.controller;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import com.sxg.shortUrl.model.UrlModel;
import com.sxg.shortUrl.service.UrlService;
import com.sxg.shortUrl.utils.Result;
import com.sxg.shortUrl.utils.RedisUtil;
import com.sxg.shortUrl.utils.UrlUtil;

/**
 * 
 * @author sxg
 *
 */
@RestController
@Api(tags = "短链接接口")
public class UrlController {

	private final UrlService service;
	private final RedisUtil redisUtil;

	public UrlController(UrlService service, RedisUtil redisUtil) {
		this.service = service;
		this.redisUtil = redisUtil;
	}

	@PostMapping
	@ApiOperation(value = "生成短链接信息")
	@ApiImplicitParam(name = "url", value = "长链接", required = true, dataType = "string")
	public Result<UrlModel> createSurl(@RequestParam(name = "url") String url) {

		if (!UrlUtil.isValidUrl(url)) {
			throw new RuntimeException("url不合法");
		}

		String s = redisUtil.get(RedisUtil.urlLong + url);
		if (!StrUtil.isNullOrUndefined(s)) {
			return Result.success(new UrlModel(s, url));
		}
		UrlModel sUrl = this.service.createShortUrl(url);

		return Result.success(sUrl);
	}

	@GetMapping("{shortUrl}")
	@ApiOperation(value = "获取长链接信息")
	@ApiImplicitParam(name = "shortUrl", value = "短链接URI", required = true, dataType = "string", paramType = "path")
	public Result<UrlModel> getUrl(@PathVariable String shortUrl) {
		if (StrUtil.length(shortUrl) != 8) {
			throw new RuntimeException("短链接不合法");
		}

		String s = redisUtil.get(RedisUtil.urlShort + shortUrl);
		if (!StrUtil.isNullOrUndefined(shortUrl)) {
			return Result.success(new UrlModel(shortUrl, s));
		}

		UrlModel urlDO = this.service.getUrl(s);
		return Result.success(urlDO);
	}
}
