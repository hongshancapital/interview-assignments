package com.xing.shortlink.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xing.shortlink.domain.exception.ExtensionException;
import com.xing.shortlink.domain.http.request.CreateShortUrlRequest;
import com.xing.shortlink.domain.http.response.CreateShortUrlResponse;
import com.xing.shortlink.domain.http.request.QueryOriginalUrlRequest;
import com.xing.shortlink.domain.http.response.QueryOriginalUrlResponse;
import com.xing.shortlink.domain.http.Result;
import com.xing.shortlink.domain.utils.ShortUrlGenerator;
import com.xing.shortlink.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

import static com.xing.shortlink.domain.entity.ErrorCodeEnum.INVALID_SHORT_URL;
import static com.xing.shortlink.domain.entity.ErrorCodeEnum.SHORT_URL_CREATE_ERROR;
import static com.xing.shortlink.domain.entity.ErrorCodeEnum.SHORT_URL_OVER_LIMIT;

/**
 * 短链接核心业务服务
 *
 * @Author xingzhe
 * @Date 2021/7/17 23:00
 */
@Service
@Slf4j
public class UrlServiceImpl implements UrlService {

    /**
     * 过期时间，单位小时
     */
    private final static Integer EXPIRE_TIME = 1;

    /**
     * 缓存中保存的最大容量
     */
    private final static Integer MAX_SIZE = 1000000;

    /**
     * 缓存初始化容量，防止频繁扩容
     */
    private static final Integer INIT_SIZE = 10000;

    /**
     * 容量报警百分比，默认80%
     */
    @Value("${config.warn_rate}")
    private Double warnRate;

    /**
     * 短链域名
     */
    @Value("${config.domain}")
    private String urlDomain;

    /**
     * md5混淆因子
     */
    private String[] factors = {"xing", "zhe", "xing1", "zhe1"};

    /**
     * 静态缓存
     */
    private static Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(MAX_SIZE)
            .expireAfterWrite(EXPIRE_TIME, TimeUnit.HOURS)
            .initialCapacity(INIT_SIZE)
            .build();

    /**
     * 生成短链核心服务
     *
     * @param request 生成短链请求
     * @return 生成短链应答
     */
    @Override
    public Result<CreateShortUrlResponse> createShortUrl(CreateShortUrlRequest request) {
        String originalUrl = request.getOriginalUrl().trim();

        String shortUrl = createAndSaveShortUrl(originalUrl);

        CreateShortUrlResponse response = new CreateShortUrlResponse();
        response.setShortUrl(shortUrl);
        return Result.success(response);
    }

    /**
     * 查询原始链接核心服务
     *
     * @param request 查询原始链接请求
     * @return 查询原始链接应答
     */
    @Override
    public Result<QueryOriginalUrlResponse> queryOriginalUrl(QueryOriginalUrlRequest request) {
        String shortUrl = request.getShortUrl().trim();
        String key = shortUrl.substring(shortUrl.lastIndexOf("/") + 1);
        String originalUrl = cache.getIfPresent(key);

        if (!StringUtils.hasText(originalUrl)) {
            return Result.fail(INVALID_SHORT_URL.getCode(), INVALID_SHORT_URL.getMsg());
        }

        QueryOriginalUrlResponse response = new QueryOriginalUrlResponse();
        response.setOriginalUrl(originalUrl);
        return Result.success(response);
    }

    /**
     * 短链生成核心业务逻辑
     *
     * @param originUrl 原始链接
     * @return 短链接
     */
    private String createAndSaveShortUrl(String originUrl) {
        //1.容量判断
        if (cache.size() >= MAX_SIZE) {
            log.error("短链接缓存超限，当前最大限额配置:{}", MAX_SIZE);
            throw new ExtensionException(SHORT_URL_OVER_LIMIT.getCode(), SHORT_URL_OVER_LIMIT.getMsg());
        }
        //2.高并发情况下日志可能打印过多，考虑降级
        if (cache.size() >= MAX_SIZE * warnRate) {
            log.warn("短链接缓存容量报警，当前缓存数量:{}，最大缓存数量:{}", cache.size(), MAX_SIZE);
        }
        String shortUrl = "";
        for (String factor : factors) {
            //2.生成计算后的短链数组
            String[] keys = ShortUrlGenerator.shortUrl(originUrl, factor);
            //3.选取短链，缓存判断
            shortUrl = setNxShortUrl(keys, originUrl);
        }
        //生成的短链全部被占用，记录异常，人工介入
        if (!StringUtils.hasText(shortUrl)) {
            log.error("短链接生成失败，短链接全部已使用！originUrl={}", originUrl);
            throw new ExtensionException(SHORT_URL_CREATE_ERROR.getCode(), SHORT_URL_CREATE_ERROR.getMsg());
        }
        return shortUrl;
    }

    /**
     * 多线程访问共用缓存，需要加锁，使用string的intern生成对象，作为锁
     * 先判断缓存中是否存在，如果不存在则写入缓存，
     * 如果存在判断是否重复，重复的请求返回对应短链，如果不存在则尝试下一短链
     *
     * @param keys      生成的短链数组
     * @param originUrl 原始链接
     * @return 生成的短链
     */
    private String setNxShortUrl(String[] keys, String originUrl) {
        String shortUrl = null;
        for (String key : keys) {
            key = key.intern();
            synchronized (key) {
                //1.缓存中先查询是否已存在短链key
                String cacheOriginUrl = cache.getIfPresent(key);
                //2.如果存在有两种情况，重复请求或碰撞
                if (StringUtils.hasText(cacheOriginUrl)) {
                    if (cacheOriginUrl.equals(originUrl)) {
                        //重复地址跳出循环返回原有短链接，如果是碰撞则继续执行
                        shortUrl = urlDomain + key;
                        break;
                    }
                } else {
                    shortUrl = urlDomain + key;
                    cache.put(key, originUrl);
                    break;
                }
            }
        }
        return shortUrl;
    }
}
