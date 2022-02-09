package com.zm.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zm.demo.enums.ResultCodeEnum;
import com.zm.demo.service.UrlService;
import com.zm.demo.vo.Result;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequestMapping("/url")
public class UrlController {

    @Autowired
    UrlService urlService;

    /**
    * 接受长域名信息，返回短域名信息
    * @Param: longUrl 长链接
    * @return: java.lang.String
    * @Author: zhaomin
    * @Date: 2021/10/29 17:12
    */
    @GetMapping("/shortUrl")
    @ApiOperation(value = "接受长域名信息，返回短域名信息", notes = "")
    @ApiImplicitParam(name = "longUrl", value = "长链接地址", required = true)
    public Result shortUrl(String longUrl) {
        if (longUrl == null || longUrl.length() == 0) {
            return Result.error(ResultCodeEnum.PARAM_EMPTY);
        }

        String shortUrl = urlService.shortUrl(longUrl);

        return Result.success(shortUrl);
    }

    /**
     * 接受短域名信息，返回长域名信息
     *
     * @Param: shortUrl 短链接
     * @return: java.lang.String
     * @Author: zhaomin
     * @Date: 2021/10/29 17:12
     */
    @GetMapping("/longUrl")
    @ApiOperation(value = "接受短域名信息，返回长域名信息", notes = "")
    @ApiImplicitParam(name = "shortUrl", value = "短链接地址", required = true)
    public Result longUrl(String shortUrl) {
        if (shortUrl == null || shortUrl.length() == 0) {
            return Result.error(ResultCodeEnum.PARAM_EMPTY);
        }

        String longUrl = urlService.longUrl(shortUrl);

        return Result.success(longUrl);
    }

}



