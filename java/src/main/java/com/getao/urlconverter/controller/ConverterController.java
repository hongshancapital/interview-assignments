package com.getao.urlconverter.controller;

import com.getao.urlconverter.dto.vo.GetLongUrlVO;
import com.getao.urlconverter.dto.vo.GetShortUrlVO;
import com.getao.urlconverter.service.UrlConverterService;
import com.getao.urlconverter.util.ConverterUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@RestController
public class ConverterController {

    @Autowired
    private UrlConverterService urlConverterService;


    @ApiOperation("获取短链接")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longUrl", value = "长链接Url", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/getShortUrl", method = RequestMethod.GET)
    public GetShortUrlVO getShortUrl(String longUrl) {
        if(!ConverterUtil.isLegalUrl(longUrl)) {
            log.error("Illegal long URL : {}", longUrl);
            return new GetShortUrlVO(401, null, "Illegal long URL");
        }
        return urlConverterService.getShortUrl(longUrl);
    }

    @ApiOperation("获取长链接")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shortUrl", value = "短链接Url", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/getLongUrl", method = RequestMethod.GET)
    public GetLongUrlVO getLongUrl(String shortUrl) {
        if(!ConverterUtil.isLegalShort(shortUrl)) {
            log.error("Illegal short URL : {}", shortUrl);
            return new GetLongUrlVO(401, null, "Illegal short URL");
        }
        return urlConverterService.getLongUrl(shortUrl);
    }
}