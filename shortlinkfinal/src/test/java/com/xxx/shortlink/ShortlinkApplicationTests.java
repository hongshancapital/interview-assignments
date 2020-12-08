package com.xxx.shortlink;

import com.xxx.shortlink.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ShortlinkApplicationTests {

    @Autowired
    private LinkService linkService;

    @Test
    void contextLoads() {
    }

    @Test
    void generateShortLink(){

        String link = "http://www.google1.com";
        String shortLink = linkService.save(link);
        log.info("shortLink: {}", shortLink);
    }


    @Test
    void getLink(){
        String shortLink = "kaka";
        String link = linkService.get(shortLink);
        log.info("link: {}", link);
    }
}
