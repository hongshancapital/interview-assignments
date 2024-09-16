package com.ttts.urlshortener.service.impl;

import com.ttts.urlshortener.business.SenderNumsBusiness;
import com.ttts.urlshortener.domain.SenderNums;
import com.ttts.urlshortener.service.LongValueCreateService;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  分片+单机LongAdder方案实现
 *  先申请该单机自增号段
 *  然后单机内部使用LongAdder自增实现
 */
@Slf4j
@Service
public class PartitionSegmentLongValueCreateServiceImpl implements LongValueCreateService {

    // 预申请的发号段
    private volatile SenderNums current;
    // 自增序列
    private volatile AtomicLong atomicLong;

    // 读写锁，处理发号段申请
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private SenderNumsBusiness senderNumsBusiness;

    @Autowired
    public PartitionSegmentLongValueCreateServiceImpl(
        SenderNumsBusiness senderNumsBusiness) {
        this.senderNumsBusiness = senderNumsBusiness;
    }

  /**
     * @param value 输入字符串
     * @return
     */
    @Override
    public Long create(String value) {
        //检查是否申请发号段
        checkApplied();

        long currentId;
        long end;
        long result;

        lock.readLock().lock();
        try {
            currentId = current.getId();
            end = current.getEndNum();
            result = atomicLong.getAndIncrement();
        } finally {
            lock.readLock().unlock();
        }

        //检查是否使用完成
        if (result > end) {
            log.debug("===>号段{}耗尽，lastEnd:{}，尝试申请新号段", current.getId(), end);
            tryApplySenderNums(currentId);
            return create(value);
        }
        return result;
    }

    protected void tryApplySenderNums(long currentId) {
        lock.writeLock().lock();
        try {
            //检查是否已经申请过
            if (getCurrent() == null || currentId == getCurrent().getId()) {
                //未申请
                applySenderNums();
                log.debug("===>申请新号段成功", getCurrent().getId());
            } else {
                log.debug("===>其他线程已经申请");
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 检查是否申请号段
     */
    protected void checkApplied() {
        if (getCurrent() == null) {
            lock.writeLock().lock();
            try {
                if (getCurrent() == null) {
                    log.debug("初始申请号段");
                    applySenderNums();
                }
            } finally {
                lock.writeLock().unlock();
            }
        }
    }


    protected void applySenderNums() {
        // 重新申请
        atomicLong = null;
        current = senderNumsBusiness.add();
        atomicLong = new AtomicLong(current.getStartNum());
    }

    public SenderNums getCurrent() {
        return current;
    }

    public AtomicLong getAtomicLong() {
        return atomicLong;
    }
}