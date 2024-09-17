package com.sequoiacap.shortlinkcenter.infrastructure;

import com.sequoiacap.shortlinkcenter.domain.exception.DomainErrorCodeEnum;
import com.sequoiacap.shortlinkcenter.domain.exception.DomainException;
import com.sequoiacap.shortlinkcenter.domain.shorturl.entity.UrlEntity;
import com.sequoiacap.shortlinkcenter.domain.shorturl.repository.UrlEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author h.cn
 * @date 2022/3/17
 */
@Component
@Slf4j
public class UrlEntityRepositoryImpl implements UrlEntityRepository {

    /**
     * shortUrl -> sourceUrl
     */
    private ConcurrentHashMap<String, UrlEntity> shortUrlDataMap = new ConcurrentHashMap<>(10 * 1024);

    /**
     * sourceUrl -> shortUrl
     */
    private ConcurrentHashMap<String, UrlEntity> sourceUrlDataMap = new ConcurrentHashMap<>(10 * 1024);

    private ReentrantLock urlLock = new ReentrantLock();

    @Override
    public void saveShortUrl(UrlEntity urlEntity) {
        try {
            urlLock.lock();
            shortUrlDataMap.put(urlEntity.getTargetUrl(), urlEntity);
            sourceUrlDataMap.put(urlEntity.getSourceUrl(), urlEntity);
        } catch (Exception e) {
            shortUrlDataMap.remove(urlEntity.getTargetUrl());
            sourceUrlDataMap.remove(urlEntity.getSourceUrl());
            log.error("UrlEntityRepositoryImpl saveShortUrl: [{}]  error: ", urlEntity.getSourceUrl(), e);
            throw new DomainException(DomainErrorCodeEnum.SAVE_FAILED.getCode(), "url保存失败，请稍后再试");
        } finally {
            urlLock.unlock();
        }
    }

    @Override
    public String getShortUrlBySourceUrl(String sourceUrl) {
        UrlEntity urlEntity = sourceUrlDataMap.get(sourceUrl);
        if (Objects.isNull(urlEntity)) {
            return null;
        }
        return urlEntity.getTargetUrl();
    }

    @Override
    public String getSourceUrlByShortUrl(String shortUrl) {
        UrlEntity urlEntity = shortUrlDataMap.get(shortUrl);
        if (Objects.isNull(urlEntity)) {
            return null;
        }
        return urlEntity.getSourceUrl();
    }
}
