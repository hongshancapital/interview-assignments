package com.domain.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：ji
 * @description：心跳请求
 */
@Api("心跳请求处理controller")
@RestController
public class IndexController {

    @ApiOperation(value = "心跳检测")
    @GetMapping("/index")
    public void index(){
    }
}
