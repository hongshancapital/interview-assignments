package com.tizzy.business.controller;

import com.tizzy.business.dto.UrlLongRequest;
import com.tizzy.business.exception.EntityNotFoundException;
import com.tizzy.business.response.ResponseMessage;
import com.tizzy.business.service.ShortUrlService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/shorturl/api/v1")
public class ShortUrlController {

    private static final Logger logger = LoggerFactory.getLogger(ShortUrlController.class);

    @Autowired
    private ShortUrlService urlService;

    @PostMapping("/shorten")
    public ResponseMessage convertToShortUrl(@RequestBody @Valid UrlLongRequest request) {
        String longUrl = request.getLongUrl();
        long expireTime = request.getExpiresTime();

        String shortUrl = urlService.convertToShortUrl(longUrl, expireTime);
        return new ResponseMessage(shortUrl, longUrl, true, "short url have got");
    }

    @GetMapping(value = "/parse/{shortUrl}")
    public ResponseMessage getAndRedirect(@PathVariable String shortUrl) {
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
        return new ResponseMessage(shortUrl, url, true, "original url have got.");
    }
}
