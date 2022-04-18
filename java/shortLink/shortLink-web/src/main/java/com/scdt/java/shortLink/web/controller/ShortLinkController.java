package com.scdt.java.shortLink.web.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scdt.java.shortLink.component.service.ShortLinkService;
import com.scdt.java.shortLink.component.constant.ServiceException;
import com.scdt.java.shortLink.web.param.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "控制器--短链接")
@RestController
public class ShortLinkController {

    @Resource
    private ShortLinkService shortLinkService;

    @ApiOperation("短域名存储接口：接受长域名信息，返回短域名信息")
    @GetMapping("/getShortLink")
    public Response getShortLink(@ApiParam(value = "长域名", required = true) @RequestParam String longLink) {
        if (StringUtils.isEmpty(longLink)) {
            return Response.fail("链接不能为空");
        }

        try {
            return Response.success(shortLinkService.getShortLink(longLink));
        } catch (ServiceException ex) {
            return Response.fail(ex.getMessage());
        } catch (Exception e) {
            return Response.systemError();
        }
    }

    @ApiOperation("短域名读取接口：接受短域名信息，返回长域名信息")
    @GetMapping("/restoreLongLink")
    public Response restoreLongLink(@ApiParam(value = "短域名", required = true) @RequestParam String shortLink) {
        if (StringUtils.isEmpty(shortLink)) {
            return Response.fail("链接不能为空");
        }

        try {
            return Response.success(shortLinkService.restoreLongLink(shortLink));
        } catch (ServiceException ex) {
            return Response.fail(ex.getMessage());
        } catch (Exception e) {
            return Response.systemError();
        }
    }
}
