package com.rad.shortdomainname.service.impl;

import com.rad.shortdomainname.service.IdGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author xukui
 * @program: ShortDomainName
 * @description: 雪花算法
 * @date 2022-03-19 16:02:18
 */

@Slf4j
@Service
public class SnowFlakeServiceImpl implements IdGeneratorService {

    private final int QUEUE_SIZE = 400;

    private final BlockingQueue<Long> queue = new LinkedBlockingQueue<>(QUEUE_SIZE);

    @PostConstruct
    public void init() {
        Thread thread;
        String threadName = "snowFlake";
        thread = new Thread(() -> {
            IdWorker idWorker = new IdWorker(1, 1, 1);
            while (true) {
                try {
                    long id = idWorker.nextId();
                    queue.put(id);
                    log.info("生成成功,线程{}", threadName);
                } catch (InterruptedException e) {
                    log.error("生成唯一id发生异常,msg:{}", ExceptionUtils.getStackTrace(e));
                }
            }
        });
        thread.setName(threadName);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public Long generateId() {
        return queue.poll();
    }
}
