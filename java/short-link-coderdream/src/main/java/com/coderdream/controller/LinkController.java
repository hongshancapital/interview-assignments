package com.coderdream.controller;

import com.coderdream.common.Result;
import com.coderdream.common.ResultBuilder;
import com.coderdream.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 短链接控制器
 *
 * @author CoderDream
 * @version 1.0
 * @date 2022/5/8
 */
@RestController
@RequestMapping("/api/v1")
@EnableAutoConfiguration
@Api(value = "短链接控制器")
public class LinkController {
    @Resource
    private LinkService linkService;

    @GetMapping("/link/short")
    @ApiOperation(value = "获取短链接")
    public Result<String> getShortLink(@ApiParam("长链接地址") String longLink) {
        if (StringUtils.isEmpty(longLink)) {
            throw new IllegalArgumentException("longLink 不可为空");
        }
        String shortLink = linkService.getShortLink(longLink);

        return ResultBuilder.buildSuccess(shortLink);
    }

    @GetMapping("/link/long")
    @ApiOperation(value = "获取长链接")
    public Result<String> getLongLink(@ApiParam("短链接地址") String shortLink) {
        if (StringUtils.isEmpty(shortLink)) {
            throw new IllegalArgumentException("shortLink 不可为空");
        }
        String longLink = linkService.getLongLink(shortLink);

        return ResultBuilder.buildSuccess(longLink);
    }
}