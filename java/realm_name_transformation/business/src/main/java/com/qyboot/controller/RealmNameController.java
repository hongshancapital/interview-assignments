package com.qyboot.controller;

import com.qyboot.utils.Base62UrlShorter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zk
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "域名转换")
@Slf4j
public class RealmNameController {


    private Base62UrlShorter base62UrlShorter = new Base62UrlShorter();

    @GetMapping("/api/short/url")
    @ApiOperation("获取短域名")
    public String shortUrl(@RequestParam String url) {
        return base62UrlShorter.shorten(url);
    }

    @GetMapping("/api/realm/name")
    @ApiOperation("获取长域名")
    public String realmNameUrl(@RequestParam String url) {
        return base62UrlShorter.lookup(url);
    }
}
