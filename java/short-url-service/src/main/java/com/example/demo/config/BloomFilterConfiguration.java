package com.example.demo.config;


import cn.hutool.bloomfilter.BloomFilter;
import cn.hutool.bloomfilter.BloomFilterUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author wangxiaosong
 * @since 2022/1/10
 */
@Component
@ConfigurationProperties(prefix = "bloom.filter")
public class BloomFilterConfiguration {

    private Integer expectedInsertion;

    @Bean
    public BloomFilter bloomFilter() {
        return BloomFilterUtil.createBitMap(expectedInsertion);
    }

    public Integer getExpectedInsertion() {
        return expectedInsertion;
    }

    public void setExpectedInsertion(Integer expectedInsertion) {
        this.expectedInsertion = expectedInsertion;
    }
}
