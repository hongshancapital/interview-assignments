package interview.assignments.zhanggang.support.lock.impl;

import interview.assignments.zhanggang.config.exception.SystemException;
import interview.assignments.zhanggang.config.exception.error.LockTimeoutException;
import interview.assignments.zhanggang.config.properties.ShortenerConfig;
import interview.assignments.zhanggang.support.lock.LockHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class LockHandlerInMemoryImpl implements LockHandler {
    private final ShortenerConfig shortenerConfig;
    private final Map<Integer, ReadWriteLock> readWriteLocks;
    private final Lock mapLock;

    public LockHandlerInMemoryImpl(ShortenerConfig shortenerConfig) {
        this.shortenerConfig = shortenerConfig;
        this.mapLock = new ReentrantLock();
        this.readWriteLocks = new HashMap<>();
    }

    @Override
    public <T> T read(String lockId, Callable<? extends T> callable) {
        return lock(getLock(lockId).readLock(), callable);
    }

    @Override
    public <T> T write(String lockId, Callable<? extends T> callable) {
        return lock(getLock(lockId).writeLock(), callable);
    }

    private <T> T lock(Lock lock, Callable<? extends T> callable) {
        try {
            final ShortenerConfig.LockConfig lockConfig = shortenerConfig.getLockConfig();
            if (lock.tryLock(lockConfig.getTimeout(), lockConfig.getTimeunit())) {
                try {
                    return callable.call();
                } finally {
                    lock.unlock();
                }
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new SystemException(e);
        }
        throw new LockTimeoutException();
    }

    private ReadWriteLock getLock(String id) {
        final int lockId = id.hashCode() % shortenerConfig.getLockConfig().getMaxPoolSize();
        mapLock.lock();
        try {
            return readWriteLocks.computeIfAbsent(lockId, k -> new ReentrantReadWriteLock());
        } finally {
            mapLock.unlock();
        }
    }
}
