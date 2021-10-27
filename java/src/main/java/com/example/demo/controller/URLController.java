package com.example.demo.controller;

import com.example.demo.service.URLService;
import com.example.demo.request.QueryOriginalURLRequest;
import com.example.demo.request.QueryShortURLRequest;
import com.example.demo.response.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("短域名API接口")
@RestController
public class URLController{

    @Autowired
    private URLService urlService;

    @ResponseBody
    @RequestMapping(value="/queryShortURL", method=RequestMethod.POST )
    @ApiOperation(value="短域名存储接口", httpMethod = "POST", notes="接受长域名信息，返回短域名信息", produces="application/json", response = BaseResponse.class)
    public BaseResponse queryShortURL(
            @Valid @ApiParam(value = "请求对象", required = true) @RequestBody QueryShortURLRequest request){
        String originalURL = request.getOriginUrl();
        return urlService.queryShortURL(originalURL);
    }

    @ResponseBody
    @RequestMapping(value="/queryOriginalURL", method=RequestMethod.POST)
    @ApiOperation(value="短域名读取接口", httpMethod = "POST", notes="接受短域名信息，返回长域名信息", produces="application/json", response = BaseResponse.class)
    public BaseResponse queryOriginalURL(
            @Valid @ApiParam(value = "请求对象", required = true) @RequestBody QueryOriginalURLRequest request){
        String shortURL = request.getShortURL();
        return urlService.queryOriginalURL(shortURL);
    }
}
