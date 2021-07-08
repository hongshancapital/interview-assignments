package com.hongshanziben.assignment.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @author SJYUAN:KINGSJ.YUAN@FOXMAIL.COM
 * @date 2021-07-08.
 */
public class SnowFlakeUtil {

    /**
     * 保证线程安全，getSnowflake单例
     *
     * @param workerId
     * @param datacenterId
     * @return
     */
    public static long snowflakeCode(long workerId, long datacenterId) {
        Snowflake snowflake = IdUtil.getSnowflake(workerId, datacenterId);
        return snowflake.nextId();
    }

    /**
     * 默认生成策略
     *
     * @return
     */
    public static long snowflakeCode() {
        long code = snowflakeCode(0L, 1L);
        return code;
    }

    public static void main(String[] args) {
        System.out.println(snowflakeCode());
        System.out.println(snowflakeCode(0, 1));
    }
}
