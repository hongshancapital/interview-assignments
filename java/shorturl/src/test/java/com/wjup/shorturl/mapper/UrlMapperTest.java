package com.wjup.shorturl.mapper;

import com.wjup.shorturl.ShorturlApplication;
import com.wjup.shorturl.config.TestConfig;
import com.wjup.shorturl.entity.UrlEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class) // 等价于使用 @RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ShorturlApplication.class, TestConfig.class })
@Slf4j
public class UrlMapperTest {

    @Autowired
    private UrlMapper urlMapper;

    @Before
    public void init() {
        log.info("测试开始。。。");
    }

    @After
    public void after(){
        log.info("测试结束。。。");
    }

    /**
     * mapper 层测试
     * 测试创建长短链接对应数据是否可持久化成功
     */
    @Test
    public void testCreateShortUrl(){
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setLongUrl("https://github.com/scdt-china/interview-assignments/tree/master/java");
        urlEntity.setShortUrlId("u2oi3n");
        urlEntity.setUuid(UUID.randomUUID().toString());
        int shortUrl = urlMapper.createShortUrl(urlEntity);
        Assert.assertEquals(1,shortUrl);
    }

    /**
     * 测试根据短链接id查找对应的长链接返回到web层进行重定向请求
     */
    @Test
    public void testFindByShortUrlId(){
        UrlEntity u2oi3n = urlMapper.findByShortUrlId("u2oi3n");
        Assert.assertNotNull(u2oi3n);
    }

    /**
     * 测试修改数据
     */
    @Test
    public void testUpdateShortUrl(){
        urlMapper.updateShortUrl("u2oi3n",new Date());
    }

    @Test
    public void testFindByPwd(){
        UrlEntity u2oi3n = urlMapper.findByPwd("u2oi3n", "123456");
        Assert.assertNotNull(u2oi3n);
    }

}
