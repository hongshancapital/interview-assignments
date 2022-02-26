package com.scdt.shorturl.controller;

import cn.hutool.core.lang.Validator;
import com.scdt.shorturl.common.Result;
import com.scdt.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leo
 * @Description: url域名转换控制器
 * @date 2022/1/27 16:47
 */
@Api(tags = "短域名服务")
@Slf4j
@RestController
public class ShortUrlController {

        @Autowired
        private ShortUrlService shortUrlService;

        @GetMapping(value = "longUrlToShortUrl")
        @ApiOperation("长域名转短域名")
        public Result<Object> longUrlToShortUrl(@Parameter(description = "长域名") @RequestParam String url) {

            if(Validator.isUrl(url)){
                String shortUrl = shortUrlService.shorterUrl(url);
                return Result.ok(shortUrl);
            }else{
                return Result.fail("无效的URL");
            }

        }

        @GetMapping(value = "shortUrlToLongUrl")
        @ApiOperation("根据短域名获取对应长域名")
        public Result<Object> shortUrlToLongUrl(@Parameter(description = "短域名") @RequestParam String shortUrl) {
            String url = shortUrlService.getOriginUrl(shortUrl);
            return Result.ok(url);
        }
}
