package com.hs.shortlink.controller;

import com.hs.shortlink.domain.common.ResultVo;
import com.hs.shortlink.domain.constant.ResultStatusEnum;
import com.hs.shortlink.service.UrlTransformService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: Dangerous
 * @time: 2022/5/13 21:12
 */
@RestController
@RequestMapping("/urlTransform")
@Api(tags = "长短域名转换服务")
@Validated
public class UrlTransformController {

    @Resource
    private UrlTransformService urlTransformService;

    @ApiOperation(value = "获取短域名", notes = "通过长域名和短域名长度获取短域名")
    @GetMapping("/getShortUrl")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "长域名", name = "longUrl", paramType = "query", required = true),
            @ApiImplicitParam(value = "短域名长度", name = "length", paramType = "query", required = true),
    })
    public ResultVo<String> getShortUrl(
            @NotNull(message = "指定长链接不能为空") String longUrl,
            @NotNull(message = "指定短链接长度不能为空") @Max(8) @Min(4) Integer length) {
        return new ResultVo<>(ResultStatusEnum.SUCCESSES, urlTransformService.getShortUrl(longUrl, length));
    }

    @ApiOperation(value = "获取长域名", notes = "通过短域名获取长域名")
    @GetMapping("/getLongUrl")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "短域名", name = "shortUrl", paramType = "query", required = true),
    })
    public ResultVo<String> getLongUrl(
            @NotNull(message = "指定短链接不能为空") String shortUrl) {
        return new ResultVo<>(ResultStatusEnum.SUCCESSES, urlTransformService.getLongUrl(shortUrl));
    }
}
