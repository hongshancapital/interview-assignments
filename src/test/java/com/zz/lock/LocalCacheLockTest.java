package com.zz.lock;

import org.junit.Test;

/**
 * @author zz
 * @version 1.0
 * @date 2022/4/1
 */
public class LocalCacheLockTest {

    private LocalCacheLock localCacheLock=new LocalCacheLock();

    @Test
    public void lock() {
        localCacheLock.lock("123");
        localCacheLock.unlock("123");
    }

    @Test
    public void unlock() {
    }
}