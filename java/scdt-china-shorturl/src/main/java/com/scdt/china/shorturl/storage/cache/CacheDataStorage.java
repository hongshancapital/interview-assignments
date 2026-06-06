package com.scdt.china.shorturl.storage.cache;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.scdt.china.shorturl.pojo.Url;
import com.scdt.china.shorturl.storage.AbstractDataStorage;
import com.scdt.china.shorturl.storage.DataStorage;
import com.scdt.china.shorturl.storage.DataStorageEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zhouchao
 * @Date: 2021/12/08 18:42
 * @Description:
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "shortUrl", name = "dataType",havingValue = "cache")
public class CacheDataStorage extends AbstractDataStorage {

    @Value("${urlBloomFilter.total}")
    private int total;

    /**
     * 链接的布隆过滤器，用短链接获取长链接时判断用,TOTAL大小可根据实际情况而定
     */
    BloomFilter<String> urlBloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), total);

    /**
     * 保存短链接的map
     */
    public static final Map<String, Url> URL_MAP = new ConcurrentHashMap<>();

    @Override
    public int getDataStorageType() {
        return DataStorageEnum.cache.getCode();
    }

    @Override
    public boolean saveUrl(String shortCode, Url url) {
        // 如果重复保存相同的key，或者出现hash碰撞，则保存原来的
        if (URL_MAP.containsKey(shortCode)){
            return false;
        }

        Url u = URL_MAP.put(shortCode, url);
        urlBloomFilter.put(shortCode);
        return true;
    }

    @Override
    public Url mapping(String shortCode) {
        if (!urlBloomFilter.mightContain(shortCode)) {
            log.warn("短连接：{}，未匹配到相应的连接", shortCode);
        }

        Url url = URL_MAP.get(shortCode);
        return url;
    }
}
