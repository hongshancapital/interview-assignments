package com.zc.shorturl.controller;

import com.zc.shorturl.dto.BaseResponse;
import com.zc.shorturl.dto.ResultCode;
import com.zc.shorturl.service.ShortUrlService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@Slf4j
public class RedirectController {
    private final ShortUrlService shortUrlService;

    @ApiOperation(value = "Redirect", notes = "Finds original url from short url and redirects")
    @GetMapping(value = "/{shortUrl}")
    public ResponseEntity<BaseResponse> GetLongUrl(@PathVariable String shortUrl){
        // TODO:生产屏蔽日志
        log.info(shortUrl);
        String longUrl = shortUrlService.getLongUrlFromCache(shortUrl);
        if (longUrl.equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(BaseResponse.fail(ResultCode.URL_NOT_FOUND));
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .body(BaseResponse.ok(null));
    }
}
