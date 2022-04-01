package com.zz.lock;

/**
 * 处理并发生成同一个url的时候锁的问题
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
public interface GeneratorLock {
    /**
     * 加锁
     * @param key
     * @throws Exception
     */
    void lock(String key);

    void unlock(String key);
}
