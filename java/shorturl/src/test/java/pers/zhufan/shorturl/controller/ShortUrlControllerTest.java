package pers.zhufan.shorturl.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.zhufan.shorturl.ShortUrlApplication;
import pers.zhufan.shorturl.domain.LongUrl;
import pers.zhufan.shorturl.domain.shorturl.ShorterUrl;
import pers.zhufan.shorturl.dto.RespData;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortUrlApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShortUrlControllerTest {

    @Resource
    private ShortUrlController shortUrlController;

    @Resource
    private HttpServletRequest httpServletRequest;

    @Test
    public void generalShortUrl() {

        LongUrl longUrl = new LongUrl("http://www.baidu.cam/api/test/query/09846754834455");

        RespData<ShorterUrl> shorterUrlRespData = shortUrlController.generalShortUrl(httpServletRequest, longUrl);

        System.out.println(shorterUrlRespData);

    }

    @Test
    public void recoverLongUrl() {

        LongUrl longUrl = new LongUrl("http://www.baidu.cam/api/test/query/09846754834455");

        RespData<ShorterUrl> shorterUrlRespData = shortUrlController.generalShortUrl(httpServletRequest, longUrl);

        RespData<LongUrl> longUrlRespData = shortUrlController.recoverLongUrl(httpServletRequest, shorterUrlRespData.getBody());

        System.out.println(longUrlRespData);

    }
}