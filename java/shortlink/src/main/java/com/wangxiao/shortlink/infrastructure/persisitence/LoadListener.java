package com.wangxiao.shortlink.infrastructure.persisitence;

import com.wangxiao.shortlink.domain.shortlink.LinkPairRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

@Configuration
@Slf4j
@Order(1)
public class LoadListener implements ApplicationRunner {
    @Resource
    private PersistenceService persistenceService;
    @Resource
    private LinkPairRepository linkPairRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("load persistence start");
        linkPairRepository.load(persistenceService.load());
        log.info("load persistence finished");
    }
}
