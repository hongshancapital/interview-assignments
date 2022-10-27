package com.sequoia.interview.idGenerator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author 17612735387@163.com
 * @date 2022/8/13 10:44
 **/
@Slf4j
@Service
public class SnowFlakeGenerator implements IdGenerator {

    private final int QUEUE_SIZE = 2000;

    private final BlockingQueue<Long> queue = new LinkedBlockingQueue<>(QUEUE_SIZE);

    ExecutorService executorService = Executors.newFixedThreadPool(4);

    @PostConstruct
    public void init() {
        executorService.execute(()->{
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1, 1);
            while (true) {
                Long id = null;
                try {
                    id = idWorker.nextId();
                    queue.put(id);
                    log.info("put snowFlakeID {}", id);
                } catch (InterruptedException e) {
                    log.error("生成唯一id发生异常,msg:{}", id, e);
                }
            }
        });
        /*Thread thread = new Thread(() -> {
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1, 1);
            while (true) {
                Long id = null;
                try {
                    id = idWorker.nextId();
                    queue.put(id);
                    log.info("put snowFlakeID {}", id);
                } catch (InterruptedException e) {
                    log.error("生成唯一id发生异常,msg:{}", id, e);
                }
            }
        }, "snowFlake");
        thread.setDaemon(true);
        thread.start();*/
    }
    /**
     * @Description:
     * @Param: longLink
     * @return: String
     * @Author: yhzhang
     * @Date: 2022/8/13
     */
    @Override
    public Long generateId() {
        return queue.poll();
    }
}
