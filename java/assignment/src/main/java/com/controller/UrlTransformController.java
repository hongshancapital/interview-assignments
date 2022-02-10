package com.controller;

import com.constant.CommonConstants;
import com.exception.SystemException;
import com.model.ShortUrlModel;
import com.service.IUrlTransformService;
import com.validator.UrlTransformValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/UrlTransform")
@Api(tags = {"短域名服务"}, description = "短域名长域名转换服务")
public class UrlTransformController {

    @Autowired
    private UrlTransformValidator urlTransformValidator;

    @Autowired
    private IUrlTransformService iUrlTransformService;
    @GetMapping("/getShortUrl")
    @ApiOperation("查询短域名")
    @ApiImplicitParam(name = "longUrl", value = "长链接", dataType = "String", paramType ="query", required = true, defaultValue = "www.bayer.com")
    public ShortUrlModel getShortUrl(@RequestParam String longUrl){
        ShortUrlModel model = new ShortUrlModel();
        //校验
        urlTransformValidator.validateGetShortUrl(longUrl);
        try{
            String shortUrl = iUrlTransformService.getShortUrl(longUrl);
            model.setShortUrlStr(shortUrl);
            model.setCode(CommonConstants.CODE_SUCCESS);
            model.setMessage(CommonConstants.MSG_SUCCESS);
        }catch (SystemException ex){
            model.setCode(ex.getCode());
            model.setMessage(ex.getMessage());
            return model;
        }
        return model;
    }
    @GetMapping("/getLongUrl")
    @ApiOperation("查询短域名")
    @ApiImplicitParam(name = "shortUrl", value = "短链接", dataType = "String", paramType ="query", required = true, defaultValue = "http://t.cn/ABCDEF")
    public ShortUrlModel getLongUrl(@RequestParam String shortUrl){
        ShortUrlModel model = new ShortUrlModel();
        //校验
        urlTransformValidator.validateGetLongUrl(shortUrl);
        try{
            String longUrl = iUrlTransformService.getLongUrl(shortUrl);
            model.setLongUrlStr(longUrl);
            model.setCode(CommonConstants.CODE_SUCCESS);
            model.setMessage(CommonConstants.MSG_SUCCESS);
        }catch (SystemException ex){
            model.setCode(ex.getCode());
            model.setMessage(ex.getMessage());
            return model;
        }
        return model;
    }
}
