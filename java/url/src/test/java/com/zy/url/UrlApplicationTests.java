package com.zy.url;

import com.zy.url.dto.UrlMapDto;
import com.zy.url.service.UrlMapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = UrlApplication.class)
class UrlApplicationTests {

    @Autowired
    private UrlMapService urlMapService;

    @Test
    void createShortUrlTest() {
        UrlMapDto urlMapDto = new UrlMapDto();
        urlMapDto.setOriginUrl("http://taichi.ops.minshenglife.com/#/devops/app-service?type=project&id=26" +
                "&name=%E5%81%A5%E5%BA%B7%E9%99%A9%E4%BA%8B%E4%B8%9A%E9%83%A8%E9%A1%B9%E7%9B%AE&category=GENERAL&organizationId=1");
        urlMapService.createShortUrl(urlMapDto);
    }
}
