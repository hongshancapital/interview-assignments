package com.wb.shorturl.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author bing.wang
 * @date 2021/1/8
 */

@SpringBootTest
public class ShortUrlServiceTest {

    @Autowired
    private ShortUrlService shortUrlService;

    /**
     * 测试存在的短码查询长网址
     *
     * @param shortCode the query shortCode
     */

    @ParameterizedTest
    @ValueSource(strings = {"Os7jWHKNc4"})
    void shortCodeRedirectSuccess(String shortCode) {
        String url = shortUrlService.getOriginUrlByShortCode(shortCode);
        Assertions.assertNotNull(url);
    }


    /**
     * 测试不存在的短码查询长网址
     *
     * @param shortCode the query shortCode
     */

    @ParameterizedTest
    @ValueSource(strings = {"notExistuwS", ""})
    void shortCodeRedirectFailure(String shortCode) {
        String url = shortUrlService.getOriginUrlByShortCode(shortCode);
        Assertions.assertNull(url);
    }

    /**
     * 测试存在的长网址查询短码
     *
     * @param url the url to be converted
     */

    @ParameterizedTest
    @ValueSource(strings = {"https://www.123.com", "http://www.abc.com", "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&ch=&tn=baidu&bar=&wd=%E5%85%A8%E5%9B%BD%E7%96%AB%E6%83%85&oq=%E5%8C%97%E4%BA%AC%E7%96%AB%E6%83%85&rsv_pq=d4733834000065cb&rsv_t=f82ex8ASC3BvNJSI%2F%2BZx5DFtS6a2WeRUs7cP1k2DIdHN5x0x166RYkqw1pU&rqlang=cn"})
    void getShortCodeByOriginUrlSuccess(String url) {
        String shortCode = shortUrlService.getShortCodeByOriginUrl(url);
        Assertions.assertNotNull(shortCode);
    }

    /**
     * 测试不存在的长网址查询短码
     *
     * @param url the url to be converted
     */
    @ParameterizedTest
    @ValueSource(strings = {"https://www.hao123.com", "http://www.taobao2.com"})
    void getShortCodeByOriginUrlFailure(String url) {
        String shortCode = shortUrlService.getShortCodeByOriginUrl(url);
        Assertions.assertNull(shortCode);
    }

}
