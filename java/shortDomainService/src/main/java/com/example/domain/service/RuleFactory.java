package com.example.domain.service;

import com.example.domain.service.strategy.DomainGenerateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: RuleFactory
 * @Author DH
 * @Date: 2021/12/6 15:35
 */
@Component
public class RuleFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private static Map<String, DomainGenerateService> DOMAIN_GENERATE_SERVICE_MAP = new HashMap<>();

    public static DomainGenerateService getDomainServiceStrategys(String rule) {
        return DOMAIN_GENERATE_SERVICE_MAP.get(rule);
    }


    @PostConstruct
    public void initShortDomainServiceMap() {
        registerDomainService();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void registerDomainService() {
        Map<String, DomainGenerateService> domainGenerateServices = applicationContext.getBeansOfType(DomainGenerateService.class);

        if (domainGenerateServices.values().size() == 0) {
            return;
        }
        for (DomainGenerateService domainGenerateService : domainGenerateServices.values()) {
            String key = StringUtils.substringBefore(domainGenerateService.getClass().getSimpleName(), "N");
            DOMAIN_GENERATE_SERVICE_MAP.put(key, domainGenerateService);
        }
    }
}
