package com.example.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class DomainServiceTest {

    @Autowired
    DomainService domainService;

    @Test()
    void saveDomainMethod() {
        log.info(domainService.saveDomainMethod("www.dd.cpm"));
    }
}