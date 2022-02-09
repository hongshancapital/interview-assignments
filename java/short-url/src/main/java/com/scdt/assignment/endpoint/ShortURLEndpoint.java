package com.scdt.assignment.endpoint;

import com.scdt.assignment.application.ShortURLApplicationService;
import com.scdt.assignment.infrastructure.utils.ParamsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器接口层
 */
@Api("短网址相关接口")
@RestController("/")
public class ShortURLEndpoint {

    @Autowired
    private ShortURLApplicationService service;

    @ApiOperation("根据原网址获取短网址")
    @ApiResponse(code = 200, message = "获取成功", response = String.class)
    @GetMapping("/getShortUrl/{url}")
    public String getShortUrl(@ApiParam(name = "原始地址", required = true, example = "https://www.baidu.com")
                               @PathVariable("url") String url) {
        url = ParamsUtil.validUrl(url);
        return service.getShortUrl(url);
    }


    @ApiOperation("根据长网址获取短网址")
    @GetMapping("/getOriginUrl/{url}")
    public String getOriginUrl(@ApiParam(name = "短网址", required = true)
                               @PathVariable("url") String url) {
        url = ParamsUtil.validUrl(url);
        return service.getOriginUrl(url);
    }

}
