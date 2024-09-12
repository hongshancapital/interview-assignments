package com.interview.controller;

import com.interview.dto.Response;
import com.interview.log.WriteLog;
import com.interview.service.DomainService;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description :
 * @Author: nyacc
 * @Date: 2021/12/17 13:36
 */

@ResponseBody
@Controller
@RequestMapping("/domain")
@Api("长短域名转换接口")
public class DomainController {

    @Resource
    private DomainService domainService;


    @ApiOperation(value = "根据长域名转换为短域名", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "success", response = Response.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longUrl", value = "长域名链接", required = true,
                    paramType = "query", dataType = "string")})
    @PostMapping("/v1/shortUrl")
    @WriteLog(logLevel = WriteLog.LogLevel.Result)
    public Response<String> transLongUrlToShort(@RequestParam(required = true) String longUrl){
        return Response.buildSuccess(domainService.transLongUrlToShortUrl(longUrl));
    }


    @ApiOperation(value = "根据短域名转换为长域名", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "success", response = Response.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shortUrl", value = "短域名链接", required = true,
                    paramType = "query", dataType = "string")})
    @PostMapping("/v1/longUrl")
    @WriteLog(logLevel = WriteLog.LogLevel.Result)
    public Response<String> getLongUrlByShowUrl(@RequestParam(required = true) String shortUrl){
        return Response.buildSuccess(domainService.getLongUrlByShortUrl(shortUrl));
    }



}
