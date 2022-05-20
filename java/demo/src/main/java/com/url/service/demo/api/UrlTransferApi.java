package com.url.service.demo.api;

import com.url.service.demo.service.UrlTransferService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "短域名服务接口")
@RestController
public class UrlTransferApi {

	@Autowired
	private UrlTransferService transferService;

	@ApiOperation(value = "短域名存储接口", notes = "接受长域名信息，返回短域名信息")
	@ApiImplicitParam(name = "fullUrl", value = "传入长域名", required = true, dataType = "String")
	@GetMapping("/saveUrl")
	public Object saveUrl(@RequestParam("fullUrl") String fullUrl) {
		return transferService.saveUrl(fullUrl);
	}

	@ApiOperation(value = "短域名读取接口", notes = "接受短域名信息，返回长域名信息")
	@ApiImplicitParam(name = "shortUrl", value = "传入短域名", required = true, dataType = "String")
	@GetMapping("/getFullUrl")
	public Object getFullUrl(@RequestParam("shortUrl") String shortUrl) {
		return transferService.getFullUrl(shortUrl);
	}
}
