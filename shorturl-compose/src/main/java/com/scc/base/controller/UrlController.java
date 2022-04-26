package com.scc.base.controller;

import com.scc.base.constant.NumberConstants;
import com.scc.base.entity.JsonResult;
import com.scc.base.service.UrlService;
import com.scc.base.util.NumberTransformUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author renyunyi
 * @date 2022/4/24 4:04 PM
 * @description short url controller
 **/
@Slf4j
@RestController
@Api
public class UrlController {

    @Resource
    private UrlService shortUrlService;

    /**
     * get short url that the long url mapped
     * @param longUrl long url
     * @return result wrapper of short URL
     */
    @ApiOperation(value = "get short url that the long url mapped, if short url does not exist, it will create a short url based on long url")
    @ApiResponse(code = 0, message = "success", reference = "String")
    @RequestMapping(path = "/getShortUrl", method = RequestMethod.GET)
    public JsonResult getShortUrl(@ApiParam(name = "longUrl", value = "long url string", example = "/abscdesfshelelejcle/52062-6-56365352/7759dwmfwvg") String longUrl){
        return JsonResult.getSuccessResult(shortUrlService.getShortUrlFrom(longUrl));
    }

    /**
     * get long url that the short url mapped
     * @param shortUrl short url
     * @return result wrapper of long URL
     */
    @ApiOperation(value = "get long url that the short url mapped, if long url does not exist, it will return null")
    @RequestMapping(path = "/getLongUrl", method = RequestMethod.GET)
    public JsonResult getLongUrl(@ApiParam(name = "shortUrl", value = "short url string", example = "1iL") String shortUrl){

        if (shortUrl.length() > NumberConstants.BASE_62_MAX_LENGTH){
            log.info("short url length is bigger than {} : {}", NumberConstants.BASE_62_MAX_LENGTH, shortUrl);
            return JsonResult.getErrorResult("short url length should be shorter than " + NumberConstants.BASE_62_MAX_LENGTH);
        }

        if (NumberTransformUtils.isBase62(shortUrl)){
            String longUrl = shortUrlService.getLongUrlFrom(shortUrl);
            if (StringUtils.isBlank(longUrl)){
                log.error("long url is null, short url:{}",shortUrl);
                return JsonResult.getErrorResult();
            }
            return JsonResult.getSuccessResult(longUrl);
        }

        log.info("short url is not base 62 : {}", shortUrl);
        return JsonResult.getErrorResult(shortUrl + " is not base 62!");
    }

}
