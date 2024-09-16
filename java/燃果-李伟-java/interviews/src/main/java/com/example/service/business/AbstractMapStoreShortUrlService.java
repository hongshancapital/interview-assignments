package com.example.service.business;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.example.util.ConcurrentMapCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public abstract class AbstractMapStoreShortUrlService implements ShortUrlService {

    // 两个concurrentmap用来保存短地址-长地址的映射关系，
    // mapping
    private ConcurrentMapCache longShortUrlMap = new ConcurrentMapCache(5);

    private ConcurrentMapCache shortLongUrlMap = new ConcurrentMapCache(5);

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static final int EXPIRE = 60 * 1000;

    @Override
    public String lookupLong(String shortUrl) {
        String longUrl = null;
        Lock readLock = readWriteLock.readLock();
        try {
            readLock.lock();
            longUrl = (String) shortLongUrlMap.get(shortUrl);
        } catch (Exception e) {
        } finally {
            readLock.unlock();
        }
        return longUrl;
    }

    @Override
    public String convertShort(String longUrl) {
        String shortUrl = lookupShort(longUrl);
        if (StringUtils.isEmpty(shortUrl)) {
            shortUrl = shorten(longUrl);
            if (StringUtils.isEmpty(shortUrl)) {
                log.info("Cannot convert long url to short url");
            } else {
                synchronizedResult(longUrl, shortUrl);
            }
        }
        return new StringBuilder(DOMAIN_PREFIX).append(shortUrl).toString();
    }

    protected String lookupShort(String longUrl) {
        String shortUrl = null;
        Lock readLock = readWriteLock.readLock();
        try {
            readLock.lock();
            shortUrl = (String) longShortUrlMap.get(longUrl);
        } catch (Exception e) {
        } finally {
            readLock.unlock();
        }
        return shortUrl;
    }

    protected void synchronizedResult(String longUrl, String shortUrl) {
        Lock writeLock = readWriteLock.writeLock();
        try {
            writeLock.lock();
            longShortUrlMap.set(longUrl, shortUrl, EXPIRE);
            shortLongUrlMap.set(shortUrl, longUrl, EXPIRE);
        } catch (Exception e) {
        } finally {
            writeLock.unlock();
        }
    }

    public abstract String shorten(String longUrl);

}
