package com.lynnhom.sctdurlshortservice.controller;

import com.lynnhom.sctdurlshortservice.model.dto.BaseResponse;
import com.lynnhom.sctdurlshortservice.model.dto.UrlDto;
import com.lynnhom.sctdurlshortservice.model.dto.UrlResponseDto;
import com.lynnhom.sctdurlshortservice.service.ShortUrlService;
import com.lynnhom.sctdurlshortservice.common.utils.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 短链接服务controller
 * @author: Lynnhom
 * @create: 2021-10-28 09:58
 **/
@Slf4j
@Api(tags = "短链接服务")
@RestController
@RequestMapping("api")
public class ShortUrlController {
    @Autowired
    private ShortUrlService shortUrlService;

    @ApiOperation("创建短链接")
    @PostMapping("/short-url/create")
    public BaseResponse<UrlResponseDto> create(@RequestBody UrlDto url) {
        log.info("path:/short-url/create, url={}", JsonUtil.toJson(url));
        return BaseResponse.success(shortUrlService.create(url));
    }

    @ApiOperation("获取短链接对应的原始链接")
    @GetMapping("/short-url/get")
    public BaseResponse<String> getOriginalUrl(@ApiParam(value = "短链接地址", required = true) @RequestParam("shortUrl") String shortUrl) {
        log.info("path:/short-url/get, shortUrl={}", shortUrl);
        return BaseResponse.success(shortUrlService.getOriginalUrl(shortUrl));
    }
}
