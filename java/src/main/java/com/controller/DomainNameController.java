package com.controller;

import com.check.DomainNameValidator;
import com.service.DomainNameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author jeffrey
 * @Date 2021/10/11
 * @description:短域名服务
 */

@RestController
@RequestMapping("/api/domain_name")
@Api(tags = {"短域名服务"}, description = "短域名长域名转换服务")
public class DomainNameController {

      @Autowired
      private DomainNameValidator domainNameValidator;

      @Autowired
      private DomainNameService domainNameService;

      @GetMapping("/get_short_name")
      @ApiOperation("查询短域名")
      @ApiImplicitParam(name = "longUrl", value = "长域名", dataType = "String", paramType ="query", required = true, defaultValue = "www.baidu.com")
      public String getShortUrl(@RequestParam(required = true) String longUrl) {
        domainNameValidator.validateGetShortUrl(longUrl);
        return domainNameService.getShortUrl(longUrl);
      }

      @GetMapping("/get_long_name")
      @ApiOperation("查询长域名")
      @ApiImplicitParam(name = "shortUrl", value = "短域名", dataType = "String", paramType ="query", required = true, defaultValue = "baidu")
      public String getLongUrl(@RequestParam String shortUrl) {
        domainNameValidator.validateGetLongUrl(shortUrl);
        return domainNameService.getLongUrl(shortUrl);

      }
    }
