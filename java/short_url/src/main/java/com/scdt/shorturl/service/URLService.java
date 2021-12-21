package com.scdt.shorturl.service;

import com.scdt.shorturl.lrucache.LRUCache;
import com.scdt.shorturl.model.Record;
import com.scdt.shorturl.model.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 短域名服务实现
 */
@Service
public class URLService {
    private static final Logger log = LoggerFactory.getLogger(URLService.class);

    private final Hashids hashids;
    //实际情况，短域名不可能和长域名相等，所以每次都是存储两条记录，两个合并成一个操作
    private final LRUCache<String,String> lruCache;
    private final AtomicLong idGenerator = new AtomicLong(0);
    public URLService(Hashids hashids,
                      LRUCache<String, String> lruCache) {
        this.hashids = hashids;
        this.lruCache = lruCache;
    }

    /**
     * 根据长域名生成短域名
     * @param longUrls
     * @return
     */
    public Mono<Res<List<Record>>> createShortUrlByLongUrl(Set<String> longUrls) {
        List<Record> records = longUrls.stream().map(longUrlParam -> {
            String encodeLongUrlParam = URLEncoder.encode(longUrlParam.trim(), StandardCharsets.UTF_8);
            //先从longUrlLRUCache里找，如果存在，直接返回短域名
            Optional<Record> optionalRecord = lruCache.get(encodeLongUrlParam)
                    // 保持一致 所以这里要从短域名Cache中获取，将记录前置到最近已使用
                    .flatMap(shortUrl -> lruCache.get(shortUrl).map(encodeLongUrl -> new Record(shortUrl, URLDecoder.decode(encodeLongUrl, StandardCharsets.UTF_8))));
            if (optionalRecord.isPresent()) return optionalRecord.get();

            // 如果不存在，则新增一条
            long id = idGenerator.getAndIncrement();
            String shortUrl = hashids.encode(id);
            //先放短域名-长域名映射 再放长域名-短域名映射 两次操作同时成功才算成功
            boolean success = lruCache.put(shortUrl,encodeLongUrlParam) && lruCache.put(encodeLongUrlParam,shortUrl);
            if (success) return new Record(shortUrl,longUrlParam);
            else {
                //回滚id，下次直接覆盖旧值
                idGenerator.decrementAndGet();
                return null;
            }
        }).collect(Collectors.toList());
        return Mono.just(Res.success(records));
    }

    /**
     * 根据短域名获取长域名，LRUCache容量满了后，长期不用的短域名存在过期的情况
     * @param shortUrls
     * @return
     */
    public Mono<Res<List<Record>>> getLongUrlByShortUrl(Set<String> shortUrls) {
        // 有效短域名
        List<Record> validRecords = new ArrayList<>();
        // 过期短域名
        List<Record> expiredRecords = new ArrayList<>();
        for (String shortUrl : shortUrls){
            Optional<String> longUrlOptional = lruCache.get(shortUrl);
            if(longUrlOptional.isPresent()){
                String encodeLongUrl = longUrlOptional.get();
                // 保持一致 所以这里要从长域名Cache中获取，将记录前置到最近已使用
                lruCache.get(encodeLongUrl).ifPresent(sUrl -> {
                    validRecords.add(new Record(sUrl,URLDecoder.decode(encodeLongUrl,StandardCharsets.UTF_8)));
                });
            }else {
                expiredRecords.add(new Record(shortUrl,null));
            }
        }
        if (expiredRecords.isEmpty()){
            return Mono.just(Res.success(validRecords));
        }else {
            return Mono.just(Res.error("找不到短域名或已过期",expiredRecords));
        }

    }


}
