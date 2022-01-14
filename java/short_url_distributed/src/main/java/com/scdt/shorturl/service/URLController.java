package com.scdt.shorturl.service;

import com.scdt.shorturl.distributed.DistributedLRUCacheService;
import com.scdt.shorturl.distributed.LocalLRUCacheService;
import com.scdt.shorturl.exception.BizInValidException;
import com.scdt.shorturl.model.Record;
import com.scdt.shorturl.model.Res;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/short-urls/api/v1")
public class URLController {

    private final LocalLRUCacheService localLRUCacheService;
    private final BizValidator validator;
    private final DistributedLRUCacheService distributedLruCacheService;

    public URLController(LocalLRUCacheService localLRUCacheService, BizValidator validator, DistributedLRUCacheService distributedLruCacheService) {
        this.localLRUCacheService = localLRUCacheService;
        this.validator = validator;
        this.distributedLruCacheService = distributedLruCacheService;
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
        return distributedLruCacheService.setOrGet(longUrls, DistributedLRUCacheService.METHOD_SET);
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
        return distributedLruCacheService.setOrGet(shortUrls, DistributedLRUCacheService.METHOD_GET);
    }

    //=======================================同步缓存RPC接口===================================

    /**
     * 收到leader发出的同步命令，开始同步本地缓存
     * @param method
     * @param urls
     * @return
     */
    @Operation(hidden = true)
    @PutMapping("/inner/sync/{method}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Res<List<Record>>> sync(@PathVariable String method, @RequestBody Set<String> urls){
        log.info("收到同步命令-"+method+":" + urls);
        if (DistributedLRUCacheService.METHOD_GET.equals(method)){
            return localLRUCacheService.getLongUrlByShortUrl(urls);
        }else {
            return localLRUCacheService.createShortUrlByLongUrl(urls);
        }
    }

    /**
     * 收到广播
     * @param method
     * @param urls
     * @return
     */
    @Operation(hidden = true)
    @PostMapping("/inner/broadcast/{method}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Res<List<Record>>> broadcasted(@PathVariable String method,@RequestBody Set<String> urls){
        log.info("收到广播-"+method+":" + urls);
        return distributedLruCacheService.broadcastedAndSetOrGet(urls,method)
                .switchIfEmpty(Mono.just(Res.error("当前不是leader，无法发起同步命令",null)));
    }
}
