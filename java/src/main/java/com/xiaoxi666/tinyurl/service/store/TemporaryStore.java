package com.xiaoxi666.tinyurl.service.store;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/17
 * @Version: 1.0
 * @Description: 缓存存储（临时）
 */
@Component
public class TemporaryStore extends AbstractStore implements InitializingBean {

    // 1小时后过期
    private static final int MAX_HOUR = 1;

    @Resource
    private HotCache hotCache;

    @Override
    public void afterPropertiesSet() {
        cache = Caffeine.newBuilder()
                .maximumSize(MAX_SIZE) // 限制一下内存
                .expireAfterWrite(MAX_HOUR, TimeUnit.HOURS)
                .removalListener((key, value, cause) -> {
                    hotCache.clear((String) value);
                })
                .build(s -> "");
    }
}
