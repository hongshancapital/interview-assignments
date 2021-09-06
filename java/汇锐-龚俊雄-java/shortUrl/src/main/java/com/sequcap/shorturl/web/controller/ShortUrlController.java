package com.sequcap.shorturl.web.controller;

import com.sequcap.shorturl.service.ShortUrlConvertService;
import com.sequcap.shorturl.web.model.UrlModel;
import com.sequcap.shorturl.web.resp.ShortUrlResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/shortUrl", produces = { MediaType.APPLICATION_JSON_VALUE })
@Api(tags = { "长Url转短Url" })
public class ShortUrlController {

    Logger log = LoggerFactory.getLogger(ShortUrlController.class);

    private final ShortUrlConvertService convertService;

    public ShortUrlController(ShortUrlConvertService convertService) {
        this.convertService = convertService;
    }

    @ApiOperation(value = "长Url转短Url")
    @PostMapping(value = "/long2Short")
    public ShortUrlResp<String> long2Short(@ApiParam(value = "长Url") @Valid @RequestParam("longUrl")  String longUrl) {
        log.info("[***Long URL convert to short URL, longUrl={} ***]", longUrl);
        UrlModel urlModel = convertService.long2Short(longUrl);
        if (urlModel == null) {
            return ShortUrlResp.error("System internal error.");
        }
        return ShortUrlResp.ok(urlModel.getShortUrl());
    }

    @ApiOperation(value = "通过短Url获取长Url")
    @PostMapping(value = "/getLongUrl")
    public ShortUrlResp<String> getLongUrlByshort(@ApiParam(value = "短Url") @Valid @RequestParam("shortUrl")  String shortUrl) {
        log.info("[***Get long URL by short URL, shortUrl={} ***]", shortUrl);
        UrlModel urlModel = convertService.getLongUrlByShort(shortUrl);
        if (urlModel == null) {
            return ShortUrlResp.error("404", "The long url is not exist or expired.");
        }

        return ShortUrlResp.ok(urlModel.getLongUrl());
    }
}
