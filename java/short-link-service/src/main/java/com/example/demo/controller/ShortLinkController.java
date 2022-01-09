package com.example.demo.controller;

import com.example.demo.bean.po.ShortUrlPo;
import com.example.demo.bean.request.OriginRequest;
import com.example.demo.bean.request.ShortLinkRequest;
import com.example.demo.bean.response.ShortLinkResponse;
import com.example.demo.bean.result.Result;
import com.example.demo.bean.result.Results;
import com.example.demo.service.ShortLinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shenbing
 * @since 2022/1/8
 */
@Api(value = "短链接", tags = "短链接服务")
@RestController
public class ShortLinkController {

    @Resource
    ShortLinkService shortLinkService;

    @ApiOperation(value = "短域名存储接口", notes = "接受长域名信息，返回短域名信息")
    @PostMapping("/api/short/link/register")
    public Result<ShortLinkResponse> register(@RequestBody @Validated ShortLinkRequest req) {
        return Results.ok(shortLinkService.register(req.getOriginalUrl()));
    }

    @ApiOperation(value = "短域名读取接口", notes = "接受短域名信息，返回长域名信息")
    @GetMapping("/api/short/link/origin")
    public Result<String> getOriUrl(OriginRequest req) throws Exception {
        return shortLinkService.getOriUrl(req);
    }

    /**
     * 短连接跳转，例如：http://localhost:8081/BAA
     */
    @ApiOperation(value = "短连接跳转", notes = "使用短连接访问，跳转至原始连接地址")
    @GetMapping("/{shortUrl:[a-zA-Z0-9]+}")
    public void shortRedirect(HttpServletResponse response, @PathVariable("shortUrl") String shortUrl) throws IOException {
        ShortUrlPo shortUrlPo = shortLinkService.getShortUrl(shortUrl);
        if (shortUrlPo == null) {
            errorPath(response);
            return;
        }
        response.sendRedirect(shortUrlPo.getOriginalUrl());
    }

    protected void errorPath(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.addHeader("Content-Type", "application/json; charset=UTF-8");
        response.getWriter().println("{ \"status\": -1, \"msg\": \"短连接不存在\", \"data\": \"\" }");
        response.getWriter().flush();
        response.getWriter().close();
    }

}
