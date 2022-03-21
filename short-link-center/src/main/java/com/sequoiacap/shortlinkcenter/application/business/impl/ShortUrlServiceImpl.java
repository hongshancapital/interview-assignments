package com.sequoiacap.shortlinkcenter.application.business.impl;

import com.sequoiacap.shortlinkcenter.application.business.ShortUrlService;
import com.sequoiacap.shortlinkcenter.domain.shorturl.entity.UrlEntity;
import com.sequoiacap.shortlinkcenter.domain.shorturl.repository.UrlEntityRepository;
import com.sequoiacap.shortlinkcenter.domain.shorturl.task.NotifierTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiuyuan
 * @date 2022/3/17
 */
@Service
@Slf4j
public class ShortUrlServiceImpl implements ShortUrlService {

    @Resource
    private UrlEntityRepository urlEntityRepository;

    /**
     * 异步生成短链编码线程池（预加载）
     */
    private final ExecutorService SHORT_URL_GENERATE_EXECUTOR = Executors.newFixedThreadPool(1);

    /**
     * 默认生成的短链大小（6为编码大概生成480亿左右，加上url失效日期，未来1-2年足够用，实在不够可以修改为8位）
     */
    private static final int SHORT_URL_BIT_DEFAULT = 6;

    @PostConstruct
    public void init() {
        SHORT_URL_GENERATE_EXECUTOR.submit(new NotifierTask());
    }

    @Override
    public String getBySourceUrl(String sourceUrl, String targetDomainName) {
        // 查询短链是否存在，存在直接返回
        // 由于本地存储，本地直接访问即可（实际项目可用 本地缓存+redis 多级缓存优化）
        String targetUrl = urlEntityRepository.getShortUrlBySourceUrl(sourceUrl);
        if (StringUtils.isNotBlank(targetUrl)) {
            return targetUrl;
        }
        // 封装领域对象
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setSourceUrl(sourceUrl);
        // 校验
        urlEntity.sourceUrlValidate();
        // 解析协议
        urlEntity.parseProtocol();
        // 查询是否有预生成的短链,如果有, 不生成短链编码
        String shortCode = NotifierTask.getTask();
        urlEntity.setUrlCode(shortCode);
        // 短链code如果已经预生成, 不生成短链编码
        urlEntity.buildShortUrlCode(SHORT_URL_BIT_DEFAULT, NotifierTask.getShortUrlCodeBloomFilter());
        // 生成目标短链
        urlEntity.buildTargetUrl(targetDomainName);
        // 保存短链()
        urlEntityRepository.saveShortUrl(urlEntity);
        return urlEntity.getTargetUrl();
    }

    @Override
    public String getByShortUrl(String shortUrl) {
        // 查询短链是否存在，存在直接返回
        // 由于本地存储，本地直接访问即可（实际项目可用 本地缓存+redis 多级缓存优化）
        return urlEntityRepository.getSourceUrlByShortUrl(shortUrl);
    }
}
