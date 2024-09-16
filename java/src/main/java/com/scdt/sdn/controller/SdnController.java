/**
 * Project: scdt-sdn
 * File created at 2022/3/13 21:18
 * Copyright (c) 2018 linklogis.com all rights reserved.
 */
package com.scdt.sdn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scdt.sdn.dto.ResponseDto;
import com.scdt.sdn.service.SdnService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 功能描述
 *
 * @author donghang
 * @version 1.0
 * @type SdnController
 * @date 2022/3/13 21:18
 */
@RequestMapping(value = "/sdn")
@RestController
@Api(tags = "内部控制层")
public class SdnController {
    @Autowired
    private SdnService sdnService;

    @ApiOperation(value = "生成短域名", notes = "接口功能说明：生成短域名")
    @PostMapping("/encode")
    public ResponseDto<String> encode(@ApiParam(value = "长url") @RequestParam String url) {
        return ResponseDto.instanceOf(sdnService.encode(url));
    }

    @ApiOperation(value = "解析短域名", notes = "接口功能说明：解析短域名")
    @PostMapping("/decode")
    public ResponseDto<String> decode(@ApiParam(value = "短url") @RequestParam String url) {
        return ResponseDto.instanceOf(sdnService.decode(url));
    }

}
