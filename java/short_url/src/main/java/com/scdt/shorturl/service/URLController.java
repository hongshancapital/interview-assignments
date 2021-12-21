package com.scdt.shorturl.service;

import com.scdt.shorturl.exception.BizInValidException;
import com.scdt.shorturl.model.Record;
import com.scdt.shorturl.model.Res;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/short-urls/api/v1")
public class URLController {

    private final URLService urlService;
    private final BizValidator validator;

    public URLController(URLService urlService, BizValidator validator) {
        this.urlService = urlService;
        this.validator = validator;
    }

    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     *
     * @param longUrls 长域名集合
     * @return shortUrls
     */
    @Operation(summary = "短域名存储接口", description = "接受长域名信息，返回短域名信息")
    @PostMapping("/createShortUrl")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Res<List<Record>>> createShortUrlByLongUrl(@RequestBody Set<String> longUrls) {
        try {
            validator.validLongUrl(longUrls);
        } catch (BizInValidException bizInValidException) {
            return Mono.error(bizInValidException);
        }
        return urlService.createShortUrlByLongUrl(longUrls);
    }

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息。
     * 支持跨域请求
     * @param shortUrls 短域名集合
     * @return longUrls
     */
    @Operation(summary = "短域名读取接口", description = "接受短域名信息，返回长域名信息")
    @CrossOrigin
    @PostMapping("/getLongUrl")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Res<List<Record>>> getLongUrlByShortUrl(@RequestBody Set<String> shortUrls) {
        try {
            validator.validShortUrl(shortUrls);
        } catch (BizInValidException bizInValidException) {
            return Mono.error(bizInValidException);
        }
        return urlService.getLongUrlByShortUrl(shortUrls);
    }


}
