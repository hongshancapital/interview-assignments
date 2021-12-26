package com.sequoia.domain.controller;

import com.sequoia.domain.entity.Response;
import com.sequoia.domain.service.IShortDomainService;
import com.sequoia.domain.util.UrlChecker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@ResponseBody
@Controller
@RequestMapping("/v1/url")
@Api("Short Domain restful API")
public class ShortDomainController {
    @Resource
    private IShortDomainService domainService;

    /**
     * 生成短域名接口
     *
     * @param url 经过base64编码后的长域名
     * @return 生成的短域名
     */
    @ApiOperation(value = "根据长域名转换为短域名", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Response.class),
            @ApiResponse(code = 400, message = "参数错误", response = Response.class)
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "url", value = "长域名链接（Base64编码）", required = true, paramType = "query", dataType = "string")})
    @GetMapping
    public Response<String> covertToShortUrl(@RequestParam String url) {
        UrlChecker.check(url);
        return new Response<String>().success(domainService.toShortUrl(url));
    }

    @ApiOperation(value = "根据短域名获取原始长域名", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Response.class),
            @ApiResponse(code = 400, message = "参数错误", response = Response.class)
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "url", value = "短域名链接（Base64编码）", required = true, paramType = "query", dataType = "string")})
    @GetMapping("/restore")
    public Response<String> restoreToLongUtl(@RequestParam() String url) {
        UrlChecker.check(url);
        return new Response<String>().success(domainService.restoreToOriginUrl(url));
    }
}
