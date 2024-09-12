
package com.oldnoop.shortlink.controller;

import com.oldnoop.shortlink.model.ApiResult;
import com.oldnoop.shortlink.service.ShortLinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("shortlink")
@Api(tags = "短链接API接口")
public class ShortLinkController {

	@Autowired
	private ShortLinkService shortLinkService;

	@PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation("短链接生成接口")
	public ApiResult<List<String>> create(
			@ApiParam(value = "长链接", defaultValue = "", required = true) @RequestBody List<String> longLinkList) {
		List<String> shortLinkList = shortLinkService.create(longLinkList);
		return ApiResult.success(shortLinkList);
	}

	@PostMapping(value = "search")
	@ResponseBody
	@ApiOperation("短连接查询接口")
	public ApiResult<String> search(
			@ApiParam(value = "短链接", defaultValue = "", required = true) @RequestParam String shortLink) {
		String longLink = shortLinkService.search(shortLink);
		return ApiResult.success(longLink);
	}

}
