package com.example.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.service.DomainNameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api("域名调整相关")
@RestController("domainName")
public class DomainNameController {
    @Autowired
    private DomainNameService domainNameService;
    @ApiOperation(value = "输入长域名获取短域名")
    @GetMapping("getShort")
    public String getShortAccordingToLong(String domain) throws Exception {
        if (domain == null) {
            throw new Exception("请输入有效域名");
        }
        return domainNameService.longToShort(domain);
    }
    @ApiOperation(value = "输入短域名获取长域名")
    @GetMapping("getLong")
    public String shortDomainNameToGetLongDomainName(String domain) {
        return domainNameService.getLongByShort(domain);
    }

}
