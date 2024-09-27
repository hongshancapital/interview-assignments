package com.scdt.cache.utils;

/**
 * 系统工具类
 */
public abstract class SystemUtils {

    public static long allocatedMemory() {
        return (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory());
    }

    public static long presumableFreeMemory() {
        return Runtime.getRuntime().maxMemory() - allocatedMemory();
    }

    public static float allocatedMemoryRate() {
        return allocatedMemory() / Runtime.getRuntime().maxMemory();
    }
}
