package com.sequoiacap.domain.service;


import com.sequoiacap.domain.cache.UrlMapCacheManager;
import com.sequoiacap.domain.common.CommonConstant;
import com.sequoiacap.domain.common.SequenceGenerator;
import com.sequoiacap.domain.common.TransformStatus;
import com.sequoiacap.domain.common.lock.CompressionCodeStatus;
import com.sequoiacap.domain.common.lock.DistributedLock;
import com.sequoiacap.domain.common.lock.DistributedLockFactory;
import com.sequoiacap.domain.common.lock.LockKey;
import com.sequoiacap.domain.dao.CompressionCodeDao;
import com.sequoiacap.domain.dao.DomainConfDao;
import com.sequoiacap.domain.dao.UrlMapDao;
import com.sequoiacap.domain.entity.CompressionCode;
import com.sequoiacap.domain.entity.DomainConf;
import com.sequoiacap.domain.entity.UrlMap;
import com.sequoiacap.domain.util.ConversionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * url转换
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UrlMapService implements BeanFactoryAware {

    @Autowired
    private final UrlMapCacheManager urlMapCacheManager;
    @Autowired
    private final DistributedLockFactory distributedLockFactory;
    @Autowired
    private final SequenceGenerator sequenceGenerator;
    @Autowired
    private final DomainConfDao domainConfDao;
    @Autowired
    private final UrlMapDao urlMapDao;
    @Autowired
    private final CompressionCodeDao compressionCodeDao;

    private final UrlValidator urlValidator = new UrlValidator(new String[]{CommonConstant.HTTP_PROTOCOL,
            CommonConstant.HTTPS_PROTOCOL});

    @Value("${compress.code.batch:100}")
    private Integer compressCodeBatch;

    private UrlMapService self;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        self = beanFactory.getBean(UrlMapService.class);
    }

    public UrlMap getUrlMap(String compressionCode){
        return urlMapCacheManager.loadUrlMapCacheByCompressCode(compressionCode);
    }

    /**
     * 创建短链映射
     *
     * @param domain       domain
     * @param insertEntity insertEntity
     * @return String String
     */
    public String createUrlMap(String domain, UrlMap insertEntity) {
        DistributedLock lock = distributedLockFactory.provideDistributedLock(LockKey.CREATE_URL_MAP.getCode());
        try {
            lock.lock(LockKey.CREATE_URL_MAP.getReleaseTime(), TimeUnit.MILLISECONDS);
            CompressionCode compressionCode = getAvailableCompressCode();
            Assert.isTrue(Objects.nonNull(compressionCode) &&
                    CompressionCodeStatus.AVAILABLE.getValue().equals(compressionCode.getCodeStatus()), "压缩码不存在或者已经被使用");
            String longUrl = insertEntity.getLongUrl();
            Assert.isTrue(urlValidator.isValid(longUrl), String.format("链接[%s]非法", longUrl));
            DomainConf domainConf = domainConfDao.selectByDomain(domain);
            Assert.notNull(domainConf, String.format("域名不存在[c:%s]", domain));
            UrlMap urlMap = new UrlMap();
            urlMap.setLongUrl(insertEntity.getLongUrl());
            String code = compressionCode.getCompressionCode();
            String shortUrl = String.format("%s://%s/%s", domainConf.getProtocol(), domainConf.getDomainValue(), code);
            urlMap.setShortUrl(shortUrl);
            urlMap.setCompressionCode(code);
            urlMap.setUrlStatus(insertEntity.getUrlStatus());
            urlMap.setDescription(insertEntity.getDescription());
            CompressionCode updater = new CompressionCode();
            updater.setCodeStatus(CompressionCodeStatus.USED.getValue());
            updater.setId(compressionCode.getId());
            // 事务
            self.saveUrlMapAndUpdateCompressCode(urlMap, updater);
            // 刷新缓存
            urlMapCacheManager.refreshUrlMapCache(urlMap);
            return shortUrl;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 保存短链映射和更新压缩码状态
     *
     * @param urlMap          urlMap
     * @param compressionCode compressionCode
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveUrlMapAndUpdateCompressCode(UrlMap urlMap, CompressionCode compressionCode) {
        compressionCodeDao.updateByPrimaryKeySelective(compressionCode);
        urlMapDao.insertSelective(urlMap);
    }

    /**
     * 获取一个可用的压缩码
     *
     * @return CompressCode
     */
    private CompressionCode getAvailableCompressCode() {
        CompressionCode compressionCode = compressionCodeDao.getLatestAvailableCompressionCode();
        if (Objects.nonNull(compressionCode)) {
            return compressionCode;
        } else {
            generateBatchCompressionCodes();
            return Objects.requireNonNull(compressionCodeDao.getLatestAvailableCompressionCode());
        }
    }

    /**
     * 批量生成压缩码
     */
    private void generateBatchCompressionCodes() {
        for (int i = 0; i < compressCodeBatch; i++) {
            long sequence = sequenceGenerator.generate();
            CompressionCode compressionCode = new CompressionCode();
            compressionCode.setSequenceValue(String.valueOf(sequence));
            String code = ConversionUtils.X.encode62(sequence);
            code = code.substring(code.length() - 6);
            compressionCode.setCompressionCode(code);
            compressionCodeDao.insertSelective(compressionCode);
        }
    }

}
