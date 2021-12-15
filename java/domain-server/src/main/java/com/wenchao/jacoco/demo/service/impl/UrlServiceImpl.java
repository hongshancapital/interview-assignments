package com.wenchao.jacoco.demo.service.impl;

import com.wenchao.jacoco.demo.service.UrlService;
import com.wenchao.jacoco.demo.storage.IStorage;
import com.wenchao.jacoco.demo.utils.CacheUtils;
import com.wenchao.jacoco.demo.utils.NumericConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 域名转换服务实现
 *
 * @author Wenchao Gong
 * @date 2021/12/15 16:12
 */
@Service
public class UrlServiceImpl implements UrlService {

    /**
     * 短链接数据进制
     */
    public static final int SEED = 62;
    @Resource
    private IStorage storage;

    @Override
    public String getShortUrl(String longUrl) {
        Long shortUrl = CacheUtils.getShortUrl(longUrl, () -> {
            Long temp = storage.getShortUrl(longUrl);
            if (Objects.nonNull(temp)) {
                CacheUtils.put(longUrl, temp);
            }
            return temp;
        });
        if (Objects.isNull(shortUrl)) {
            shortUrl = storage.nextVal();
            storage.put(longUrl, shortUrl);
            CacheUtils.put(longUrl, shortUrl);
        }
        return NumericConvertUtils.toOtherNumberSystem(shortUrl, SEED);
    }

    @Override
    public Optional<String> getLongUrl(String shortUrl) {
        Long number = NumericConvertUtils.toDecimalNumber(shortUrl, SEED);
        String longUrl = CacheUtils.getLongUrl(number, () -> {
            String temp = storage.getLongUrl(number);
            if (Objects.nonNull(temp)) {
                CacheUtils.put(temp, number);
            }
            return temp;
        });
        return Optional.ofNullable(longUrl);
    }
}
