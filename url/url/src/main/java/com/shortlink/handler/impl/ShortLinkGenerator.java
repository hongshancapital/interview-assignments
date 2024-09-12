package com.shortlink.handler.impl;

import com.shortlink.common.GeneralException;
import com.shortlink.common.Status;
import com.shortlink.entity.CreateShortLinkRequest;
import com.shortlink.entity.FetchOriginalUrlRequest;
import com.shortlink.entity.ShortLinkEntity;
import com.shortlink.handler.NumberGeneratorService;
import com.shortlink.handler.ShortLinkHandler;
import com.shortlink.util.CaffeineUtil;
import com.shortlink.util.NumericConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;


@Slf4j
@Service
public class ShortLinkGenerator implements ShortLinkHandler {

    @Autowired
    private NumberGeneratorService numberGeneratorService;

    // 短链不可用时间 1天
    private static long SHORT_EXPIRE_TIME = 60 * 60 * 24;

    private int seed = 62;

    public ShortLinkEntity createShortLink(CreateShortLinkRequest request) {
        // 1.参数判断是否合法
        if (Objects.isNull(request) || StringUtils.isEmpty(request.getUrl())) {
            log.info("ShortLinkGenerator createShortLink param is invalid url:{} ", request.getUrl());
            throw new GeneralException(Status.INVALID_PARAM);
        }
        // 2.获取短链
        String shortLink = generatorShortLink();
        // 2.1布隆过滤器判断  如果需要 则在后面需要put进过滤器
        ShortLinkEntity shortLinkEntity = ShortLinkEntity.builder()
                .shortLink(shortLink)
                .appid(request.getAppId())
                .originalUrl(request.getUrl())
                .expireTime(System.currentTimeMillis() + SHORT_EXPIRE_TIME * 1000)
                .createTime(new Date())
                .build();
        // 3.添加缓存
        CaffeineUtil.put(shortLink, shortLinkEntity);
        // 异步持久化实体
        return shortLinkEntity;
    }

    public ShortLinkEntity fetchUrlByShortLink(FetchOriginalUrlRequest request) {

        if (Objects.isNull(request) || StringUtils.isEmpty(request.getShortLink())) {
            log.info("ShortLinkGenerator fetchUrlByShortLink param is invalid shortLink:{} ", request.getShortLink());
            throw new GeneralException(Status.INVALID_PARAM);
        }
        // 获取本地缓存 暂时直接强转
        ShortLinkEntity entity = (ShortLinkEntity) CaffeineUtil.get(request.getShortLink());
        // 如果有中间件缓存 以及 持久化层 可在此考虑布隆过滤器 解决缓存穿透等问题
        if (Objects.isNull(entity)) {
            log.info("ShortLinkGenerator fetchUrlByShortLink result is empty. shortLink:{} ", request.getShortLink());
            throw new GeneralException(Status.INVALID_RESULT);
        }
        return entity;
    }

    private  String generatorShortLink() {
        // 生成号码
        long number = numberGeneratorService.nextId();

        // 生成短链
        return NumericConvertUtil.toOtherNumberSystem(number, seed);
    }


}
