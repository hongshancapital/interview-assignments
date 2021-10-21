package com.shenshen.zhw.urlconverter.controller;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.shenshen.zhw.urlconverter.service.ConvertService;
import com.shenshen.zhw.urlconverter.utils.RestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Description: 短域名服务接口类
 * @Author: zhangwei
 * @Create Date: 2021-11-08
 */
@Api(value = "短域名生成接口")
@RestController("/")
public class ApiController {

    @Autowired
    private ConvertService convertService;

    @GetMapping("/")
    public String index() {

        return "服务启动成功";
    }

    /**
     * 获取长域名对应的短域名标识
     * @param shortUrl 短域名标识
     * @return 长域名，如果找不到返回null
     */
    @ApiOperation(value = "获取长域名对应的短域名", notes = "")
    @GetMapping("/get")
    public String get(@ApiParam(value = "短域名标识" , required=true ) @RequestParam String shortUrl) {
        if (shortUrl==null || shortUrl.isEmpty()) {
            return RestUtils.error(1001,"参数 shortUrl不能为空");
        }

        return RestUtils.success("",convertService.get(shortUrl));
    }

    /**
     * 把长域名转化为短域名标识
     * @param longUrl 长域名
     * @return 短域名标识
     */
    @ApiOperation(value = "把长域名转化为短域名标识", notes = "")
    @PostMapping("/save")
    public String save(@ApiParam(value = "长域名" , required=true ) @RequestParam String longUrl) {
        if (longUrl==null || longUrl.isEmpty()) {
            return RestUtils.error(1001,"参数 longUrl不能为空");
        }
        return RestUtils.success("",convertService.save(longUrl));
    }

}
