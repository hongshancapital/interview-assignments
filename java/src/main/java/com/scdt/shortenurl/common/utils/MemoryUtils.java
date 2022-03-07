package com.scdt.shortenurl.common.utils;

import com.scdt.shortenurl.common.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 内存空间检测工具类
 * @Author chenlipeng
 * @Date 2022/3/7 2:14 下午
 */
public class MemoryUtils {

    static Logger logger = LoggerFactory.getLogger(MemoryUtils.class);

    /**
     * 检测内存空间
     */
    public static Boolean checkMemoryEnough() {
        final long freeMemory = Runtime.getRuntime().freeMemory() / 1024 / 1024;
        logger.info(String.format("可用内存剩余：", freeMemory));
        final boolean enough = freeMemory > 100;
        if (!enough) {
            throw new BizException("OutOfMemory Error");
        }
        return true;
    }

}
