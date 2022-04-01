package com.getao.urlconverter.controller;

import com.getao.urlconverter.dto.param.GetLongUrlParam;
import com.getao.urlconverter.dto.param.GetShortUrlParam;
import com.getao.urlconverter.dto.vo.GetLongUrlVO;
import com.getao.urlconverter.dto.vo.GetShortUrlVO;
import com.getao.urlconverter.service.UrlConverterService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.StringUtils;

@RestController
public class ConverterController {

    private UrlConverterService urlConverterService;

    @ApiOperation("获取短链接")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longUrl", value = "长链接Url", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/getShortUrl", method = RequestMethod.GET)
    public GetShortUrlVO getShortUrl(String longUrl) {
        if (StringUtils.isEmpty(longUrl)) {
            GetShortUrlVO vo = new GetShortUrlVO();
            vo.setDescription("Empty URL cannot be converted.");
            return vo;
        }else{
            return urlConverterService.getShortUrl(longUrl);
        }
    }

    @ApiOperation("获取长链接")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shortUrl", value = "短链接Url", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/getLongUrl", method = RequestMethod.GET)
    public GetLongUrlVO getLongUrl(String shortUrl) {
        if(StringUtils.isEmpty(shortUrl)) {
            GetLongUrlVO vo = new GetLongUrlVO();
            vo.setDescription("Empty URL cannot be converted.");
            return vo;
        }else {
            return urlConverterService.getLongUrl(shortUrl);
        }

    }
}
