package shorturl.server.server.application.cache;

import org.springframework.context.annotation.Bean;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.TimeUnit;


@Configuration
public class CacheConfig {
    @Bean
    public Cache<String, String> caffeineCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(60, TimeUnit.MINUTES)
                // 初始的缓存空间大小
                .initialCapacity(1024*1024)
                // 缓存的最大条数
                .maximumSize(1024*1024)
                .build();
    }
}
