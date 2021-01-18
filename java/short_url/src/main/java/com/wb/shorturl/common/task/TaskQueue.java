package com.wb.shorturl.common.task;

import com.wb.shorturl.entity.ShortUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author bing.wang
 * @date 2021/1/8
 */

@Component
public class TaskQueue {


    /**
     * 处理长网址生成短网址接口的队列并设置缓冲容量
     */

    private BlockingQueue<AsyncVo<ShortUrl, Object>> taskQueue;

    public BlockingQueue<AsyncVo<ShortUrl, Object>> getTaskQueue() {

        return taskQueue;
    }

    @Value("${request.queue.size}")
    public void setTaskQueue(int queueSize) {
        taskQueue = new LinkedBlockingQueue<>(queueSize);
    }

}