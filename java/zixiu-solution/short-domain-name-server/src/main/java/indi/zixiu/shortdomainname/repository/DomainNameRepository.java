package indi.zixiu.shortdomainname.repository;

import indi.zixiu.shortdomainname.entity.DomainNameEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class DomainNameRepository {
    private final Map<Long, DomainNameEntity> shortNameToDomainName = new HashMap<>(1000);
    private final Map<String, DomainNameEntity> longNameToDomainName = new HashMap<>(1000);

    private final ReadWriteLock repositoryLock = new ReentrantReadWriteLock();  // 控制并发读写数据库的锁

    public int getDomainNameCount() {
        return shortNameToDomainName.size();
    }

    // 插入不会获取数据库的锁

    /**
     * 插入一条域名记录，该域名记录的 shortName 字段值不能与数据库中的 shortName 字段值重复， longName 也一样，否则抛异常
     *
     * @param domainNameEntity
     * @throws DuplicateKeyException shortName 或 longName 字段值与数据库中的对应字段值重复
     */
    public void insert(DomainNameEntity domainNameEntity) throws DuplicateKeyException {
        if (shortNameToDomainName.containsKey(domainNameEntity.getShortName())) {
            throw new DuplicateKeyException("DomainNameRepository 键重复, 键名: shortName，键值: " + domainNameEntity.getShortName());
        }

        if (longNameToDomainName.containsKey(domainNameEntity.getLongName())) {
            throw new DuplicateKeyException("DomainNameRepository 键重复, 键名: longName，键值: " + domainNameEntity.getLongName());
        }

        shortNameToDomainName.put(domainNameEntity.getShortName(), domainNameEntity);
        longNameToDomainName.put(domainNameEntity.getLongName(), domainNameEntity);
    }

    // 会获取数据库的 lockMode类型锁
    public void insert(DomainNameEntity domainNameEntity, LockMode lockMode) {
        lock(lockMode);
        insert(domainNameEntity);
        unlock(lockMode);
    }

    public DomainNameEntity findByShortName(long shortName) {
        return shortNameToDomainName.get(shortName);
    }

    public DomainNameEntity findByShortName(long shortName, LockMode lockMode) {
        lock(lockMode);
        DomainNameEntity domainNameEntity = findByShortName(shortName);
        unlock(lockMode);
        return domainNameEntity;
    }

    public DomainNameEntity findByLongName(String longName) {
        return longNameToDomainName.get(longName);
    }

    public DomainNameEntity findByLongName(String longName, LockMode lockMode) {
        lock(lockMode);
        DomainNameEntity domainNameEntity = findByLongName(longName);
        unlock(lockMode);
        return domainNameEntity;
    }

    // 获取数据库的读锁
    public void lockForRead() {
        repositoryLock.readLock().lock();
    }

    // 释放数据库的读锁
    public void unlockForRead() {
        repositoryLock.readLock().unlock();
    }

    // 获取数据库的写锁
    public void lockForWrite() {
        repositoryLock.writeLock().lock();
    }

    // 释放数据库的写锁
    public void unlockForWrite() {
        repositoryLock.writeLock().unlock();
    }

    private void lock(LockMode lockMode) {
        if (lockMode == LockMode.READ) {
            lockForRead();
        } else if (lockMode == LockMode.WRITE) {
            lockForWrite();
        }
    }

    private void unlock(LockMode lockMode) {
        if (lockMode == LockMode.READ) {
            unlockForRead();
        } else if (lockMode == LockMode.WRITE) {
            unlockForWrite();
        }
    }
}
