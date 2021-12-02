package org.hkm.service;

import org.hkm.AppConfigProperties;
import org.hkm.model.ShortUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import static org.hkm.enums.RedisKey.Short_Url_Prefix;

@Service
public class ShortApiService {

    private AppConfigProperties appProp;

    private Logger logger = LoggerFactory.getLogger(ShortApiService.class);

    public ShortApiService(AppConfigProperties appProp) {
        this.appProp = appProp;
    }


    /**
     * 保存有效链接的数量
     */
    private static final int QUEUE_LEN = 1000000;

    /**
     * 用于处理过期以及限制有效链接数量
     *
     * 队列的数据按时间先后顺序插入，队列头即为最早过期的，10s循环检查一次队列头，过期消费掉
     */
    private static final ArrayBlockingQueue<ShortUrl> queue = new ArrayBlockingQueue<>(QUEUE_LEN);

    /**
     * 短链接与原始链接的映射表
     */
    private static final Map<String, ShortUrl> cache = new ConcurrentHashMap<>();

    /**
     * 原始链接与短链接的映射表
     * 用于防止同一url多次转换
     */
    private static final Map<String, String> duplicateCheck = new ConcurrentHashMap<>();

    public String transfer(String url){

        // 生成一个指定长度的字符串，作为短链接
        String key = generate(appProp.getLength());

        // 将短链接作为key，原始链接作为value，存入cache
        ShortUrl shortUrl = new ShortUrl(key, url, LocalDateTime.now().plusSeconds(appProp.getExpireSec()).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        // 如果已经存在，则返回之前的生成的key
        if (duplicateCheck.containsKey(url)) {
            return MessageFormat.format(appProp.getDomain(), duplicateCheck.get(url));
        }

        // 写操作成功后才返回短链接
        boolean success = add(shortUrl);
        return success?MessageFormat.format(appProp.getDomain(), key):null;
    }

    private final ShortUrl empty = new ShortUrl();

    /**
     * 短链接转换为原始链接
     * @param key
     * @return
     */
    public String redirect(String key){
        return Optional
                .ofNullable(cache.get(Short_Url_Prefix.key(key)))
                .orElse(empty)
                .getOriginUrl();
    }

    /**
     * 过期删除操作
     * 间隔10s检查一次队列最前面的是否过期
     */
    @Scheduled(fixedDelay = 10000)
    public void expire(){

        while (!queue.isEmpty()) {
            Long now = System.currentTimeMillis();
            if (queue.peek().getExpireTimeStamp() < now) {// 说明到过期时间
                try {
                    // 队列中消费掉
                    ShortUrl data = queue.take();

                    // 缓存映射表中删除
                    cache.remove(Short_Url_Prefix.key(data.getKey()));

                    // 防重映射表中删除
                    duplicateCheck.remove(data.getOriginUrl());
                    logger.info("remove {} from queue ", data.getKey());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
        }
    }

    /**
     * 写操作
     * @param shortUrl
     * @return
     */
    private boolean add(ShortUrl shortUrl) {
        boolean success = queue.offer(shortUrl);
        if (success) {
            cache.put(Short_Url_Prefix.key(shortUrl.getKey()), shortUrl);
            duplicateCheck.put(shortUrl.getOriginUrl(), shortUrl.getKey());
            logger.info("[{}] transferd to {}", shortUrl.getOriginUrl(), shortUrl.getKey());
        } else {
            logger.info("[{}] failed", shortUrl.getOriginUrl());
        }
        return success;
    }

    private static Random random = new Random();
    private static char[] baseChars = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};

    private String generate(int length) {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(baseChars[random.nextInt(baseChars.length)]);
        }
        return sb.toString();

    }

}
