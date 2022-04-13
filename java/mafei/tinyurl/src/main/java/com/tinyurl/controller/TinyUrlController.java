package com.tinyurl.controller;

import com.tinyurl.entity.TinyUrlEntity;
import com.tinyurl.response.ResponseCode;
import com.tinyurl.response.ResponseEntity;
import com.tinyurl.response.ResponseUtil;
import com.tinyurl.service.TinyUrlCodecService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "短网址服务")
@RestController
public class TinyUrlController {

    @Autowired
    private TinyUrlCodecService tinyUrlCodecService;

    @ApiOperation(value = "短网址生成")
    @PostMapping(value = "/encode.htm")
    public ResponseEntity<TinyUrlEntity> encode(@ApiParam(value = "网址") String url) {
        if (StringUtils.hasLength(url)) {
            TinyUrlEntity tinyUrlEntity = new TinyUrlEntity();
            tinyUrlEntity.setUrl(tinyUrlCodecService.encode(url));
            return ResponseUtil.buildResponse(ResponseCode.CODE_SUCCESS, tinyUrlEntity);
        } else {
            return ResponseUtil.buildResponse(ResponseCode.CODE_INPUT_NULL, null);
        }
    }

    @ApiOperation(value = "短网址还原")
    @PostMapping(value = "/decode.htm")
    public ResponseEntity<TinyUrlEntity> decode(@ApiParam(value = "网址") String url) {
        if (StringUtils.hasLength(url)) {
            TinyUrlEntity tinyUrlEntity = new TinyUrlEntity();
            tinyUrlEntity.setUrl(tinyUrlCodecService.decode(url));
            return ResponseUtil.buildResponse(ResponseCode.CODE_SUCCESS, tinyUrlEntity);
        } else {
            return ResponseUtil.buildResponse(ResponseCode.CODE_INPUT_NULL, null);
        }
    }
}
