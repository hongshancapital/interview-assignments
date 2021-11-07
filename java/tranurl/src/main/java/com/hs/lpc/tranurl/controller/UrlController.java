package com.hs.lpc.tranurl.controller;

import com.hs.lpc.tranurl.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/url")
@Api(value = "长域名、短域名相互转换接口", protocols = "application/json", produces = "application/json")
public class UrlController {

    public static final String DEFAULT_TINY_DOMAIN = "http://c.t.cn/";

    @Autowired
    private UrlService urlService;

    /**
     * 根据长链接获取短链接
     * @param longUrl 长链接
     * @return 短链接
     */
    @GetMapping("/short")
    @ApiOperation("长域名转短")
    public String getShortUrl(@RequestParam("longUrl") String longUrl) {
        String shortUrl = urlService.getShortUrl(longUrl);
        return DEFAULT_TINY_DOMAIN + shortUrl;
    }
    /**
     * 根据短链接获取长链接
     * @param shortUrl 短链接
     * @return 长链接
     */
    @GetMapping("/long")
    @ApiOperation("短域名转长")
    public String getLongUrl(@RequestParam("shortUrl") String shortUrl) {
        if (shortUrl.startsWith(DEFAULT_TINY_DOMAIN)) {
            shortUrl = shortUrl.replace(DEFAULT_TINY_DOMAIN, "");
        }
        String longUrl = urlService.getLongUrl(shortUrl);
        return longUrl;
    }
}
