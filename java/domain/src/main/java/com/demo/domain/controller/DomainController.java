package com.demo.domain.controller;

import com.demo.domain.util.Constant;
import com.demo.domain.util.ShortUrlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author fanzj
 * @Date 2022/4/2 11:25
 * @Version 3.0
 * @Description 实现短域名服务-api
 */
@RestController
@Api(value = "DomainController", tags = "域名-API")
public class DomainController {

    /**
     * Title:
     * Description: 接受短域名信息，返回长域名信息
     *
     * @return
     * @date 2022/4/2-13:20
     */
    @ApiOperation(value = "通过短域名地址获取域名长地址", notes = "通过短域名地址获取域名长地址", httpMethod = "GET")
    @GetMapping("/getLongDomainUrl")
    public String getLongUrl(@ApiParam(name = "shortDomain", value = "短域名地址", required = true) @RequestParam(
            "shortDomain") String shortDomain) {
        // URL校验
        if (!ShortUrlUtil.verifyUrl(shortDomain)) {
            return Constant.URL_PARSE_ERROR;
        }
        // 包含SHORT_URL_PREFIX校验
        int index = shortDomain.indexOf(Constant.SHORT_URL_PREFIX);
        if (index == -1) {
            return Constant.URL_PARSE_ERROR;
        }
        shortDomain = shortDomain.substring(index + Constant.SHORT_URL_PREFIX.length());
        return ShortUrlUtil.getLongUrl(shortDomain);
    }

    /**
     * Title:
     * Description: 接受长域名信息，返回短域名信息
     *
     * @return
     * @date 2022/4/2-13:20
     */
    @ApiOperation(value = "通过长域名地址获取域名短地址", notes = "通过长域名地址获取域名短地址", httpMethod = "GET")
    @GetMapping("/getShortDomainUrl")
    public String getShortUrl(@ApiParam(name = "longDomain", value = "长域名地址", required = true) @RequestParam(
            "longDomain") String longDomain) {
        // URL校验
        if (ShortUrlUtil.verifyUrl(longDomain)) {
            return Constant.SHORT_URL_PREFIX + ShortUrlUtil.getShortUrl(longDomain, null);
        } else {
            return Constant.URL_PARSE_ERROR;
        }
    }

}
