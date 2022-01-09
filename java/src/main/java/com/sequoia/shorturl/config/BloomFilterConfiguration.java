package com.sequoia.shorturl.config;

import cn.hutool.bloomfilter.BloomFilter;
import cn.hutool.bloomfilter.BloomFilterUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: yanggj
 * @Description: TODO
 * @Date: 2022/1/6 21:47
 * @Version: 1.0.0
 */
@Configuration
public class BloomFilterConfiguration {

    @Value("${filter.bloom.expected-insertions}")
    private Integer expectedInsertion;

    @Bean
    public BloomFilter bloomFilter() {
        return BloomFilterUtil.createBitMap(expectedInsertion);
    }
}
