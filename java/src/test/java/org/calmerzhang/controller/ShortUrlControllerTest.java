package org.calmerzhang.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.calmerzhang.controller.dto.ReturnEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * controller测试类
 *
 * @author calmerZhang
 * @create 2022/01/20 4:39 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ShortUrlControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetShortUrl() {
        log.info("##########begin test$$$$$$$$$$$$$$");
        String uri = "/shortUrl?longUrl={longUrl}";
        String longUrl = "https://www.cnblogs.com/54chensongxia/p/11414923.html";

        ReturnEntity<String> returnEntity = restTemplate.getForObject(uri, ReturnEntity.class, longUrl);
        Assert.assertNotNull(returnEntity);
        Assert.assertNotEquals(returnEntity.getData(), StringUtils.EMPTY);

        String shortUrl = returnEntity.getData();

        returnEntity = restTemplate.getForObject(uri, ReturnEntity.class, longUrl);
        Assert.assertNotNull(returnEntity);
        Assert.assertEquals(returnEntity.getData(), shortUrl);
    }

    @Test
    public void testGetLongUrl() {
        String getShortUri = "/shortUrl?longUrl={longUrl}";
        String longUrl = "https://www.cnblogs.com/54chensongxia/p/11414923.html";

        ReturnEntity<String> returnEntity = restTemplate.getForObject(getShortUri, ReturnEntity.class, longUrl);
        String shortUrl = returnEntity.getData();
        log.info(returnEntity.toString());

        String getLongUri = "/longUrl?shortUrl={shortUrl}";
        ReturnEntity<String> longUrlReturn = restTemplate.getForObject(getLongUri, ReturnEntity.class, shortUrl);
        log.info(longUrlReturn.toString());
        Assert.assertEquals(longUrl, longUrlReturn.getData());
    }



}
