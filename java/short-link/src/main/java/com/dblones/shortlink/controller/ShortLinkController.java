package com.dblones.shortlink.controller;

import com.dblones.shortlink.service.ShortLinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Api("短域名服务的接口文档")
@RestController
public class ShortLinkController {

    private static final Logger LOG = LoggerFactory.getLogger(ShortLinkController.class);

    @Autowired
    private ShortLinkService shortLinkService;

    @ApiOperation(value = "短域名存储接口", notes = "接受长域名信息，返回短域名信息")
    @RequestMapping(value = "/store", method = RequestMethod.GET)
    public String storeLongLink(String url) {
        if (StringUtils.isEmpty(url)){
            return "长域名信息不能为空";
        }
        LOG.info("长域名url:{}", url);
        return shortLinkService.transformUrl(url);
    }

    @ApiOperation(value = "短域名读取接口", notes = "接受短域名信息，返回长域名信息")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getLongLink(String url) {
        if (StringUtils.isEmpty(url)){
            return "短域名信息不能为空";
        }
        LOG.info("短域名url:{}", url);
        return shortLinkService.getLongUrl(url);
    }
}
