package com.david.urlconverter.controller;

import com.david.urlconverter.common.ResponseMessage;
import com.david.urlconverter.common.ResultStatusCode;
import com.david.urlconverter.common.UrlConverterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.david.urlconverter.service.web.IUrlConverterService;

/**
 * 域名转换API
 */
@RestController
@Slf4j
@RequestMapping(value="/url")
@Api("域名转换")
public class UrlConverterController {

    @Autowired
    private IUrlConverterService urlConverterService;



    @ApiOperation("探活测试空接口")
    @RequestMapping ("/test")
    public ResponseMessage test(){
        return ResponseMessage.ok();
    }

    @ApiOperation("生成短域名")
    @ApiImplicitParam(name="longUrl",value="长域名",required = true,dataType = "String", paramType = "query")
    @RequestMapping ("/short/generate")
    public ResponseMessage generateShortUrl(@RequestParam String longUrl){
        log.info("generateShortUrl 入参：{}",longUrl);
        if(StringUtils.isBlank(longUrl)){
            ResponseMessage fail = ResponseMessage.failed(ResultStatusCode.EMPTY_PARAM);
            return fail;
        }
        String shortUrl = null;
        try {
            shortUrl = urlConverterService.generateShortUrl(longUrl);
        } catch(UrlConverterException e) {
            ResponseMessage fail = ResponseMessage.failed(e.getStatusCode());
            return fail;
        }
        ResponseMessage ok = ResponseMessage.ok();
        ok.setShortUrl(shortUrl);
        return ok;
    }

    @ApiOperation("查询长域名")
    @ApiImplicitParam(name="shortUrl",value="短域名",required = true,dataType = "String", paramType = "query")
    @RequestMapping ("/long/retrieve")
    public ResponseMessage retrieveLongUrl(@RequestParam String shortUrl){
        log.info("retrieveLongUrl 入参：{}",shortUrl);
        if(StringUtils.isBlank(shortUrl)){
            ResponseMessage fail = ResponseMessage.failed(ResultStatusCode.EMPTY_PARAM);
            return fail;
        }
        String longUrl = null;
        try {
            longUrl = urlConverterService.retrieveLongUrl(shortUrl);
        } catch (UrlConverterException e) {
            ResponseMessage fail = ResponseMessage.failed(e.getStatusCode());
            return fail;
        }

        if(StringUtils.isBlank(longUrl)){
            ResponseMessage fail = ResponseMessage.failed(ResultStatusCode.INVALID_SHORT_URL);
            return fail;
        }

        ResponseMessage  ok = ResponseMessage.ok();
        ok.setLongUrl(longUrl);
        return ok;
    }
}