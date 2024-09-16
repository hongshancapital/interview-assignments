package com.weige.shorturl.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName MemoryUtils
 * @Description 检测内存空间
 * @Author zwwang14
 * @Date 2022/1/24 16:20
 * @Version 1.0
 */
public class MemoryUtils {

    static Logger logger = LoggerFactory.getLogger(MemoryUtils.class);

    /**
     * 检测内存空间
     */
    public static Boolean checkMemoryEnough() throws Exception {
        final long freeMemory = Runtime.getRuntime().freeMemory() / 1024 / 1024;
        final boolean isMemoryEnough = freeMemory > 10;// 内存检测，防止溢出，设定可用内存 > 10M
        if (isMemoryEnough) {
            logger.info(String.format("可用内存剩余：%sM", freeMemory));
        } else {
            throw new Exception("OutOfMemory Error");
        }
        return true;
    }
}
