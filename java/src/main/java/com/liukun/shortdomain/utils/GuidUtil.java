package com.liukun.shortdomain.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * <b>Class name</b>:
 * </p>
 * <p>
 * <b>Class description</b>: Class description goes here.
 * </p>
 * <p>
 * <b>Author</b>: kunliu
 * </p>
 * <b>Change History</b>:<br/>
 * <p>
 *
 * <pre>
 * Date          Author       Revision     Comments
 * ----------    ----------   --------     ------------------
 * 2021/10/7       kunliu        1.0          Initial Creation
 *
 * </pre>
 *
 * @author kunliu
 * @date 2021/10/7
 * </p>
 */
@Slf4j
public class GuidUtil {
    private AtomicLong value = new AtomicLong(1000000000);

    private long add() {
        long current;
        long next;
        do {
            current = value.get();
            next = current > 999999999 ? 100000000 : current + 1;
        }
        while (!value.compareAndSet(current, next));
        return next;
    }

    public long getGuid() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMM");
        long guid = Long.valueOf(formatter2.format(now) + add());
        return guid;
    }

}
