package com.qinghaihu.shorturl.controller;

import com.qinghaihu.shorturl.entity.JsonSimpleResult;
import com.qinghaihu.shorturl.entity.ResolveUrlInfo;
import com.qinghaihu.shorturl.entity.SaveUrlInfo;
import com.qinghaihu.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "短域名服务")
@RequestMapping("/shorturl")
@RestController
public class ShorUrlController {

    @Autowired
    ShortUrlService shortUrlService;

    @ApiOperation(value = "短域名存储接口")
    @PostMapping("/getAndSave")
    public JsonSimpleResult getAndSaveShortUrl(@RequestBody SaveUrlInfo saveUrlInfo){
        JsonSimpleResult result = new JsonSimpleResult();
        try{
            String shortUrl = shortUrlService.transferToShortUrl(saveUrlInfo.getLongUrl());
            result.setSuccess(true);
            result.setErrMsg("success");
            result.setResult(shortUrl);
        }catch (Exception ex){
            result.setSuccess(false);
            result.setErrMsg(ex.getMessage());
            result.setResult("");
        }
        return  result;
    }

    @ApiOperation(value = "短域名读取接口")
    @PostMapping("/resolveShortUrl")
    public JsonSimpleResult resolveShortUrl(@RequestBody ResolveUrlInfo resolveUrlInfo){
        JsonSimpleResult result = new JsonSimpleResult();
        try{
            String longUrl = shortUrlService.transferToLongUrl(resolveUrlInfo.getShortUrl());
            result.setSuccess(true);
            result.setErrMsg("success");
            result.setResult(longUrl);
        }catch (Exception ex){
            result.setSuccess(false);
            result.setErrMsg(ex.getMessage());
            result.setResult("");
        }
        return  result;
    }

}
