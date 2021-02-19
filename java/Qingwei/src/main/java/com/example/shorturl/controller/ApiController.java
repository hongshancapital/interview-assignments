/*
 * Copyright (C) 2021 hongsan, Inc. All Rights Reserved.
 */
package com.example.shorturl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shorturl.service.ShortUrlServiceImpl;

/**
 * shorturl接口服务
 * com.example.shorturl.controller shorturl
 *
 * @create mencius 2021-02-18 19:33
 */
@RestController
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    ShortUrlServiceImpl shortUrlService;

    @Value("${shortUrl.expireTime:60}")
    private Integer expireTime;

    /**
     * 获取code
     *
     * @return
     */
    @RequestMapping("/gen")
    public ResponseEntity<String> index(@RequestParam("fullUrl") String fullUrl,
                                        @RequestParam(value = "expireTime", required = false) Integer expireTime) {
        if (expireTime == null) {
            expireTime = getExpireTime();
        }
        if (!StringUtils.hasLength(fullUrl)) {
            return ResponseEntity.badRequest().body("fullUrl error");
        }
        final String code = shortUrlService.genCode(fullUrl, expireTime);
        return ResponseEntity.ok().body(code);
    }

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }
}
