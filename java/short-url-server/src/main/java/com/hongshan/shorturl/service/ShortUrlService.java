package com.hongshan.shorturl.service;

import com.hongshan.shorturl.dao.ShortUrlDao;
import com.hongshan.shorturl.domain.exception.NotFoundException;
import com.hongshan.shorturl.domain.dto.ShortUrlGenerateDTO;
import com.hongshan.shorturl.domain.model.ShortUrlModel;
import com.hongshan.shorturl.domain.entity.ShortUrlEntity;
import com.hongshan.shorturl.util.LongConvertUtil;
import com.hongshan.shorturl.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: huachengqiang
 * @date: 2022/3/19
 * @description:
 */
@Service
public class ShortUrlService {
    private static final Logger logger = LoggerFactory.getLogger(ShortUrlService.class);
    private static AtomicLong increase = new AtomicLong(9000_000_000_000L);
    @Autowired
    private ShortUrlDao shortUrlDao;

    /**
     * 生成短链接，接收一个原始链接和超时时间，返回短链接
     * 1、先计算原始链接的hash（base64字符串）
     * 2、在hash缓存中查是否有相同的原始链接存在，有则直接返回，没有则生成新的映射关系
     * 3、计算短链接key，生成短链接信息，并存储缓存中
     *
     * @param request
     * @return {@link ShortUrlModel}
     */
    public ShortUrlModel createShortUrl(ShortUrlGenerateDTO request) {
        String originUrl = request.getOriginUrl();
        String hash = MD5Util.md5ToBase64(originUrl);
        ShortUrlEntity existsOne = shortUrlDao.findByHash(hash, request.getOriginUrl());
        if (Objects.nonNull(existsOne)) {
            return new ShortUrlModel(existsOne);
        }
        ShortUrlEntity shortUrlEntity = new ShortUrlEntity();
        shortUrlEntity.setOriginUrl(request.getOriginUrl());
        shortUrlEntity.setShortKey(LongConvertUtil.convertToStr(increase.incrementAndGet()));
        shortUrlEntity.setExpireAt(request.getExpireAt());
        shortUrlEntity.setHash(hash);
        shortUrlDao.add(shortUrlEntity);
        return new ShortUrlModel(shortUrlEntity);
    }


    /**
     * 根据短链接的后缀获取原始链接
     *
     * @param suffix 短链接后缀名
     * @return
     * @throws NotFoundException
     */
    public ShortUrlModel getShorUrl(String suffix) {
        ShortUrlEntity shortUrlEntity = shortUrlDao.findByKey(suffix);
        if (Objects.isNull(shortUrlEntity)) {
            throw new NotFoundException("原始链接不存在或已过期");
        }
        return new ShortUrlModel(shortUrlEntity);
    }
}
