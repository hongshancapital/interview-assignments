package com.peogoo.controller;

import com.peogoo.common.util.ShortUrlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @auth renxiaolong
 * @Date 2021-4-14 14:02:57
 * @Description
 */
@RestController
@RequestMapping(value = "/longToShort")
@Api( value = "USERINFO", description = "用户信息测试controller")

public class LongToShortController {
    private Logger logger = LoggerFactory.getLogger(LongToShortController.class);

    private static final String SHORT_URL = "shortUrl:";

    public Map urlMap =new HashMap();


    /**
     * 长地址转换成短地址
     *
     * @param longUrl 入参长地址
     * @return
     */
    @RequestMapping(value = "/longToShort", method = RequestMethod.GET)
    @ApiOperation(value = "长地址转化成短地址，并保存起来")
    public String longToShort(@RequestParam String longUrl) {
        //将长地址转化成短地址，放入到内存中，可以用redis 代替
        String shortUrl = ShortUrlUtils.shortUrl(longUrl);
        urlMap.put(SHORT_URL+shortUrl,longUrl);
        return "http://localhost:8080/url/" + shortUrl;
    }

}