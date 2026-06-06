package com.heyenan.shorturldemo.webservice.controller;

import com.heyenan.shorturldemo.common.response.ShortUrlResponse;
import com.heyenan.shorturldemo.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author heyenan
 * @description 长域名接口
 *
 * @date 2020/5/07
 */
@Controller
@Slf4j
@Api(tags = "短域名读取接口")
@RequestMapping("/")
public class LongUrlController {
    @Resource
    private ShortUrlService tinyUrlService;

    @RequestMapping("/getLongUrl/{shortUrl}")
    @ResponseBody
    @ApiOperation(
            value = "获取长链接",
            notes = "获取长链接"
    )
    public String getOriginUrl(@PathVariable("shortUrl") String shortUrl) {
        return "redirect:" + tinyUrlService.getOriginUrl(shortUrl);
    }
}
