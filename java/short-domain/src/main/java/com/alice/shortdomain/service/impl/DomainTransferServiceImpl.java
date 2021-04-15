package com.alice.shortdomain.service.impl;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import com.alice.shortdomain.dto.RequestDTO;
import com.alice.shortdomain.dto.ResponseDTO;
import com.alice.shortdomain.service.DomainTransferService;
import com.alice.shortdomain.utils.RandomStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Alice [l1360737271@qq.com]
 * @date 2021/4/14 9:51
 */

@Service
public class DomainTransferServiceImpl implements DomainTransferService {

    private final static Logger log = LoggerFactory.getLogger(DomainTransferServiceImpl.class);

    /**
     * TODO:场景不明确缓存暂定1000万
     * 用于缓存长域名与短域名对应关系
     */
    private final static LRUCache<String, String> cache = CacheUtil.newLRUCache(10_000_000);

    private final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Value("${domain.prefix:http://localhost/}")
    private String DOMAIN_PREFIX;

    @Override
    public ResponseDTO<String> transferShort(RequestDTO request) {
        try {
            lock.writeLock().lock();
            Assert.notNull(request);
            ResponseDTO<String> response = new ResponseDTO<>();
            if (StringUtils.isBlank(request.getUrl())) {
                response.setCode(500);
                response.setMessage("转换失败，url不能为空");
                response.setData("");
                return response;
            }

            // TODO: 换成自己实现的随机字符串生成工具
            // 生成短域名长度
            int length = RandomUtil.randomInt(1, 8);
            // 生成短域名
            String shortUrl = RandomStringUtil.randomStr(length);
            // 避免重复的key ，保证key （短域名） 不重复， 不保证长域名的不重复
            while (cache.containsKey(shortUrl)) {
                length = RandomUtil.randomInt(1, 8);
                shortUrl = RandomStringUtil.randomStr(length);
            }
            //
            cache.put(shortUrl, request.getUrl());
            response.setCode(200);
            response.setMessage("转换成功");
            response.setData(String.format("%s/%s", DOMAIN_PREFIX, shortUrl));
            log.info("{}", response);
            return response;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public ResponseDTO<String> search(RequestDTO request) {
        try {
            lock.readLock().lock();
            Assert.notNull(request);
            String shortUrl = request.getUrl().replaceFirst(DOMAIN_PREFIX + "/", "");
            ResponseDTO<String> response = new ResponseDTO<>();
            response.setCode(200);
            if (cache.containsKey(shortUrl)) {
                response.setData(cache.get(shortUrl));
                response.setMessage("查询成功");
            } else {
                response.setData("");
                response.setMessage(String.format("未查询到%s对应的长域名", shortUrl));
            }
            log.info("{}", response);
            return response;
        } finally {
            lock.readLock().unlock();
        }
    }
}
