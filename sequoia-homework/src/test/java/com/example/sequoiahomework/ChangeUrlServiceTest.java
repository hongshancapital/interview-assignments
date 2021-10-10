package com.example.sequoiahomework;

import com.example.sequoiahomework.service.ChangeUrlService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Irvin
 * @description 链接转换服务测试
 * @date 2021/10/9 21:08
 */
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class ChangeUrlServiceTest {

    @Resource
    private ChangeUrlService changeUrlService;

    @Test
    public void testChange() {
        String originalUrl = "https://github.com/scdt-china/interview-assignments";
        log.info("原本的链接为：{}", originalUrl);
        String shortUrl = changeUrlService.longToShort(originalUrl);
        log.info("转换后链接为：{}",  shortUrl);

        String shortUrl2 = changeUrlService.longToShort(originalUrl);
        log.info("第二次转换后链接为：{}",  shortUrl2);

        String longUrl = changeUrlService.shortToLong(shortUrl);
        log.info("根据短链接获取长链接结果为：{}",  longUrl);
    }

}
