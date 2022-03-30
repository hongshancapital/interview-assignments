package com.rad.shortdomainname.controller;

import com.rad.shortdomainname.common.Result;
import com.rad.shortdomainname.common.ResultFactory;
import com.rad.shortdomainname.enums.ResultCodeEnum;
import com.rad.shortdomainname.service.ShortDomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xukui
 * @program: ShortDomainName
 * @description: Swagger的配置类
 * @date 2022-03-19 13:02:11
 */

@Slf4j
@Api(tags="短域名服务")
@RestController
@RequestMapping("/shortDomainName")
public class ShortDomainNameController {

    @Resource
    private ShortDomainService shortDomainService;

    @ApiOperation("将短域名转化为长域名")
    @GetMapping("/getLongDomain")
    public Result getLongDomain(@ApiParam("短连接地址") String shortLink){
        try {
            String longLink =  shortDomainService.findLongLink(shortLink);
            return ResultFactory.createSuccess(longLink);
        }catch (Exception e){
            log.error("短连接转化异常,msg:{}", ExceptionUtils.getStackTrace(e));
            return ResultFactory.createError(ResultCodeEnum.INNER_ERROR);
        }
    }

    @ApiOperation("将长域名转化为短域名")
    @GetMapping("/getShortDomain")
    public Result getShortDomain(@ApiParam("长连接地址") String longLink){
        try {
            String shortLink =  shortDomainService.generateShortLink(longLink);
            return ResultFactory.createSuccess(shortLink);
        }catch (Exception e){
            log.error("长接转化异常,msg:{}", ExceptionUtils.getStackTrace(e));
            return ResultFactory.createError(ResultCodeEnum.INNER_ERROR);
        }
    }
}
