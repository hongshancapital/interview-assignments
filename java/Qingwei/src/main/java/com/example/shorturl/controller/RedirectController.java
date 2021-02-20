/*
 * Copyright (C) 2021 hongsan, Inc. All Rights Reserved.
 */
package com.example.shorturl.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shorturl.service.ShortUrlServiceImpl;

/**
 * 重定向服务
 * com.example.shorturl.controller shorturl
 *
 * @create mencius 2021-02-18 19:33
 */
@RestController
public class RedirectController {
    private static final Logger logger = LoggerFactory.getLogger(RedirectController.class);

    @Autowired
    ShortUrlServiceImpl shortUrlService;

    /**
     * 重定向到fullUrl,即查询服务。
     *
     * @param code
     * @return
     */
    @RequestMapping("/{code}")
    public ResponseEntity<String> forward(@PathVariable(value = "code") String code) {
        logger.info(code);
        if (!StringUtils.hasLength(code)) {
            return ResponseEntity.notFound().build();
        }
        String url = shortUrlService.queryLongUrl(code);
        try {
            if (!StringUtils.hasLength(url)) {
                return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(new URI("/")).build();
            }
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(new URI(url)).build();
        } catch (URISyntaxException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
