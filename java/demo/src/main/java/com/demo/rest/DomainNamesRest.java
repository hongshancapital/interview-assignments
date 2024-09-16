package com.demo.rest;

import com.demo.dto.ResponseDTO;
import com.demo.service.DomianNamesService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author syd
 * @description
 * @date 2022/1/11
 */
@Api("长短域名转换")
@RestController
@RequestMapping("/domain")
@Slf4j
public class DomainNamesRest {
    @Autowired
    DomianNamesService domianNamesService;

    /**
     * 长域名转短域名
     *
     * @param url
     * @return
     */
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 400, message = "请求参数错误")})
    @ApiOperation(value = "长域名转短域名", notes = "长短域名转换")
    @GetMapping("/shorten")
    public ResponseDTO shorten(@ApiParam(value = "长域名", required = true) @RequestParam("url") String url) {
        return domianNamesService.shorten(url);
    }

    /**
     * 短域名获取长域名
     *
     * @param url 短域名地址
     * @return
     */
    @ApiOperation(value = "短域名获取长域名", notes = "长短域名转换")
    @GetMapping("/longer")
    public ResponseDTO longUrl(@ApiParam(value = "短域名", required = true) @RequestParam("url") String url) {
        return domianNamesService.longer(url);
    }
}
