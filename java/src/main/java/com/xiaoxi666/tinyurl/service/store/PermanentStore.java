package com.xiaoxi666.tinyurl.service.store;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/17
 * @Version: 1.0
 * @Description: 持久存储（注意，我们暂时仍然使用本地缓存实现，仅用最大数量进行内存限制。实际情况中都会落库）
 */
@Component
public class PermanentStore extends AbstractStore implements InitializingBean {

    @Resource
    private HotCache hotCache;

    @Override
    public void afterPropertiesSet() {
        cache = Caffeine.newBuilder()
                .maximumSize(MAX_SIZE)
                .removalListener((key, value, cause) -> {
                    hotCache.clear((String) value);
                })
                .build(s -> "");
    }
}
