package com.scdt.shortlink.controller;

import com.scdt.shortlink.common.ResultBean;
import com.scdt.shortlink.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xbhong
 * @date 2022/4/12 23:43
 */
@RestController
@Api(tags = "短域名接口")
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @PostMapping(value = "/getShortLink")
    @ApiOperation("接受长域名信息，返回短域名信息")
    @ResponseBody
    public ResultBean getShortLink(@ApiParam(value = "长域名地址", required = true)
                                   @RequestParam String longLink) {
        if (StringUtils.isBlank(longLink)) {
            return ResultBean.create(400, "域名格式不正确", longLink);
        }
        String shortLink = linkService.createShortLink(longLink);
        if (StringUtils.isEmpty(shortLink)) {
            return ResultBean.fail("获取短域名失败", shortLink);
        }
        return ResultBean.success("获取短域名成功", shortLink);
    }

    @GetMapping(value = "/getLongLink")
    @ApiOperation("接受短域名信息，返回长域名信息")
    public ResultBean getLongLink(@ApiParam(value = "短域名地址", required = true)
                                  @RequestParam String shortLink) {
        if (StringUtils.isBlank(shortLink)) {
            return ResultBean.create(400, "短域名格式不正确", shortLink);
        }
        String longLink = linkService.getLongLink(shortLink);
        if (StringUtils.isNotEmpty(longLink)) {
            return ResultBean.success("获取长域名成功", longLink);
        }
        return ResultBean.fail("短域名信息不存在", shortLink);
    }
}
