package org.calmerzhang.repo.impl;

import lombok.extern.slf4j.Slf4j;
import org.calmerzhang.repo.imp.MemoryShortUrlRepoImp;
import org.calmerzhang.repo.model.ShortUrlMappingPO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 内存存储测试
 *
 * @author calmerZhang
 * @create 2022/03/06 12:59 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class MemoryShortUrlRepoImpTest {

    @Autowired
    private MemoryShortUrlRepoImp memoryShortUrlRepoImp;

    @Test
    public void testLru() {
        String longUrl = "longUrl";
        String shortUrl = "shortUrl";
        for (int i = 1; i < 4096; i++) {

            ShortUrlMappingPO shortUrlMappingPO =
                    ShortUrlMappingPO.builder().longUrl(longUrl + i).shortUrl(shortUrl + i).build();
            memoryShortUrlRepoImp.saveLongShortUrl(shortUrlMappingPO);
        }

        ShortUrlMappingPO byShortUrl = memoryShortUrlRepoImp.getByShortUrl(shortUrl + 1);
        Assert.assertNull(byShortUrl);
        byShortUrl = memoryShortUrlRepoImp.getByShortUrl(shortUrl + 4095);
        Assert.assertNotNull(byShortUrl);
        Assert.assertEquals(byShortUrl.getLongUrl(), longUrl + 4095);
    }
}
