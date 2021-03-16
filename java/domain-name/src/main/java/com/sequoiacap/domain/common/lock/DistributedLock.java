package com.sequoiacap.domain.common.lock;

import java.util.concurrent.TimeUnit;

/**
 * 分步式锁
 */
public interface DistributedLock {

    /**
     * 阻塞加锁
     *
     * @param leaseTime leaseTime
     * @param unit      unit
     */
    void lock(long leaseTime, TimeUnit unit);

    /**
     * 尝试加锁
     *
     * @param waitTime  waitTime
     * @param leaseTime leaseTime
     * @param unit      unit
     * @return boolean
     */
    boolean tryLock(long waitTime, long leaseTime, TimeUnit unit);

    /**
     * 解锁
     */
    void unlock();
}
