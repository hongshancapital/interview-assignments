package com.domainserver.handle;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 并发自动上锁解锁
 *
 * @author Administrator
 * @Date 2021/9/21
 */
public class AutoUnlockProxy implements Closeable {

    ReentrantLock lock = new ReentrantLock();

    public AutoUnlockProxy(ReentrantLock lock) {
        this.lock = lock;
    }

    @Override
    public void close()  {
        lock.unlock();
    }

    public void lock() {
        lock.lock();
    }

}
