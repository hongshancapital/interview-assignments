package com.scdt.exam.controller;

import com.scdt.exam.model.ShortUrl;
import com.scdt.exam.service.ShortUrlService;
import com.scdt.exam.support.ResponseResult;
import com.scdt.exam.util.TinyUrlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/url")
@Api(tags = "短域名接口")
public class UrlController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ShortUrlService shortUrlService;


    @ApiOperation(value = "获取短域名的接口")
    @GetMapping("/short")
    public ResponseResult<String> getShortUrl(@ApiParam(value = "长域名", required = true)
                                              @RequestParam String url) {
        String shortUrl = stringRedisTemplate.opsForValue().get(url);
        if (shortUrl != null) {
            return ResponseResult.successWithData(shortUrl);
        }
        String[] shortUrls = TinyUrlUtil.shortUrl(url, 8);
        for (String u : shortUrls) {
            int index = Math.abs(u.hashCode()) % 10 + 1;
            ShortUrl shortUrlInDb = shortUrlService.selectByCode("t_short_url_" + index, u);
            if (shortUrlInDb != null) {
                if (shortUrlInDb.getFullUrl().equals(url)) {
                    return ResponseResult.successWithData(shortUrlInDb.getCode());
                } else {
                    continue;
                }
            } else {
                stringRedisTemplate.opsForValue().set(url, u, 1, TimeUnit.HOURS);
                ShortUrl sUrl = new ShortUrl();
                sUrl.setFullUrl(url);
                sUrl.setCode(u);
                sUrl.setCreateTime(new Date());
                sUrl.setTableName("t_short_url_" + index);
                System.out.println(sUrl);
                shortUrlService.insertWithTableName(sUrl);
                return ResponseResult.successWithData(u);
            }
        }
        return ResponseResult.failed("获取短域名失败");
    }

    @ApiOperation(value = "根据短域名获取长域名的接口")
    @GetMapping("/full")
    public ResponseResult<String> getFullUrl(@ApiParam(value = "短域名", required = true) @RequestParam String code) {
        int index = Math.abs(code.hashCode()) % 10 + 1;
        ShortUrl shortUrl = shortUrlService.selectByCode("t_short_url_" + index, code);
        if (shortUrl != null) {
            return ResponseResult.successWithData(shortUrl.getFullUrl());
        }
        return ResponseResult.failed("未找到对应的长域名");
    }

}
