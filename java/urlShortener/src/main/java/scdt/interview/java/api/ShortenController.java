package scdt.interview.java.api;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import scdt.interview.java.common.constant.ShortenConstant;
import scdt.interview.java.common.entity.ShortenRequest;
import scdt.interview.java.config.SystemConfig;
import scdt.interview.java.service.ShortenService;

/**
 * 短域名服务接口
 * @author Laerfu Zhang
 */

@Api(tags = "短域名接口 V1")
@RestController
@RequestMapping("/api/shorten/v1")
public class ShortenController {

	@Resource
	private ShortenService shortenService;
	
	/**
	 * 	短域名生成
	 * @param shortenRequest 
	 * @return shortenKey
	 */
	@ApiOperation(value = "短域名存储接口", notes = "短域名存储接口")
	@PutMapping
	public String generateShort(@RequestBody ShortenRequest shortenRequest) {
		String shortUrl = shortenService.generate(shortenRequest.getLongUrl());
		return ShortenConstant.SHORT_DOMAIN_HOST + shortUrl;
	}

	/**
	 * 	短域名读取接口：接受短域名信息，返回长域名信息。
	 * @param shortUrl
	 * @return
	 */
	@ApiOperation(value = "短域名读取接口", notes = "短域名读取接口")
	@GetMapping("/{shortUrl}")
	public String getLong(@PathVariable("shortUrl") String shortUrl) {
		String longUrl = shortenService.getLongURL(shortUrl);
		if(longUrl != null && longUrl.length() > 0) {
			return longUrl;
		}else {
			return "短域名不存在！";
		}

	}
}
