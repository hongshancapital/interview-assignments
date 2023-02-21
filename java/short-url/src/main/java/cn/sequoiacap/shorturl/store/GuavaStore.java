package cn.sequoiacap.shorturl.store;

import cn.sequoiacap.shorturl.exception.StoreException;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.Weigher;
import com.google.common.util.concurrent.Striped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 使用 GuavaCache 实现的存储
 */
@Repository
public class GuavaStore implements Store {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuavaStore.class);

    private static final int TRY_LOCK_TIMEOUT = 100;

    // 针对短链接 id 的锁, 使用 striped 的锁, 相同的短链接 id 一定是同一把锁, 不同的短链接 id 有概率是同一把锁
    // 锁的个数越多, 不同的短链接 id 获取到相同的锁的概率就越小, 根据应用请求线程数来设置
    // 应用线程数一般在 300-400, 所以这里设置为 512
    private static final Striped<Lock> LOCKS = Striped.lock(512);

    private static final int PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final Cache<String, String> store = CacheBuilder.newBuilder()
            // 能同时写存储的线程数量, 由于应用是 IO 密集型, 应该设置为 2*CPU 核心数
            .concurrencyLevel(PROCESSORS * 2)
            // 设置最大能使用的存储空间字节数, 整个堆 6G, 老年代为 4G, 这里设置为老年代的 70%
            // 剩下的空间给 guava 中的数组, entry 等使用
            .maximumWeight(2_800_000_000L)
            // 每对长短链接的映射所占用的内存的计算, 按照每一个字符占用一个 char 类型的长度来计算
            .weigher((Weigher<String, String>) (key, value) -> (key.length() + value.length()) * 2)
            // 记录存储状态, 可以观察存储使用情况
            .recordStats()
            .build();

    @Override
    public boolean write(String shortUrlId, String originalUrl) throws StoreException {
        Lock lock = getLock(shortUrlId);
        if (!tryLock(lock)) {
            // 规定时间内加锁失败
            throw new StoreException("lock " + shortUrlId + "'s store failed");
        }

        // 加锁成功后, 可以放心判断是否有冲突
        try {
            String storedOriginalUrl = get(shortUrlId);
            if (storedOriginalUrl == null) {
                store.put(shortUrlId, originalUrl);
            } else {
                return storedOriginalUrl.equals(originalUrl);
            }
        } finally {
            lock.unlock();
        }

        // 需要关注 evictionCount, 看是否存在 key 剔除情况
        LOGGER.info("store status: {}", store.stats());
        return true;
    }

    @Override
    public String get(String shortUrlId) {
        return store.getIfPresent(shortUrlId);
    }

    @VisibleForTesting
    Lock getLock(String shortUrlId) {
        return LOCKS.get(shortUrlId);
    }

    private boolean tryLock(Lock lock) throws StoreException {
        try {
            return lock.tryLock(TRY_LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new StoreException("interrupted when try to lock store", e);
        }
    }
}
