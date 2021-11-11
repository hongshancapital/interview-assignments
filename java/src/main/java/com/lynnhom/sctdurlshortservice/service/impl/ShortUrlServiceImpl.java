package com.lynnhom.sctdurlshortservice.service.impl;

import com.lynnhom.sctdurlshortservice.common.enums.RespCodeEnum;
import com.lynnhom.sctdurlshortservice.common.exception.BizException;
import com.lynnhom.sctdurlshortservice.model.dto.UrlDto;
import com.lynnhom.sctdurlshortservice.model.dto.UrlResponseDto;
import com.lynnhom.sctdurlshortservice.model.po.ShortKey;
import com.lynnhom.sctdurlshortservice.service.ShortUrlService;
import com.lynnhom.sctdurlshortservice.common.utils.DateUtil;
import com.lynnhom.sctdurlshortservice.common.utils.StringUtil;
import com.lynnhom.sctdurlshortservice.service.UrlMappingCacheService;
import com.lynnhom.sctdurlshortservice.utils.TokenUtil;
import com.lynnhom.sctdurlshortservice.utils.UrlValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @description: 短链接服务实现类
 * @author: Lynnhom
 * @create: 2021-10-28 10:29
 **/
@Slf4j
@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    private static final String DOMAIN = "https://www.lynnhom.net/";
    private static final int DEFAULT_EXPIRE_DURATION = 5;

    @Autowired
    private UrlMappingCacheService urlMappingCacheService;

    @Override
    public UrlResponseDto create(UrlDto url) {
        String appId = url.getAppId();
        String token = url.getToken();
        String originalUrl = url.getUrl();
        String expireTimeStr = url.getExpireDate();
        String shortUrl;
        // 校验token的正确性
        if (!TokenUtil.isValid(appId, originalUrl, token)) {
            log.warn("create, token is invalid, appId={}, originalUrl={}, token={}", appId, originalUrl, token);
            throw new BizException(RespCodeEnum.TOKEN_INVALID);
        }

        // 校验URL长链接地址的合法性
        if (!UrlValidatorUtil.isValid(originalUrl)) {
            log.warn("create, url is invalid, url={}", originalUrl);
            throw new BizException(RespCodeEnum.URL_INVALID);
        }

        // 默认有效期限为5年,且最长有效期为5年
        Date expireTime = DateUtil.add(DateUtil.now(), DEFAULT_EXPIRE_DURATION, Calendar.YEAR);
        Date expireTimeParam;
        try {
            expireTimeParam = DateUtil.format(expireTimeStr);
        } catch (Exception e) {
            expireTimeParam = expireTime;
            log.warn("create, expire time not valid, expireTime={}", expireTimeStr);
        }
        if (expireTimeParam.before(DateUtil.now())) {
            // 请求参数的有效期早于当前日期
            log.warn("create, expire time earlier than now, expireTime={}", expireTimeStr);
            throw new BizException(RespCodeEnum.EXPIRE_TIME_EARLIER_THAN_NOW);
        }
        expireTime = expireTime.after(expireTimeParam) ? expireTimeParam : expireTime;
        LocalDateTime expireTimeLacal = expireTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        // 查询长链接是否已经存在
        ShortKey shortKey = urlMappingCacheService.getShortKey(originalUrl);
        if (Objects.nonNull(shortKey)) {
            shortUrl = shortKey.getShortKey();
            if (StringUtil.equals(url.getAppId(), shortKey.getAppId())) {
                // 同一个app再次创建相同的长链接，更新对应关系的有效期
                urlMappingCacheService.updateExpireTime(originalUrl, expireTimeLacal);
            } else {
                // 与现有app不同，则不更新对应关系的有效期，以现有的有效期为准
                expireTimeLacal = shortKey.getExpireTime();
            }
        } else {
            // 长链接不存在，生成一个长短链接的映射关系
            shortUrl = urlMappingCacheService.insertMapping(url.getAppId(), originalUrl, expireTimeLacal);
        }

        return UrlResponseDto.builder()
                .originalUrl(originalUrl)
                .shortUrl(DOMAIN.concat(shortUrl))
                .expireDate(expireTimeLacal)
                .build();
    }

    @Override
    public String getOriginalUrl(String shortUrl) {
        // 校验URL短链接地址的合法性
        if (StringUtil.isBlank(shortUrl)
                || !UrlValidatorUtil.isValid(shortUrl)
                || !StringUtil.startsWith(shortUrl, DOMAIN)) {
            log.warn("getOriginalUrl, short url is invalid, shortUrl={}", shortUrl);
            throw new BizException(RespCodeEnum.SHORT_URL_INVALID);
        }
        String shortKey = shortUrl.substring(DOMAIN.length());
        return urlMappingCacheService.getOriginalUrl(shortKey);
    }

}
