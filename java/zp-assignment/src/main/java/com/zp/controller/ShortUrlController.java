package com.zp.controller;

import com.zp.response.ResultData;
import com.zp.service.ShortUrlService;
import com.zp.utils.UrlCheckUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短域名Controller
 */
@Api(value = "短域名Controller")
@RestController
public class ShortUrlController {

    @Autowired
    ShortUrlService shortUrlService;

    @ApiOperation(value = "根据长域名获取短域名")
    @GetMapping("/shortUrl/get")
    public ResultData<String> getShortUrl(@RequestParam String url) {
        try {
            if (!UrlCheckUtil.isValidUrl(url)) {
                return ResultData.error("无效的url");
            }
            return ResultData.sccuess(shortUrlService.getShortUrl(url));
        } catch (Exception e) {
            return ResultData.error("获取短域名失败");
        }
    }

    @ApiOperation(value = "根据短域名获取长域名")
    @GetMapping("/longUrl/get")
    public ResultData<String> getLongUrl(@RequestParam String url) {
        try {
            String longUrl = shortUrlService.getLongUrl(url);
            if (StringUtils.isEmpty(longUrl)) {
                return ResultData.error("短域名不存在或已失效");
            }
            return ResultData.sccuess(longUrl);
        } catch (Exception e) {
            return ResultData.error("获取长域名失败");
        }
    }
}
