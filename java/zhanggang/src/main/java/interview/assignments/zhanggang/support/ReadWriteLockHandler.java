package interview.assignments.zhanggang.support;

import interview.assignments.zhanggang.config.exception.SystemException;
import interview.assignments.zhanggang.config.properties.ShortCodeProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class ReadWriteLockHandler {
    private final ShortCodeProperties.LockConfig lockConfig;
    private final ReadWriteLock readWriteLock;

    public ReadWriteLockHandler(ShortCodeProperties shortCodeProperties) {
        this.lockConfig = shortCodeProperties.getLockConfig();
        this.readWriteLock = new ReentrantReadWriteLock();
    }

    public <T> T read(Callable<? extends T> callable) {
        return tryLock(readWriteLock.readLock(), callable);
    }

    public <T> T write(Callable<? extends T> callable) {
        return tryLock(readWriteLock.writeLock(), callable);
    }

    private <T> T tryLock(Lock lock, Callable<? extends T> callable) {
        try {
            if (lock.tryLock(lockConfig.getTimeout(), lockConfig.getTimeunit())) {
                try {
                    return callable.call();
                } finally {
                    lock.unlock();
                }
            }
            return null;
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new SystemException(e);
        }
    }
}
