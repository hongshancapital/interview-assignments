package com.mx.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "基础功能测试")
@RestController
@RequestMapping("/api")
public class ApiController {

    private final Logger log=LoggerFactory.getLogger(ApiController.class);

    @ApiOperation("一般功能")
    @GetMapping("/exec")
    public String exec(String msg){
        return "try:".concat(msg);
    }

    @ApiOperation("打印错误日志")
    @GetMapping("/error")
    public String error(String errmsg){
        log.error(errmsg);
        return errmsg;
    }
}
