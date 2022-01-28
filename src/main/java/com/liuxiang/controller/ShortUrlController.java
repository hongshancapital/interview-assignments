package com.liuxiang.controller;

import com.liuxiang.biz.ShortUrlBiz;
import com.liuxiang.model.view.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuxiang6
 * @date 2022-01-06
 **/
@Api(tags = "短链接服务")
@Slf4j
@RestController
@RequestMapping(value = "/", produces = "application/json")
public class ShortUrlController {

    @Autowired
    private ShortUrlBiz shortUrlBiz;

    @PostMapping("long2short")
    @ApiOperation(value="长链接生成短链接")
    public CommonResult<String> long2short(String longUrl) {
        if(StringUtils.isBlank(longUrl)){
            throw new RuntimeException("参数错误");
        }
        return shortUrlBiz.generateShortUrl(longUrl);
    }

    @GetMapping("short2long/{shortUrl}")
    @ApiOperation("根据短链接查询长链接")
    public CommonResult<String> short2long(@PathVariable String shortUrl) {
        if(StringUtils.isBlank(shortUrl)){
            throw new RuntimeException("参数错误");
        }
        return shortUrlBiz.getLongUrl(shortUrl);
    }

}
