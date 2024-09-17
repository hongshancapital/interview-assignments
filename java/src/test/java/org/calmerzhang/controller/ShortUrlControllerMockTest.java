package org.calmerzhang.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.calmerzhang.common.constant.RetConstant;
import org.calmerzhang.controller.dto.ReturnEntity;
import org.calmerzhang.repo.api.ShortUrlRepo;
import org.calmerzhang.repo.model.ShortUrlMappingPO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 模拟测试
 *
 * @author calmerZhang
 * @create 2022/03/06 12:47 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ShortUrlControllerMockTest {

    @MockBean
    private ShortUrlRepo shortUrlRepo;

    @Autowired
    private ShortUrlController shortUrlController;

    String domain = "http://www.baidu.com";
    String shortUrl = "http://www.baidu.com/123";
    String longUrl = "http://www.test.com/aabb/ccdd";

    @Test
    public void testNoDomain() {
        Mockito.when(shortUrlRepo.getDomain()).thenReturn(StringUtils.EMPTY);

        ReturnEntity<String> returnEntity = shortUrlController.getShortUrl(longUrl);
        Assert.assertNotNull(returnEntity);
        Assert.assertEquals(returnEntity.getRetCode(), RetConstant.BUSINESS_FAIL);
    }

    @Test
    public void testCache() {
        Mockito.when(shortUrlRepo.getByLongUrl(longUrl)).thenReturn(ShortUrlMappingPO.builder().shortUrl(shortUrl).longUrl(longUrl).build());

        ReturnEntity<String> returnEntity = shortUrlController.getShortUrl(longUrl);
        Assert.assertNotNull(returnEntity);
        Assert.assertEquals(returnEntity.getData(), shortUrl);
    }

    @Test
    public void testCacheHitEmptyShortUrl() {
        Mockito.when(shortUrlRepo.getDomain()).thenReturn(domain);

        Mockito.when(shortUrlRepo.getByLongUrl(longUrl)).thenReturn(ShortUrlMappingPO.builder().build());

        ReturnEntity<String> returnEntity = shortUrlController.getShortUrl(longUrl);
        Assert.assertNotNull(returnEntity);
        Assert.assertNotNull(returnEntity.getData());
    }
}
