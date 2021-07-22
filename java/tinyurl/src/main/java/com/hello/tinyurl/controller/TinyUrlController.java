package com.hello.tinyurl.controller;

import com.hello.tinyurl.service.TinyUtlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;

/**
 * @author: Shuai
 * @date: 2021-7-5 21:53
 * @description:
 */
@Api(value = "TinyUrlController", tags = "短链接服务接口")
@RestController
@RequestMapping
public class TinyUrlController {

    @Resource
    private TinyUtlService service;

    @ApiOperation(value = "saveOriginalUrl",
            httpMethod = "POST",
            response = String.class,
            notes = "短域名存储接口：接受长域名信息，返回短域名信息")
    @PostMapping("url/save")
    public Result<String> saveOriginalUrl(
            @ApiParam(name = "originalUrl", required = true) @RequestParam String originalUrl) {
        Result<String> result = new Result<>();
        if (!StringUtils.hasText(originalUrl))
            return new Result<>(400, "empty parameter.", null);
        try {
            return new Result<>(200, null, service.saveOriginalUrl(originalUrl));
        } catch (Exception e) {
            return new Result<>(500, e.getMessage(), null);
        }
    }

    @ApiOperation(value = "getOriginalUrl",
            httpMethod = "GET",
            response = ModelAndView.class,
            notes = "短域名读取接口：接受短域名信息，返回长域名信息。直接跳转至页面")
    @GetMapping("i/{tinyUrl}")
    public ModelAndView getOriginalUrl(
            @ApiParam(name = "tinyUrl", required = true) @PathVariable String tinyUrl) {
        if (!StringUtils.hasText(tinyUrl))
            return new ModelAndView("home");
        try {
            String originalUrl = service.getOriginalUrl(tinyUrl);
            if (originalUrl == null) {
                return new ModelAndView("404");
            } else
                return new ModelAndView(new RedirectView(originalUrl, true));
        } catch (Exception e) {
            return new ModelAndView("404");
        }
    }
}
