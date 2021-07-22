package com.hello.tinyurl.dao;

import org.springframework.stereotype.Repository;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: Shuai
 * @date: 2021-7-14 9:53
 * @description:
 */
@Repository
public class UniqueIdDao {

    private static final AtomicLong uniqueId = new AtomicLong(10000);

    /**
     * 返回唯一自增ID
     */
    public Long getUniqueId() {
        return uniqueId.incrementAndGet();
    }
}
