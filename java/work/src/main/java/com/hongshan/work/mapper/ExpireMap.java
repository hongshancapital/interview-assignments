package com.hongshan.work.mapper;

import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定期清理抽象类
 */
@Slf4j
public abstract class ExpireMap {
    protected long ttl;

    protected abstract Map<String, ExpireNode> getMap();
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    public ExpireMap(boolean enableExpire,
                     long ttl,
                     long initialDelay,
                     long period,
                     TimeUnit unit){
        this.ttl = ttl;
        if(enableExpire) {
            executor.scheduleAtFixedRate(new IntervalCleanTask(), initialDelay, period, unit);
        }
    }

    public ExpireNode newNode(String shorUrl){
        return new ExpireNode(shorUrl, System.currentTimeMillis() + ttl);
    }

//    public void setTtl(long ttl){
//        this.ttl = ttl;
//    }

    class IntervalCleanTask implements Runnable{
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            Map realMap = getMap();
            if(realMap != null) {
                realMap.keySet().removeIf(key -> {
                    ExpireNode expireNode = (ExpireNode) realMap.get(key);
                    if (currentTime >= expireNode.getExpire()) {
                        log.info("IntervalCleanTask expired key {} removed!", key);
                        return true;
                    }
                    return false;
                });
            }
        }
    }
}