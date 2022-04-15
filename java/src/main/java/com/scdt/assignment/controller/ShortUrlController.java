package com.scdt.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scdt.assignment.config.Msg;
import com.scdt.assignment.controller.bo.ShortUrlBo;
import com.scdt.assignment.repository.ShortUrl;
import com.scdt.assignment.service.ShortUrlService;
import com.xiesx.fastboot.support.validate.annotation.VBlank;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import io.swagger.annotations.*;

/**
 * @title ShortUrlController.java
 * @description
 * @author
 * @date 2022-04-15 17:10:39
 */
@Api(tags = "短链接")
@RestController
@RequestMapping(value = "/api/short-url")
@Validated
public class ShortUrlController {

    @Autowired
    ShortUrlService mShortUrlService;

    /**
     * 获取
     *
     * @param vo
     * @return
     */
    @ApiOperation(value = "获取")
    @ApiImplicitParams({@ApiImplicitParam(name = "longurl", value = "长链接", dataType = "String", required = true)})
    @ApiResponses({@ApiResponse(code = 911, message = "链接格式错误")})
    @GetMapping("/create")
    public ShortUrlBo create(@VBlank(message = "链接参数错误") String longurl) {
        // 格式判断
        if (!HttpUtil.isHttp(longurl) && !HttpUtil.isHttps(longurl)) {
            throw Msg.INVALID;
        }
        return mShortUrlService.create(longurl);
    }

    /**
     * 查询
     *
     * @param vo
     * @return
     */
    @ApiOperation(value = "查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "shorturl", value = "短链接", dataType = "String", required = true)})
    @ApiResponses({//
            @ApiResponse(code = 911, message = "链接格式错误"), //
            @ApiResponse(code = 921, message = "链接长度错误"), //
            @ApiResponse(code = 922, message = "查询没有记录")})
    @GetMapping("/query")
    public ShortUrlBo query(@VBlank(message = "短链接参数错误") String shorturl) {
        // 获取后缀
        String suffix = StrUtil.subAfter(shorturl, "/", true);
        // 有效判断
        if (!HttpUtil.isHttp(shorturl) && !HttpUtil.isHttps(shorturl)) {
            throw Msg.INVALID;
        }
        if (!shorturl.contains(ShortUrl.DOMAIN)) {
            throw Msg.INVALID;
        } else if (suffix.length() != 8) {
            throw Msg.LENGTH;
        }
        return mShortUrlService.query(shorturl);
    }
}
