package com.sequoiacap.shorturl.controller;

import com.sequoiacap.shorturl.common.constant.StatusCode;
import com.sequoiacap.shorturl.common.entity.BaseResult;
import com.sequoiacap.shorturl.common.entity.GetShortUrlResult;
import com.sequoiacap.shorturl.common.entity.GetUrlResult;
import com.sequoiacap.shorturl.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;


@Api(value = "短域名接口", tags = {"短域名接口"})
@Controller
@RequestMapping(value = "/shorturl")
public class IndexController {


    @Autowired
    protected UrlService urlService;

    /**
     * 短域名存储接口
     * @param url 长域名
     * @return
     */

    @ApiOperation(value = "短域名存储接口")
    @ResponseBody
    @RequestMapping(value = "/get_short_url",method = {RequestMethod.GET,RequestMethod.POST})
    public GetShortUrlResult getShortUrl(@ApiParam(value = "长域名地址", required = true) @RequestParam("url") String url) {

        if(StringUtils.isEmpty(url)){
            return new GetShortUrlResult(StatusCode.ERR_PARAMS,"url参数不能为空",null);
        }
        if(!Pattern.matches("(?i)http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", url)){
            return new GetShortUrlResult(StatusCode.ERR_PARAMS,"url格式不正确",null);
        }
        String key = urlService.getShortUrl(url);
        if(key==null){
            new GetShortUrlResult(StatusCode.FAILURE,"服务目前不可用",null);
        }
        return new GetShortUrlResult(StatusCode.SUCCESS,null,key);
    }

    /**
     * 短域名读取接口
     * @param key 短域名
     * @return
     */
    @ApiOperation(value = "短域名读取接口")
    @ResponseBody
    @RequestMapping(value = "/get_url",method = {RequestMethod.GET,RequestMethod.POST})
    public GetUrlResult getUrl(@ApiParam(value = "短域名地址", required = true)@RequestParam("key") String key) {

        if(StringUtils.isEmpty(key)){
            return new GetUrlResult(StatusCode.ERR_PARAMS,"key参数不能为空",null);
        }
        String url = urlService.getUrl(key);
        if(url!=null){
            return new GetUrlResult(StatusCode.SUCCESS,null,url);
        }else{
            return new GetUrlResult(StatusCode.NO_DATA,"无效key",null);
        }

    }


}