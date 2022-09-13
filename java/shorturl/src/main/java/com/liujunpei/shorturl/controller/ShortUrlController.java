package com.liujunpei.shorturl.controller;

import com.liujunpei.shorturl.model.RequestEntity;
import com.liujunpei.shorturl.model.ResponseEntity;
import com.liujunpei.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wiremock.org.apache.commons.lang3.StringUtils;

/**
 * @author 刘俊佩
 * @date 2022/1/26 下午4:53
 */
@Api(tags = "短域名服务")
@Slf4j
@RestController
@RequestMapping("/shorturl")
public class ShortUrlController {
    @Autowired
    private ShortUrlService shortUrlService;

    @ApiOperation("长域名转短域名")
    @PostMapping("/longUrlToShortUrl")
    public ResponseEntity longUrlToShortUrl(@RequestBody RequestEntity request) {
        ResponseEntity result = new ResponseEntity();
        if (request != null && StringUtils.isNotEmpty(request.getLongUrl())) {
            String shortUrl = shortUrlService.longUrlToShortUrl(request.getLongUrl());
            result.setCode(200);
            result.setResult(shortUrl);
        } else {
            result.setCode(500);
            result.setResult("");
            result.setMessage("长域名不可为空");
        }
        return result;
    }

    @ApiOperation("根据短域名获取对应长域名")
    @PostMapping("/shortUrlToLongUrl")
    public ResponseEntity shortUrlToLongUrl(@RequestBody RequestEntity request) {
        ResponseEntity result = new ResponseEntity();
        if (request != null && StringUtils.isNotEmpty(request.getShortUrl())) {
            String shortUrl = shortUrlService.shortUrlToLongUrl(request.getShortUrl());
            result.setCode(200);
            result.setResult(shortUrl);
        } else {
            result.setCode(500);
            result.setResult("");
            result.setMessage("短域名不可为空");
        }
        return result;
    }

}
