package com.coderdream.utils;

/**
 * 常量类
 *
 * @author CoderDream
 * @version 1.0
 * @date 2022/5/8
 */
public class Constants {
    /**
     * 62个字符
     */
    public static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 布隆过滤器的容量 100000000L（如果设置为100000L，很容易产生误判）
     */
    public static final Long BLOOM_FILTER_INSERTION = 100000000L;

    /**
     * 期望的误判率
     */
    public static final double BLOOM_FILTER_FPP = 0.01;

    /**
     * Guava Cache 并发级别为8，并发级别是指可以同时写缓存的线程数
     */
    public static final Integer CONCURRENCY_LEVEL = 8;

    /**
     * Guava Cache 缓存容器的初始容量为 10
     */
    public static final Integer INITIAL_CAPACITY = 10;

    /**
     * Guava Cache 设置缓存最大容量为 100000000 Long 的最大值 9,223,372,036,854,775,807 6 位 62 进制数的可能的组合 56,800,235,584
     */
    public static final Long MAXIMUM_SIZE = 100000000L;
}
