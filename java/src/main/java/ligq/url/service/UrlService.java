package ligq.url.service;

import cn.hutool.core.lang.MurmurHash;
import cn.hutool.crypto.digest.DigestUtil;
import com.google.common.cache.*;
import ligq.url.Constants;
import ligq.url.dao.UrlDao;
import ligq.url.util.UrlDigest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UrlService {

    @Value("${app.lru.capacity}")
    private int cacheCapacity;
    @Value("${app.concurrency.level}")
    private int concurrencyLevel;
    @Resource
    private UrlDao dao;
    private LoadingCache<Integer, String> cache;
    private Object[] writeLocks;

    @PostConstruct
    public void init() throws Exception {
        //初始化guava缓存
        cache = CacheBuilder.newBuilder()
                //设置cache容量的初始值和最大值，避免resize
                .initialCapacity(cacheCapacity)
                .maximumSize(cacheCapacity)
                //设置并发级别，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(concurrencyLevel)
                //是否需要统计缓存情况,该操作消耗一定的性能
                .recordStats()
                //设置读写缓存后n小时过期
                .expireAfterAccess(12, TimeUnit.HOURS)
                //设置缓存的移除通知，异步方式
                .removalListener(RemovalListeners.asynchronous(notify -> {
                    log.info("Key:[], Value:[] Has Removed! Cause: {}", notify.getKey(), notify.getValue(), notify.getCause());
                }, Executors.newSingleThreadExecutor()))
                //指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存，该方法会缓存null值，防止缓存击穿
                .build(new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer key) throws Exception {
                        String value = dao.getValue(key);
                        log.info("load value by key:{}, value:{}", key, value);
                        return value == null ? "" : value;
                    }
                });
        //初始化rocksDB写分段锁
        writeLocks = new Object[concurrencyLevel];
        Arrays.setAll(writeLocks, i -> new Object());
        //默认加载近3天的数据
        long recent3Days = LocalDateTime.now().toEpochSecond(Constants.zone) - 60 * 60 * 24 * 3;
        //初始化缓存，防止缓存穿透
        dao.initRecentValues(recent3Days, p -> cache.put(p.getKey(), p.getValue()));
    }

    public String getLongUrl(String shortUrl) throws Exception {
        int id = UrlDigest.decode(shortUrl);
        return cache.get(id);
    }

    public String getShortUrl(String longUrl) throws Exception {
        //根据url的hash值获取并发锁
        int hashCode = this.urlHashCode(longUrl);
        Object lock = writeLocks[(writeLocks.length - 1) & hashCode];
        //基于longUrl的md5使用murmurhash计算1个随机id，如果出现冲突使用当前md5值做为salt重新计算id值，不断重试直到不冲突为止
        String salt = "";
        int id = 0;
        boolean condition = true;
        while (condition) {
            String md5 = DigestUtil.md5Hex(longUrl + salt);
            id = MurmurHash.hash32(md5);
            String cacheUrl = cache.getIfPresent(id);
            if (longUrl.equals(cacheUrl)) {
                throw new RuntimeException("This Url is Repeat!");
            } else {
                synchronized (lock) {
                    String value = dao.getValue(id);
                    if (value == null) {
                        dao.setValue(id, longUrl);
                        condition = false;
                    } else if (longUrl.equals(value)) {
                        throw new RuntimeException("This Url is Repeat!");
                    }
                }
                if (condition) {
                    salt = md5;
                } else {
                    cache.put(id, longUrl);
                }
            }
        }

        return UrlDigest.encode(id);
    }

    /** 获取cache统计信息 */
    public CacheStats getCacheStats() {
        return cache.stats();
    }

    private int urlHashCode(String url) {
        int h;
        return (h = url.hashCode()) ^ (h >>> 16);
    }
}
