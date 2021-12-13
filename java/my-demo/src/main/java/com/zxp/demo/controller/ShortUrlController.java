package com.zxp.demo.controller;

import com.zxp.demo.service.ShortUrlChangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * @description: 短链接生成Controller·
 * @author: zxp
 * @date: Created in 2021/12/13 13:59
 */

@RestController
@Api(tags="关于长短域名互相转换接口")
@Slf4j
public class ShortUrlController {

    @Resource
    ShortUrlChangeService shortUrlChangeService;

    @ApiOperation(value="长域名转短域名", notes="长域名转短域名")
    @RequestMapping("/getShortUrl")
    public String getShortUrl(String longUrl) {
         return  shortUrlChangeService.conversionShortUrl(longUrl);
    }


    @ApiOperation(value="短域名转长域名", notes="长域名转短域名")
    @RequestMapping("/getLongUrl")
    public String getLongUrl(String longUrl) {
        return  shortUrlChangeService.conversionLongUrl(longUrl);
    }
}
