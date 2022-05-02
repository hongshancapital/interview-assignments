package com.mortimer.shortenurl.controller;

import com.mortimer.shortenurl.enums.ResponseStatusEnum;
import com.mortimer.shortenurl.model.ResponseModel;
import com.mortimer.shortenurl.service.UrlShortenService;
import com.mortimer.shortenurl.vo.LongUrl;
import com.mortimer.shortenurl.vo.UrlMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;

@Api(tags = "短域名服务接口")
@RestController
@Validated
public class UrlShortenController {
    @Autowired
    private UrlShortenService urlShortenService;

    @Autowired
    private HttpServletRequest request;

    @ApiOperation("短域名存储接口：接受长域名信息，返回短域名信息")
    @PostMapping("/api/v1/shorten")
    public ResponseModel<UrlMapping> createShortUrl(@RequestBody @Validated LongUrl longUrl) {
        return ResponseModel.success(new UrlMapping(urlShortenService.createShortUrl(longUrl.getUrl()),
                longUrl.getUrl()));
    }

    @ApiOperation("短域名读取接口：接受短域名信息，返回长域名信息")
    @GetMapping("/{shortUrl}")
    public ResponseModel<UrlMapping> getLongUrl(@ApiParam("短链地址") @PathVariable("shortUrl")
                                                    @Pattern(regexp = "^[0-9a-zA-Z]{1,8}$") String shortUrl) {
        String longUrl = urlShortenService.getLongUrl(shortUrl);
        if (longUrl == null) {
            return new ResponseModel<>(ResponseStatusEnum.FAIELD_NOT_EXIST_SHORTURL, null);
        }
        return ResponseModel.success(new UrlMapping(request.getRequestURL().toString(), longUrl));
    }
}
