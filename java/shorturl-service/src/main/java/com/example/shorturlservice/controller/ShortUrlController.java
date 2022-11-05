package com.example.shorturlservice.controller;

import com.example.shorturlservice.domain.BStatusCode;
import com.example.shorturlservice.domain.BizException;
import com.example.shorturlservice.domain.QueryShortUrlRequest;
import com.example.shorturlservice.domain.SaveLongUrlRequest;
import com.example.shorturlservice.domain.SysResult;
import com.example.shorturlservice.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 短域名的存储和读取入口
 * @Author xingxing.yu
 * @Date 2022/04/15 17:49
 **/
@RestController
@Api(value = "ShortUrlController", tags = "短域名服务")
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @RequestMapping(value = "/checkHealth")
    @ApiOperation(value = "checkHealth", notes = "checkHealth", httpMethod = "GET")
    public String checkHealth() {
        return "success";
    }

    @RequestMapping(value = "/saveLongUrl", method = RequestMethod.POST, produces = "application/json")
    @ApiImplicitParam(paramType = "body", dataTypeClass = SaveLongUrlRequest.class, name = "longUrlRequest", value = "长域名", required = true)
    @ApiOperation(value = "长域名转换为短域名", notes = "接受长域名信息，返回短域名信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public SysResult saveLongUrl(@RequestBody SaveLongUrlRequest longUrlRequest) {
        SysResult result = new SysResult();
        try {
            String longUrl = longUrlRequest.getLongUrl();
            String url = shortUrlService.saveLongUrl(longUrl);
            result.setData(url);
        } catch (BizException e) {
            result.setBstatus(e.getCode(), e.getMessage());

        } catch (Exception e) {
            result.setBstatus(BStatusCode.SERVER_ERROR.getCode(), BStatusCode.SERVER_ERROR.getDes());
        }

        return result;
    }

    @RequestMapping(value = "/getShortUrl", method = RequestMethod.POST, produces = "application/json")
    @ApiImplicitParam(paramType = "body", dataTypeClass = QueryShortUrlRequest.class, name = "shortUrlRequest", value = "短域名", required = true)
    @ApiOperation(value = "查询长域名信息", notes = "接受短域名信息，返回长域名信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public SysResult getShortUrl(@RequestBody QueryShortUrlRequest shortUrlRequest) {

        SysResult result = new SysResult();
        try {
            String shortUrl = shortUrlRequest.getShortUrl();
            String url = shortUrlService.getShortUrl(shortUrl);
            result.setData(url);

        } catch (Exception e) {
            result.setBstatus(BStatusCode.SERVER_ERROR.getCode(), BStatusCode.SERVER_ERROR.getDes());
        }

        return result;
    }
}
