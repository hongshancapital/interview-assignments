package com.example.shorturlservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xingxing.yu on 2022/3/22.
 */
@RestController
@Api(value = "ShortUrlController", tags = "短域名服务")
public class ShortUrlController {

    @RequestMapping(value = "/checkHealth")
    @ApiOperation(value = "checkHealth", notes = "checkHealth")
    public String checkHealth() {
        return "success";
    }
}
