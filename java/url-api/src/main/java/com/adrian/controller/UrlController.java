package com.adrian.controller;

import com.adrian.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 域名转换接口
 */
@Controller
@Api(tags = "长短域名转换")
public class UrlController {

    @Autowired
    private UrlService urlService;

    /**
     * 长域名转换成短域名
     * @param url 完整的长域名
     * @return 短域名
     */
    @ApiOperation("长域名转短域名")
    @RequestMapping(value = "/getShortUrl", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParam(name = "url", value = "传入的原始域名")
    public String getShortUrl(String url) {
        return urlService.transformUrl(url);
    }

    /**
     * 通过短域名找到对应的长域名
     * @param url 短域名
     * @return 完整的长域名
     */
    @ApiOperation("通过短域名找到对应的长域名")
    @RequestMapping(value = "/findOriginalUrl", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParam(name = "url", value = "传入的短域名")
    public String findOriginalUrl(String url) {
        return urlService.findOriginalUrl(url);
    }
}
