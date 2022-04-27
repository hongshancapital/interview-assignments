package com.scdt.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * IdUtil
 *
 * @author weixiao
 * @date 2022-04-26 14:22
 */
public class IdUtil {

    private static final AtomicLong CREATOR = new AtomicLong(0);

    private IdUtil() {
    }

    public static long generateUniqueId() {
        return CREATOR.getAndIncrement();
    }
}
