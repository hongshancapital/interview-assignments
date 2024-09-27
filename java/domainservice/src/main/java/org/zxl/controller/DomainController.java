package org.zxl.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zxl.service.DomainService;

@Slf4j
@Api(tags = "短域名服务")
@RestController
@RequestMapping("/domain")
public class DomainController {
    @Autowired
    private DomainService domainService;

    @ApiOperation(value = "长域名映射为短域名", notes = "必须为合法域名 http | https 开头")
    @RequestMapping(value = "/short", method = RequestMethod.GET)
    public String convertToShort(@RequestParam String url) {
        return domainService.convertToShort(url);
    }

    @ApiOperation(value = "短域名映射为长域名", notes = "必须为合法域名 http | https 开头")
    @RequestMapping(value = "/recover", method = RequestMethod.GET)
    public String convertToOriginUrl(@RequestParam String url) {
        return domainService.convertToLong(url);
    }
}
