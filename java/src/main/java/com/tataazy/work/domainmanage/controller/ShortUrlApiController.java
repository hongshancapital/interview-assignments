package com.tataazy.work.domainmanage.controller;


import com.tataazy.work.domainmanage.service.ShortUrlProcessServiceImpl;
import com.tataazy.work.domainmanage.utils.DomainUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.tataazy.work.domainmanage.core.Result;

@RestController
@Api("shortUrlProcess")
@RequestMapping("/domainapi/url/")
public class ShortUrlApiController {

    @Autowired
    private ShortUrlProcessServiceImpl shortUrlProcessService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "normalUrl", value = "正常域名", required = true, paramType = "body")
    })
    @ApiOperation(value = "get short domain name")
    @RequestMapping(value = "generateshorturl", method = RequestMethod.POST)
    @ResponseBody
    public Result generateShortUrl(@RequestBody String normalUrl) {
        Result<String> result = new Result<>();
        if (!DomainUtils.isUrlValid(normalUrl)) {
            result.setSuccess(false);
            result.setErrMsg("request url invalid!");
            return result;
        }
        String shortUrl = shortUrlProcessService.generateShorUrl(normalUrl);
        if (null == shortUrl) {
            result.setSuccess(false);
            result.setErrMsg("generateShorUrl exception!");
            return result;
        }
        result.setData(shortUrl);
        return result;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "shortUrl", value = "短域名", required = true, paramType = "query")
    })
    @ApiOperation(value = "get normal domain name")
    @RequestMapping(value = "getnormalurl", method = RequestMethod.GET)
    @ResponseBody
    public Result getNormalUrl(@RequestParam("shortUrl") String shortUrl) {
        Result<String> result = new Result<>();
        if (shortUrl.length() > 20) {
            result.setSuccess(false);
            result.setErrMsg("request url too long invalid!");
            return result;
        }

        if (!DomainUtils.isUrlValid(shortUrl)) {
            result.setSuccess(false);
            result.setErrMsg("request url invalid!");
            return result;
        }
        String normalUrl = shortUrlProcessService.getNormalUrl(shortUrl);
        if (null == normalUrl) {
            result.setSuccess(false);
            result.setErrMsg("getNormalUrl null!");
            return result;
        }
        result.setData(normalUrl);
        return result;
    }
}
