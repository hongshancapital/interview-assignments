package com.zz.lock;

import com.zz.util.Constants;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用本地缓存实现的锁结构
 * 主要用于生成短码的时候，同时一个url生成短码的锁冲突的问题
 * 锁数量固定，为cpu的个数，通过计算url的hashcode，进行分区加锁处理
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
public class LocalCacheLock implements GeneratorLock {
    /**
     * 大锁
     */
    private ReentrantLock[] bigLockArr = new ReentrantLock[Constants.CPU_CORE];

    public LocalCacheLock() {
        //初始化大锁
        for (int i = 0; i < bigLockArr.length; i++) {
            bigLockArr[i] = new ReentrantLock();
        }
    }

    /**
     * 加锁，先根据url的hashCode进行分区加锁，再次put锁的形式加小锁
     *
     * @return
     */
    public void lock(String key) {
        //获取锁，并加锁
        long hashCode = hashCode(key);
        int index = (int) (hashCode % bigLockArr.length);
        bigLockArr[index].lock();
    }

    /**
     * 解锁处理
     *
     * @param key
     */
    public void unlock(String key) {
        //获取锁，并加锁
        long hashCode = hashCode(key);
        int index = (int) (hashCode % bigLockArr.length);
        bigLockArr[index].unlock();
    }

    private long hashCode(String key) {
        long origin = key.hashCode();
        origin = (origin + Integer.MAX_VALUE) % Integer.MAX_VALUE;
        return origin;
    }
}
