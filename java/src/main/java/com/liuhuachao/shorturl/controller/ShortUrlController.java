package com.liuhuachao.shorturl.controller;

import com.liuhuachao.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短域名服务 控制器
 * @author liuhuachao
 * @date 2021/12/20
 */
@RestController
@RequestMapping("/short-url")
@Api(value = "短域名服务",tags = "短域名服务",protocols = "http")
public class ShortUrlController {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(ShortUrlController.class);

	/**
	 * 短域名存储接口：接受长域名信息，返回短域名信息
	 * @param originUrl
	 * @return
	 */
	@PostMapping("/store")
	@ApiOperation(value = "短域名存储接口",notes = "接受长域名信息，返回短域名信息")
	@ApiImplicitParam(name = "originUrl", value = "原始域名", required = true, dataType = "String", paramType = "query")
	public ResponseEntity getShortUrl(@RequestParam String originUrl) throws Exception {
		String shortUrl = ShortUrlService.getShortUrl(originUrl);
		return ResponseEntity.ok(shortUrl);
	}

	/**
	 * 短域名读取接口：接受短域名信息，返回长域名信息
	 * @param shortUrl
	 * @return
	 */
	@GetMapping("/get")
	@ApiOperation(value = "短域名读取接口",notes = "接受短域名信息，返回长域名信息")
	@ApiImplicitParam(name = "shortUrl", value = "短域名", required = true, dataType = "String", paramType = "query")
	public ResponseEntity getOriginUrl(@RequestParam String shortUrl) throws Exception {
		String originUrl = ShortUrlService.getOriginUrl(shortUrl);
		if(StringUtils.isBlank(originUrl)){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("根据短域名找不到对应的原始域名！");
		}
		return ResponseEntity.ok(originUrl);
	}

}
