package com.sequoia.shorturl.web.controller;

import cn.hutool.core.util.StrUtil;
import com.sequoia.shorturl.common.ApiResult;
import com.sequoia.shorturl.web.service.IUrlConvertorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: xxx
 * @Description: TODO
 * @Date: 2022/1/3 22:57
 * @Version: 1.0.0
 */
@RestController
@Api(value = "短链服务")
@RequestMapping("/api/v1")
public class UrlConvertorController {
    private static final Logger logger = LoggerFactory.getLogger(UrlConvertorController.class);

    private final IUrlConvertorService urlConvertorService;

    public UrlConvertorController(IUrlConvertorService urlConvertorService) {
        this.urlConvertorService = urlConvertorService;
    }

    @PostMapping("/to-short-url")
    @ApiOperation("接收长域名,转换为短域名")
    public ApiResult<String> longUrlToShortUrl(@RequestParam("longUrl") String longUrl) {
        if (StrUtil.isBlank(longUrl)) {
            return ApiResult.create(400, "url不能为空", longUrl);
        }
        String shortUrl = urlConvertorService.longUrlToShortUrl(longUrl);
        return ApiResult.ok(shortUrl);
    }

    @GetMapping("/{shortUrl}")
    @ApiOperation("根据短域名,返回长域名")
    public ApiResult<String> getLongUrlByShortUrl(@PathVariable String shortUrl) {
        String longUrl = urlConvertorService.getLongUrlByShortUrl(shortUrl);
        if (StrUtil.isBlank(longUrl)) {
            return ApiResult.create(500, "不存在该链接,请核查!", longUrl);
        }
        return ApiResult.ok(longUrl);
    }


}
