package com.moonciki.interview.commons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 系统初始化
 */
@Slf4j
@Component
public class SystemInit implements CommandLineRunner {

    //@Autowired
    //DicConfigService dicConfigService;

    public void initSystem() throws Exception{
        log.info("============================== system init start ==============================");

        try {
            //dicConfigService.cacheDicConfigMap();
        } catch (Exception e) {
            log.error("########## system init error : cacheDicConfigMap faied", e);
            throw e;
        }

        log.info("============================== system init finished ==============================");
    }

    /**
     * spring 初始化完成后会执行该方法
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        initSystem();
    }
}
