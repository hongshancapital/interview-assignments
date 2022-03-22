package demo.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class SpringCacheConfig {
    // 多服务节点布置后，以 redis 为二级缓存
    // 可以以此配置 cacheManager，注释至查询方法作为一级缓存
    // 应用到短域名服务，需注意域名生成算法的时间有效性，至少在缓存的超时周期中不能生成相同的短域名
//    @Bean
//    public CacheManager cacheManager() {
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
//
//        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
//                .expireAfterWrite(24, TimeUnit.HOURS)
//                .initialCapacity(1000)
//                .maximumSize(10000);
//        cacheManager.setCaffeine(caffeine);
//
//        return cacheManager;
//    }

    /**
     * 缓存器配置
     * 超时时长等具体数据为暂定，需根据实际使用情况进行调整
     * 可以调整为通过配置文件注入属性
     * @return 缓存器
     */
    @Bean
    public Cache<String, Object> caffeineCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(24, TimeUnit.HOURS)
                // 初始的缓存空间大小
                .initialCapacity(1000)
                // 缓存的最大条数
                .maximumSize(4000000)
                .build();
    }
}
