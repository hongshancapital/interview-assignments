package com.liuwangpan.urlconvert;

import com.liuwangpan.urlconvert.services.UrlConvertService;
import com.liuwangpan.urlconvert.utils.RuntimeHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.util.StopWatch;

/**
 * @Deacription
 * @author wp_li
 **/
@Slf4j
@SpringBootTest
public class TinyUrlCoreServiceImplTest {

    @Autowired
    private UrlConvertService urlConvertService;

    @Autowired
    private CacheManager cacheManager;


    @BeforeEach
    public void beforePrintSystemInfo() {
        RuntimeHelper.printCurrentSystemInfo();
    }

    @AfterEach
    public void afterPrintSystemInfo() {
        RuntimeHelper.printCurrentSystemInfo();
    }


    /**
     * 生成短地址case
     */
    @Test
    void generateShortUrlTest() {
        StopWatch watch = new StopWatch();
        watch.start();
        String longUrl = "http://www.baidu.com/fgksgsgfsg/" + RandomStringUtils.randomAlphanumeric(10);
        String shortUrl = urlConvertService.generateShortUrl(longUrl);
        Assert.assertTrue("生成短链测试失败", StringUtils.isNotBlank(shortUrl));
        watch.stop();
        log.info("****************总耗时={},hash碰撞统计={}", watch.getTotalTimeMillis(), urlConvertService.getHashCollisionCount());
        // log.info("缓存占用内存大小={}", RamUsageEstimator.sizeOf(cacheManager.getCache("shortUrlMapping")) / 1024 / 1024);
    }

    /**
     * 批量测试case
     */
    @Test
    void batchGenerateShortUrlTest() {
        StopWatch watch = new StopWatch();
        watch.start();
        for (int i = 0; i < 10000; i++) {
            String longUrl = "http://www.baidu.com/fgksgsgfsg/" + RandomStringUtils.randomAlphanumeric(10);
            urlConvertService.generateShortUrl(longUrl);
        }
        watch.stop();
        log.info("****************总耗时={},hash碰撞统计={}", watch.getTotalTimeMillis(), urlConvertService.getHashCollisionCount());
        //log.info("缓存占用内存大小={}", RamUsageEstimator.sizeOf(cacheManager.getCache("shortUrlMapping")) / 1024 / 1024);
    }

    /**
     * hash 碰撞case
     */
    @Test
    void testProcessHashCollision() {
        cacheManager.getCache("shortUrlMapping").put("436aHd", "www.test.com");
        String longUrl = "a_http://www.baidu.com/test/aaa";
        urlConvertService.generateShortUrl(longUrl);
    }


    /**
     * 获取长地址case
     */
    @Test
    void getLongUrlTest() {
        String longUrl = "https://baijiahao.baidu.com/s?id=1722459247606477869&wfr=spider&for=pc";
        String shortUrl = urlConvertService.generateShortUrl(longUrl);
        String cacheLongUrl = urlConvertService.getLongUrl(shortUrl);
        Assert.assertTrue("获取长链测试失败", StringUtils.isNotBlank(cacheLongUrl));
    }

    /**
     * 模拟不存在的短链case
     */
    @Test
    void getInValidLongUrlTest() {
        try {
            String cacheLongUrl = urlConvertService.getLongUrl("kkll");
        } catch (Exception e) {

        }
    }


}