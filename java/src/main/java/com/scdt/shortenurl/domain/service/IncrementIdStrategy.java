package com.scdt.shortenurl.domain.service;

import com.scdt.shortenurl.common.utils.EncodeUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description 自增id算法策略实现
 * @Author chenlipeng
 * @Date 2022/3/7 2:15 下午
 */
@Service("incrementIdStrategy")
public class IncrementIdStrategy implements ShortenUrlStrategy {

    /**
     * 发号策略采用的id，使用AtomicLong保证唯一性
     */
    private AtomicLong shortUrlId = new AtomicLong(0);

    @Override
    public String genShortUrl(String url) {
        long id = shortUrlId.getAndIncrement();
        return EncodeUtils.encodeBy62Decimal(id);
    }


}
