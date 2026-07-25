package com.liuxiang.util;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

/**
 * 布隆过滤器单例
 *
 * @author liuxiang6
 * @date 2022-01-06
 **/
public class BloomSingleton {
    private volatile static BloomSingleton bloomSingleton = null;

    /**
     * 最大个数
     */
    private static final Long EXPECT_COUNT = 800000000L;

    /**
     * 判断误差
     */
    private static final Double EXPECT_ERROR_RATE = 0.01;

    private BloomFilter<String> bloomFilter = null;

    private BloomSingleton() {
    }

    public static BloomSingleton getInstance() {
        if (bloomSingleton == null) {
            synchronized (BloomSingleton.class) {
                if (bloomSingleton == null) {
                    bloomSingleton = new BloomSingleton();
                    bloomSingleton.bloomFilter =
                        BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), EXPECT_COUNT, EXPECT_ERROR_RATE);
                }
            }
        }
        return bloomSingleton;
    }

    public void put(String data) {
        if (bloomSingleton == null) {
            throw new RuntimeException("布隆过滤器尚未初始化");
        }
        bloomSingleton.bloomFilter.put(data);
    }

    public boolean isExist(String data) {
        if (bloomSingleton == null) {
            throw new RuntimeException("布隆过滤器尚未初始化");
        }
        return bloomSingleton.bloomFilter.mightContain(data);
    }

}
