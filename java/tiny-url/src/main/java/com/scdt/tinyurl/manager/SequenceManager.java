package com.scdt.tinyurl.manager;


import com.scdt.tinyurl.common.ErrorCode;
import com.scdt.tinyurl.config.AppConfig;
import com.scdt.tinyurl.exception.GlobalException;
import com.scdt.tinyurl.model.ExpiredEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Slf4j
public class SequenceManager {

    @Autowired
    private AppConfig appConfig;

    private long maxOffset;

    private final AtomicLong currentOffset = new AtomicLong(0);

    private final AtomicLong currentCount = new AtomicLong(0);

    private final AtomicLong expiredCount = new AtomicLong(0);


    @PostConstruct
    public void init() {
        maxOffset = appConfig.getMaxCapacity();
    }

    public long getNextId() {

        //cas更新当前已分配id的偏移量与已使用容量，到达上限时返回无可用ID异常，等待过期事件释放已使用容量
        return currentOffset.getAndUpdate(current -> {
            if (currentCount.get() == maxOffset) {
                log.warn("当前已无可用ID");
                throw new GlobalException(ErrorCode.ID_EXCEEDED_ERROR);
            }
            currentCount.incrementAndGet();
            return  (current+1) % maxOffset;
        });

    }

    //监听过期事件，当事件发生时移出已使用容量
    @EventListener(ExpiredEvent.class)
    public void consumeExpiredEvent(ExpiredEvent expiredEvent) {
        log.debug("expired event: {}" ,expiredEvent.toString());

        //减少currentCount变量的更新竞争，使用expiredCount进行累计
        //当expiredCount到达配置的批次大小后再更新currentCount，同时提供定时任务调用防止长时间未到达批次无法及时更新currentCount
        if(expiredCount.incrementAndGet() >= appConfig.getExpiredBatchSize()) {
            updateExpiredCount();
        }
    }


    public void updateExpiredCount() {
        //条件判断后expiredCount可能发生变化，重新获取当前过期事件批次数快照
        long expiredEventCount = expiredCount.get();
        //更新当前currentCount与expiredCount值
        currentCount.accumulateAndGet(expiredEventCount,(current,delta) -> current - delta);
        expiredCount.accumulateAndGet(expiredEventCount,(current,delta) -> current - delta);
    }

}
