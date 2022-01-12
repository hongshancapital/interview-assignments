package com.example.demo.service.impl;

import cn.hutool.bloomfilter.BloomFilter;
import com.example.demo.config.UrlConfig;
import com.example.demo.model.utils.NodeUtil;
import com.example.demo.model.utils.ShortUrlGenerator;
import com.example.demo.service.IShortUrlService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author wangxiaosong
 * @since 2022/1/10
 */
@Service
public class ShortUrlServiceImpl implements IShortUrlService {

    @Autowired
    private UrlConfig urlConfig;

    private static final String MARK = "#";

    @Autowired
    private BloomFilter bloomFilter;

    @Autowired
    private NodeUtil nodeUtil;


    @Override
    public String longUrlConvertShortUrl(String longUrl) {
        // 生成短链
        String shortUrl = ShortUrlGenerator.generate(longUrl);
        return saveUrlToNodeMap(shortUrl, longUrl, longUrl);
    }

    @Override
    public String shortUrlConvertLongUrl(String shortUrl) {
        // 判断短链是否不存在
        if (bloomFilter.contains(shortUrl)) {
            return nodeUtil.get(shortUrl);
        } else {
            throw new RuntimeException("请求的url不存在,请核对后再试!");
        }
    }

    private String saveUrlToNodeMap(String shortUrl, String longUrl, String conflictUrl) {
        // 在过滤器中查找短url是否存在
        if (bloomFilter.contains(shortUrl)) {
            // 存在则直接返回
            if (longUrl.equals(nodeUtil.get(shortUrl))) {
                return shortUrl;
            }
        } else {
            // 布隆过滤器中不存在,则保存键值对,并添加到布隆过滤器
            if (nodeUtil.get(shortUrl).equals(StringUtils.EMPTY)) {
                synchronized (shortUrl.intern()) {
                    //双重判空，防止并发问题
                    if (nodeUtil.get(shortUrl).equals(StringUtils.EMPTY)) {
                        nodeUtil.put(shortUrl, longUrl);
                        bloomFilter.add(shortUrl);
                        return shortUrl;
                    }
                }
            }
        }
        // 解决ConcurrentHashMap可能出现的hash冲突问题
        conflictUrl = longUrl + MARK;
        shortUrl = ShortUrlGenerator.generate(conflictUrl);
        return saveUrlToNodeMap(shortUrl, longUrl, conflictUrl);
    }

}
