package com.example.shorturl.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yyp
 * @date 2022/1/16 20:20
 */
public class ThreadPoolExecutorUtils {

    /**
     * 可以参数配置
     * @return
     */
    public static ThreadPoolExecutor createExecutorPool() {
        return new ThreadPoolExecutor(10, 20, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(50),
                new ThreadFactoryBuilder().setNameFormat("generate-short-url-%d").build());
    }
}
