package com.scdtchina.interview.controller;

import com.scdtchina.interview.dto.req.GetLongUrlReq;
import com.scdtchina.interview.dto.req.GetShortUrlReq;
import com.scdtchina.interview.dto.rsp.BaseRsp;
import com.scdtchina.interview.service.DomainRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/v1")
@Api(value = "域名转换", tags = {"域名转换"})
public class DomainConverterController {
    @Resource
    private DomainRelationService domainRelationService;

    @ApiOperation(value = "根据长域名生成短域名")
    @PostMapping(value = "/getShortUrl")
    public ResponseEntity<BaseRsp> getShortUrl(@Validated @RequestBody GetShortUrlReq req) {
        return ResponseEntity.ok(BaseRsp.builder().data(domainRelationService.shortenUrl(req.getUrl())).build());
    }

    @ApiOperation(value = "根据短域名返回长域名")
    @PostMapping(value = "/getLongUrl")
    public ResponseEntity<BaseRsp> getLongUrl(@Validated @RequestBody GetLongUrlReq req) {
        return ResponseEntity.ok(BaseRsp.builder().data(domainRelationService.getLongUrl(req.getUrl())).build());
    }
}
