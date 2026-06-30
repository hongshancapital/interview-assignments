package org.demo.shortlink.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.demo.shortlink.model.Result;
import org.demo.shortlink.service.ShortLinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *@author wsq
 *@date 2022/3/26 10:12
 *@description:
 */

@RestController
@Slf4j
@Api("短链接服务")
public class ShortLinkController {

    @Resource
    private ShortLinkService shortLinkService;

    @GetMapping("/getShortLink")
    @ApiOperation("长链生成短链")
    public Result getShortLink(@ApiParam(value = "长链地址", required = true) @RequestParam String longLink) {
        try {
            return Result.success(shortLinkService.generateShortLink(longLink));
        } catch (IllegalArgumentException e){
            log.error("getShortLink error",e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("getShortLink error", e);
            return Result.error("系统错误");
        }
    }

    @GetMapping("/getLongLink")
    @ApiOperation("短链获取长链")
    public Result getLongLink(@ApiParam(value = "短链地址", required = true) @RequestParam String shortLink){
        try {
            return Result.success(shortLinkService.findLongLink(shortLink));
        } catch (IllegalArgumentException e){
            log.error("getLongLink error",e);
            return Result.error(e.getMessage());
        } catch (Exception e){
            log.error("getLongLink error",e);
            return Result.error("系统错误");
        }
    }

}
