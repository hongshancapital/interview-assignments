package com.example.demo.controller;

import com.example.demo.model.common.CommonResult;
import com.example.demo.service.impl.ShortUrlServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangxiaosong
 * @since 2022/1/10
 */
@Api(value = "短链接", tags = "短链接服务")
@RestController
@RequestMapping("/api/short/url")
public class ShortUrlController {
    private static final Logger logger = LoggerFactory.getLogger(ShortUrlController.class);

    @Autowired
    private ShortUrlServiceImpl shortUrlService;

    @ApiOperation(value = "短域名存储接口", notes = "接受长域名信息，返回短域名信息")
    @PostMapping("/longUrlConvertShortUrl")
    public CommonResult<String> longUrlConvertShortUrl(@RequestParam("longUrl") String longUrl) {
        if (StringUtils.isBlank(longUrl)) {
            return CommonResult.fail(400,  longUrl,"url不能为空");
        }
        String shortUrl = shortUrlService.longUrlConvertShortUrl(longUrl);
        return CommonResult.success(shortUrl);
    }

    @ApiOperation(value = "短域名读取接口", notes = "接受短域名信息，返回长域名信息")
    @GetMapping("/shortUrlConvertLongUrl")
    public CommonResult<String> shortUrlConvertLongUrl(@PathVariable String shortUrl) {
        String longUrl = shortUrlService.shortUrlConvertLongUrl(shortUrl);
        if (StringUtils.isBlank(longUrl)) {
            return CommonResult.fail(500, "不存在该链接,请核查!", longUrl);
        }
        return CommonResult.success(longUrl);
    }


}