package com.scdt.tinyurl.schedule;

import com.scdt.tinyurl.manager.SequenceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PurgeTaskScheduler {

    @Autowired
    private SequenceManager sequenceManager;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void updatePerMinute() {
        log.debug("每分钟定时检查更新过期事件，更新currentCount");
        sequenceManager.updateExpiredCount();
    }

}
