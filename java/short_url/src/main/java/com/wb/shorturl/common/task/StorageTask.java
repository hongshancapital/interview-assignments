package com.wb.shorturl.common.task;

import com.wb.shorturl.entity.ShortUrl;
import com.wb.shorturl.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author bing.wang
 * @date 2021/1/8
 */

@Component
public class StorageTask extends Thread {

    @Autowired
    private TaskQueue queue;

    @Autowired
    private ShortUrlService shortUrlService;

    private boolean running = true;

    @Override
    public void run() {
        while (running) {
            try {
                AsyncVo<ShortUrl, Object> vo = queue.getTaskQueue().take();
                ShortUrl shortUrl = vo.getParams();
                shortUrlService.save(shortUrl);
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }

        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}