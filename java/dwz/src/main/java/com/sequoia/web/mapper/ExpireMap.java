package com.sequoia.web.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 键值对定期清理抽象基类
 */
public abstract class ExpireMap {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpireMap.class);
    protected long ttl;
    // 这里提供的Map必须是线程安全的
    protected abstract Map<String, ExpireNode> getMap();
    private ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
    public ExpireMap(boolean enableExpire,
                     long ttl,
                     long initialDelay,
                     long period,
                     TimeUnit unit){
        this.ttl = ttl;
        if(enableExpire) {
            es.scheduleAtFixedRate(new IntervalCleanTask(), initialDelay, period, unit);
        }
    }

    public ExpireNode newNode(String shorUrl){
        return new ExpireNode(shorUrl, System.currentTimeMillis() + ttl);
    }

    public void setTtl(long ttl){
        this.ttl = ttl;
    }

    class IntervalCleanTask implements Runnable{
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            Map realMap = getMap();
            if(realMap != null) {
                realMap.keySet().removeIf(key -> {
                    ExpireNode expireNode = (ExpireNode) realMap.get(key);
                    if (currentTime >= expireNode.getExpire()) {
                        LOGGER.info("expired key {} removed!", key);
                        return true;
                    }
                    return false;
                });
            }
        }
    }
}
