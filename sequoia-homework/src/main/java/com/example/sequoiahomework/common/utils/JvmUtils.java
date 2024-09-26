package com.example.sequoiahomework.common.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Irvin
 * @description jvm工具类
 * @date 2021/10/10 12:39
 */
@Slf4j
public class JvmUtils {

    public static final long MIN_USABLE = 2048;

    /**
     * 获取当前实例最大可用的内存（以字节为单位）
     *
     * @return long
     * @author Irvin
     * @date 2021/10/10
     */
    public static long getUsableByte() {
        Runtime run = Runtime.getRuntime();
        long max = run.maxMemory();
        long total = run.totalMemory();
        long free = run.freeMemory();
        long usable = max - total + free;

        log.info("最大内存 = {}", max);
        log.info("已分配内存 = {}", total);
        log.info("已分配内存中的剩余空间 = {}", free);
        log.info("最大可用内存 = {}", usable);
        return usable;
    }

}
