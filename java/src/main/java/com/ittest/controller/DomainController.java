package com.ittest.controller;

import com.ittest.service.DomainService;
import com.ittest.utils.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: taojiangbing
 * @Date: 2022/4/20 17:22
 * @Description: 域名存储与转换
 */
@RestController
@RequestMapping("/api/v1/domain")
public class DomainController {

    @Resource
    private DomainService domainService;

    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     *
     * @param longDomain 长域名
     * @return 返回短域名
     */
    @ApiOperation("短域名存储接口")
    @RequestMapping(value = "/storage", method = RequestMethod.GET)
    public Result storage(@RequestParam @ApiParam(value = "长域名") String longDomain) {
        String shortDomain = domainService.storage(longDomain);
        return Result.success(shortDomain);
    }


    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息
     *
     * @param shortDomain 短域名
     * @return 返回长域名
     */
    @ApiOperation("短域名读取接口")
    @RequestMapping(value = "/transfor", method = RequestMethod.GET)
    public Result transfor(@RequestParam @ApiParam(value = "短域名") String shortDomain) {
        String longDomain = domainService.transfor(shortDomain);
        return Result.success(longDomain);
    }
}
