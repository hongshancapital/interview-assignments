package cn.sequoiacap.links.base.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author : Liushide
 * @date :2022/4/5 17:01
 * @description : 缓存配置，配置Caffeine
 */
@Configuration
public class CacheConfig {

    /**
     * 配置缓存管理器
     * @return
     */
    @Bean
    public Cache<String, Object> caffeineCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期, 30 天
                .expireAfterWrite(30, TimeUnit.DAYS)
                // 初始的缓存空间大小，初始300
                .initialCapacity(300)
                // 缓存的最大条数，在缓存元素个数快要达到最大限制的时候，过期策略就开始执行了
                .maximumSize(600000)
                .build();
    }

}
