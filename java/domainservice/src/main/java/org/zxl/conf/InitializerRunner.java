package org.zxl.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * 特殊bean
 * 在spring容器启动前回调
 */
@Slf4j
public class InitializerRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("->->->->->->->-> before starting finished ->->->->->->->->->");
        // 初始化本地缓存
        initMenuMap();
        log.info("->->->->->->->->->->-> started ->->->->->->->->->->->->->->->");
    }

    private void initMenuMap() {
        //do something
    }
}
