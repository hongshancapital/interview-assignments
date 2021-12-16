package com.scdt.china.shorturl.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.scdt.china.shorturl.configuration.ApplicationProperties;
import com.scdt.china.shorturl.repository.id.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * URL仓库：内存实现
 *
 * @author ng-life
 */
@Repository
public class InMemoryUrlRepository implements UrlRepository {

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUrlRepository.class);

    private final IdGenerator idGenerator;

    private final Cache<Long, String> cache;

    public InMemoryUrlRepository(IdGenerator idGenerator,
                                 ApplicationProperties applicationProperties) {
        this.idGenerator = idGenerator;
        // 缓存限制最大数量，Value软引用，超出预期数量或者内存不足GC时，访问较少的缓存项优先失效
        this.cache = Caffeine.newBuilder()
                .maximumSize(applicationProperties.getMemoryStoreCount()).softValues()
                .removalListener((key, value, cause) -> {
                    if (cause == RemovalCause.SIZE || cause == RemovalCause.COLLECTED) {
                        LOG.debug("存储对象移除：{} , 原因： {}", key, cause);
                    }
                }).build();
    }

    @Override
    public Long save(String url) {
        Long newId = idGenerator.nextId();
        if (idGenerator.isRandom()) {
            int retryTimes = 0;
            while (cache.asMap().containsKey(newId)) {
                // 限制重试次数，避免死循环
                if (retryTimes++ > 5) {
                    throw new IllegalStateException("URL保存失败，随机ID冲突次数超过5次：" + url);
                }
                newId = idGenerator.nextId();
            }
        }
        cache.put(newId, url);
        return newId;
    }

    @Override
    public String get(Long id) {
        return cache.getIfPresent(id);
    }

}
