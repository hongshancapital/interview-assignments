package com.example.pengchao.shorturl.service;

import com.example.pengchao.shorturl.BaseUnitTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * @author pengchao04
 * @date 2022/5/23 11:50
 */
public class ShortUrlServiceTest extends BaseUnitTest {

    @Autowired
    private ShortUrlService shortUrlService;

    @Test
    void getLongUrl() {
        String shortUrl = shortUrlService.getShortUrl("http://www.baidu.com");
        String longUrl = shortUrlService.getLongUrl(shortUrl);
        MatcherAssert.assertThat(longUrl.equals("http://www.baidu.com"), Matchers.is(true));

        String shortUrl2 = shortUrlService.getShortUrl("http://www.google.com");
        String longUrl2 = shortUrlService.getLongUrl(shortUrl2);
        MatcherAssert.assertThat(longUrl2.equals("http://www.google.com"), Matchers.is(true));

        String shortUrl3 = shortUrlService.getShortUrl("http://www.baidu.com");
        MatcherAssert.assertThat(shortUrl3.equals(shortUrl), Matchers.is(true));

        Throwable throwable = catchThrowable(() ->  shortUrlService.getLongUrl("google"));
        assertThat(throwable).isInstanceOf(RuntimeException.class).hasMessage("未找到短url对应的长url");
    }
}