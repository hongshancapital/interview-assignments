package com.scdt.assignment.infrastructure.utils;

/**
 * 生成全局ID 工具类型
 */
public abstract class IDUtil {

    private static SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

    public static long nextId() {
        return idWorker.nextId();
    }
}
