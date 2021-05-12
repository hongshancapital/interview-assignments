package net.rungo.zz.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class DomainServiceTest {
    @Autowired
    DomainService domainService;

    /**
     * 短域名存储接口
     * 接受长域名信息，返回短域名信息
     */
    @Test
    void shortDomainSaveHandle() {
        try {
            domainService.shortDomainSaveHandle("aaaaaaaabbbbbbcccc.com");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试短域名读取接口
     * 缓存没有返回错误信息
     */
    @Test
    void shortDomainReadHandle() {
        String res = domainService.shortDomainReadHandle("short.com.cn");
        log.info("{}", res);
    }

    /**
     * 测试短域名读取接口
     * 接受短域名信息，返回长域名信息
     */
    @Test
    void shortDomainReadHandle2() {
        String shortDomain = null;
        try {
            shortDomain = domainService.shortDomainSaveHandle("aaaaaaaabbbbbbcccc.com");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String res = domainService.shortDomainReadHandle(shortDomain);
        log.info("{}", res);
    }
}