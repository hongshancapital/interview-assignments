package com.domain.service.system;

import com.domain.service.stroe.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 优雅停机监控
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
@Service
public class MonitorService {

    @Autowired
    private StoreService storeService;

    @Autowired
    private void init(){
        shutdownHook();
    }
    /**
     * 优雅停机 优雅关闭刷盘持久化 以免数据丢失
     */
    public void shutdownHook() {

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            @Override
            public void run() {
                synchronized (this) {
                    storeService.shutdown();
                }
            }
        }, "ShutdownHook"));
    }
}
