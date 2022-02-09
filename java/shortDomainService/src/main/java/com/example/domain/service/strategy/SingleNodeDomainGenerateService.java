package com.example.domain.service.strategy;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @title: SingleNodeDomainGenerateRule
 * @Author DH
 * @Date: 2021/12/6 15:45
 */
@Component
public class SingleNodeDomainGenerateService implements DomainGenerateService<Long> {

    private volatile static Long id;

    @PostConstruct
    private void initIdVal() {
        synchronized (this) {
            if(Objects.isNull(id)) {
                id = new Long(0);
            }
        }
    }

    /**
     * 获取下一个id值->短域名
     * @return
     */
    private Long getNextIdVal() {
        AtomicLong atomicId = new AtomicLong(id);
        return atomicId.getAndIncrement();
    }

    @Override
    public Long getNextShortDomainId() {

        return getNextIdVal();
    }
}
