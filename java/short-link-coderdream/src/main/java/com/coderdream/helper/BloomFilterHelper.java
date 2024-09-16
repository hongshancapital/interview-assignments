package com.coderdream.helper;


import com.coderdream.utils.Constants;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

/**
 * 布隆过滤器辅助类
 *
 * @author CoderDream
 * @version 1.0
 * @date 2022/5/8
 */
@Service
@Slf4j
public class BloomFilterHelper {
    /**
     * 布隆过滤器
     */
    private BloomFilter<String> bloomFilter;

    @PostConstruct
    public void init() {
        bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), Constants.BLOOM_FILTER_INSERTION, Constants.BLOOM_FILTER_FPP);
    }

    /**
     * 添加元素到过滤器
     */
    public boolean put(String key) {
        return bloomFilter.put(key);
    }

    /**
     * 判断元素是否存在
     */
    public boolean mightContain(String data) {
        return bloomFilter.mightContain(data);
    }

}