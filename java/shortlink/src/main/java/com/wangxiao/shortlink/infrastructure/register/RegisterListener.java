package com.wangxiao.shortlink.infrastructure.register;

import com.wangxiao.shortlink.domain.shortlink.LinkPairRepository;
import com.wangxiao.shortlink.infrastructure.persisitence.PersistenceService;
import com.wangxiao.shortlink.infrastructure.properties.ShortLinkProperties;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class RegisterListener implements ApplicationRunner {
    @Resource
    private ShortLinkProperties shortLinkProperties;
    @Resource
    private RegisterCenter registerCenter;
    @Resource
    private PersistenceService persistenceService;
    @Resource
    private LinkPairRepository linkPairRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        registerCenter.registe(shortLinkProperties.getMachineId());
        linkPairRepository.load(persistenceService.load());
    }
}
