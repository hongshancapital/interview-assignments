package com.gc.shorturl.controller;

import com.gc.shorturl.common.BaseResponse;
import com.gc.shorturl.service.IShortUrlService;
import com.gc.shorturl.service.impl.ShortUrlServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Author: GC
 * @Date: 2021/3/4
 */
@RestController
@RequestMapping("/url/")
@Api(description = "短网址服务")
public class ShortUrlController {

    @Autowired
    IShortUrlService shortUrlService;

    @GetMapping("convert2ShortUrl")
    @ApiOperation("获取短网址")
    public BaseResponse convert2ShortUrl(@ApiParam("原地址") @RequestParam String url) {
        try {
            String shortUrl = shortUrlService.convert2ShortUrl(url);
            return BaseResponse.success(shortUrl);
        } catch (Exception e) {
            return BaseResponse.failed(e.getMessage());
        }
    }

    @ApiOperation("根据短网址查询原网址")
    @GetMapping("convert2OriginalUrl")
    public BaseResponse convert2OriginalUrl(@ApiParam("短网址") @RequestParam String shortUrl) {
        try {
            String url = shortUrlService.convert2OriginalUrl(shortUrl);
            return BaseResponse.success(url);
        } catch (Exception e) {
            return BaseResponse.failed(e.getMessage());
        }
    }
}
