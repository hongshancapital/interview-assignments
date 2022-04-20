package pers.zhufan.shorturl.generator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.zhufan.shorturl.ShortUrlApplication;
import pers.zhufan.shorturl.config.ShortUrlConfig;
import pers.zhufan.shorturl.domain.shorturl.ShorterUrl;
import pers.zhufan.shorturl.storage.ShorterStorage;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortUrlApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlShorterGeneratorHashTest {

    @Resource
    UrlShorterGenerator urlShorterGenerator;

    @Resource
    ShortUrlConfig shortUrlConfig;



    @Test
    public void generate() {

        String longUrl = "http://www.baidu.cam/api/test/query/09846754834455";

        ShorterUrl generate = urlShorterGenerator.generate(shortUrlConfig.getKey(), shortUrlConfig.getLenth(), longUrl);

    }
}