package com.redwood.shorturl.controller;


import com.redwood.shorturl.domain.Result;
import com.redwood.shorturl.service.inter.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jack-ZG
 * @Date 2022-01-02
 */
@RestController
@Api(value = "长域名、短域名相互转换接口", protocols = "application/json", produces = "application/json")
public class ShortUrlController {


    @Autowired
    private ShortUrlService shortUrlService;

    /**
     * 短域名生成
     *
     * @param mainUrl
     * @return
     */
    @ApiOperation("短域名生成")
    @GetMapping("shortUrl/generate")
    public @ResponseBody
    Result generate(@RequestParam(value = "mainUrl", required = false) String mainUrl) {
        if (StringUtils.isBlank(mainUrl)) {
            return Result.error(403, "MainURL can not be empty.");
        } else if (!(mainUrl.startsWith("http://")||mainUrl.startsWith("https://"))) {
            return Result.error(402, "MainURL format error.");
        }
        return Result.ok(shortUrlService.generate(mainUrl));
    }

    /**
     * 依据短域名获取长域名
     *
     * @param shortUrl
     * @return
     */
    @ApiOperation("依据短域名获取长域名")
    @GetMapping("exchange/mainUrl")
    public @ResponseBody
    Result exchange(@RequestParam(value = "shortUrl", required = false) String shortUrl) {
        return Result.ok(shortUrlService.exchange(shortUrl));
    }
}