package com.assignment.controller;

import com.assignment.model.ResponseVO;
import com.assignment.service.ConvertService;
import com.assignment.utils.ConvertUtil;
import com.assignment.utils.LRUCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Api(tags = "域名转换服务")
@RestController
@RequestMapping("/convert")
public class ConvertController {

    @Autowired
    private ConvertService convertService;


    @ApiOperation("长连接转换短连接")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "longUrl", value = "长连接地址")
    })
    @GetMapping("/l2s")
    public ResponseVO DomainLong2Short(@RequestParam @Valid @NotEmpty(message = "longUrl不能为空") String longUrl) {
        if (longUrl.length() > 500) {
            return ResponseVO.error("URL超长了");
        }
        return ResponseVO.success(convertService.l2s(longUrl));
    }
    @ApiOperation("短连接转换长连接")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "shortUrl", value = "短连接地址")
    })
    @GetMapping("/s2l")
    public ResponseVO DomainShort2Long(@RequestParam @Valid @NotEmpty(message = "shortUrl不能为空")String shortUrl) {
        return ResponseVO.success(convertService.s2l(shortUrl));
    }

}
