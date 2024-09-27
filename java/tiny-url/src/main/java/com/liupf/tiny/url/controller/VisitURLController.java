package com.liupf.tiny.url.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liupf.tiny.url.service.ITinyURLService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * ting-url 访问
 */
@Controller
@RequestMapping("/v")
@Api(value = "tiny-url访问", tags = {"tiny-url访问"})
public class VisitURLController {

    @Resource
    private ITinyURLService tinyURLService;

    @GetMapping("/{code}")
    @ApiOperation("短网址访问，重定向到场地址")
    public void redirect(@PathVariable String code, HttpServletResponse response) {
        String longUrl = tinyURLService.getLongUrlByCode(code);
        if (StringUtils.isBlank(longUrl)) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } else {
            response.setHeader("Location", longUrl);
            response.setStatus(HttpStatus.FOUND.value());
        }
    }
}
