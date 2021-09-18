package com.example.app.api.v1;


import com.alibaba.fastjson.JSONObject;
import com.example.app.common.dto.GenerateReq;
import com.example.app.common.dto.ParseReq;
import com.example.app.common.exception.ModuleException;
import com.example.app.common.vo.FullUrlResp;
import com.example.app.common.vo.ShortUrlResp;
import com.example.app.common.vo.base.ResponseResult;
import com.example.app.service.DomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author voidm
 * @date 2021/9/18
 */
@Api(value = "短域名操作接口", tags = "短域名操作接口类")
@RestController
@RequestMapping("/short_domain")
@Validated
public class DomainController {

    @Autowired
    private DomainService domainService;
    public static final Logger log = LoggerFactory.getLogger(DomainController.class);


    @ApiOperation("根据长域名生成短域名")
    @PostMapping("/generate")
    public ResponseResult generate(@RequestBody @Validated GenerateReq generateReq) throws ModuleException {

        log.info("short_domain >> generate req[{}]", JSONObject.toJSONString(generateReq));
        String shortUrl = domainService.generateShortUrl(generateReq.getFullUrl());
        return ResponseResult.success(new ShortUrlResp(shortUrl));
    }

    @ApiOperation("根据短域名Key获取长域名")
    @GetMapping("/parse")
    public ResponseResult parse(@Validated ParseReq parseReq) throws ModuleException {
        log.info("short_domain >> parse req[{}]", JSONObject.toJSONString(parseReq));
        String fullUrl = domainService.parseWithShortUrl(parseReq.getShortUrl());
        return ResponseResult.success(new FullUrlResp(fullUrl));
    }

}