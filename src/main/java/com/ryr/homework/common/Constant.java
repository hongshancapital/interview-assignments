package com.ryr.homework.common;

/**
 * 常量定义类
 */
public class Constant {

    private Constant() {
    }

    //cache最大长度
    public final static int SHOR_URL_CACHE_MAX_SIZE = 100000000;

    //cache过期天数
    public final static int SHOR_URL_CACHE_EXPIRE_DAYS = 1;

    //cache并发线程数
    public final static int SHOR_URL_CACHE_CONCURRENCY = 10;
}
