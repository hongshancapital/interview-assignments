package com.liupf.tiny.url;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import com.liupf.tiny.url.domain.TinyURL;
import com.liupf.tiny.url.generator.TinyURLGenerator;
import com.liupf.tiny.url.repository.ITinyURLRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ActiveProfiles("dev")
public class CacheRepositoryTest {

    @Resource
    private ITinyURLRepository tinyURLRepository;

    @Resource
    private TinyURLGenerator tinyURLGenerator;

    @Test
    @DisplayName("构造ID生成器")
    public void testSaveTinyUrl() {
        // 1K 字符
        String str = RandomStringUtils.randomAscii(1024);
        TinyURL tinyURL = tinyURLGenerator.build(str);
        tinyURLRepository.saveTinyUrl(tinyURL);
        TinyURL newTinyUrl = tinyURLRepository.findByCode(tinyURL.getCode());
        Assert.isTrue(newTinyUrl != null && str.equals(newTinyUrl.getLongUrl()), "成功");
    }


    @Test
    @DisplayName("批量构造ID生成器")
    public void testBatchSaveTinyUrl() {
        // 存储10W记录，测试缓存是否会溢出
        for (int i = 0; i < 100000; i++) {
            if (i % 10000 == 0) {
                log.info("执行第{}条", i);
            }
            testSaveTinyUrl();
        }
    }

}
