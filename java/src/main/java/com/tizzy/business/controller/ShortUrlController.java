package com.tizzy.business.controller;

import com.tizzy.business.dto.UrlLongRequest;
import com.tizzy.business.exception.EntityNotFoundException;
import com.tizzy.business.response.ResponseMessage;
import com.tizzy.business.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Api(tags = "短域名服务")
@RestController
@RequestMapping("/url/api/v1")
public class ShortUrlController {

    private static final Logger logger = LoggerFactory.getLogger(ShortUrlController.class);

    @Autowired
    private ShortUrlService urlService;

    @ApiOperation(value = "获取一个新的短连接", notes = "将长链接转化为短链接")
    @PostMapping("/shorten")
    public ResponseMessage convertToShortUrl(@RequestBody @Valid UrlLongRequest request) {
        String longUrl = request.getLongUrl();
        long expireTime = request.getExpiresTime();

        String shortUrl = urlService.convertToShortUrl(longUrl, expireTime);
        return new ResponseMessage(shortUrl, longUrl, true, "short url have got");
    }

    @ApiOperation(value = "重定向到原链接", notes = "将短链接转化为原链接，并重定向")
    @ApiImplicitParam(name = "shortUrl", value = "短链接", required = true)
    @GetMapping(value = "/parse/{shortUrl}")
    public Object getAndRedirect(@PathVariable String shortUrl) {
        if (StringUtils.isEmpty(shortUrl) || StringUtils.isEmpty(shortUrl.trim())) {
            return new ResponseMessage("", "", false, "param shortUrl is wrong.");
        }

        String url = null;
        try {
            url = urlService.getOriginalUrl(shortUrl);
        } catch (EntityNotFoundException e) {
            logger.info("not fount original url.");
            return new ResponseMessage(shortUrl, "", false, "original url is not found.");
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}
