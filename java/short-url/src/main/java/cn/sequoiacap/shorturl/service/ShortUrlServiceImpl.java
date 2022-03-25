package cn.sequoiacap.shorturl.service;

import cn.sequoiacap.shorturl.exception.StoreException;
import cn.sequoiacap.shorturl.store.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShortUrlServiceImpl.class);

    private static final int MAX_RETRY = 10;

    @Value("${short.url.prefix}")
    private String prefix;

    private final ShortUrlGenerator generator;
    private final Store store;

    public ShortUrlServiceImpl(ShortUrlGenerator generator, Store store) {
        this.generator = generator;
        this.store = store;
    }

    @Override
    public String generate(String originalUrl) throws StoreException {
        String shortUrlId = generator.generate(originalUrl, 0);
        // 先无锁尝试获取一下生成的短链接 id 是否已经存在, 如果已经存在且原链接相同可直接返回
        String storedOriginalUrl = store.get(shortUrlId);
        if (originalUrl.equals(storedOriginalUrl)) {
            return getShortUrl(shortUrlId);
        }

        // 短链接为 null , 可以去尝试写存储, 如果写成功, 可以直接返回
        // 写失败说明在上面检查之后有其他线程写入导致冲突了
        // 短链接不为 null 已经产生了冲突, 不用尝试写, 直接去重试生成
        if (storedOriginalUrl == null) {
            if (store.write(shortUrlId, originalUrl)) {
                return getShortUrl(shortUrlId);
            }
        }

        logCollision(originalUrl, shortUrlId, 1);
        // 生成的短链接 id 发生了碰撞, 加入随机数去生成
        for (int count = 1; count <= MAX_RETRY; ++count) {
            shortUrlId = generator.generate(originalUrl, count);
            if (store.write(shortUrlId, originalUrl)) {
                return getShortUrl(shortUrlId);
            }
            logCollision(originalUrl, shortUrlId, count + 1);
        }

        LOGGER.warn("generate short url failed after retry {} times, originalUrl: {}", MAX_RETRY, originalUrl);
        return null;
    }

    @Override
    public String get(String shortUrlId) throws StoreException {
        return store.get(shortUrlId);
    }

    private String getShortUrl(String shortUrlId) {
        return prefix + shortUrlId;
    }

    private void logCollision(String originalUrl, String shortUrlId, int count) {
        LOGGER.info("originalUrl: {}, shortUrlId: {}, collide {} times", originalUrl, shortUrlId, count);
    }
}
