package com.tb.link.qatest.domain;

import com.tb.link.domain.service.ShortLinkDataDomainService;
import com.tb.link.domain.service.ShortLinkDomainService;
import com.tb.link.infrastructure.exception.TbRuntimeException;
import com.tb.link.start.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author andy.lhc
 * @date 2022/4/17 13:57
 */
@Slf4j
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ShortLinkDomainServiceImplTest {

    @Autowired
    ShortLinkDomainService shortLinkDomainService ;

    @Autowired
    ShortLinkDataDomainService shortLinkDataDomainService;

    @Test
    public void testGenerateShortLinkWithRandom(){
        String originUrl = "https://www.github.com/test?key=2&key3=5" ;
        String  shortLink = shortLinkDomainService.generateShortLinkWithRandom(originUrl,4) ;
        Assertions.assertNotNull(shortLink);
    }

    @Test
    public void testContainShortCode(){
        String shortLink= "http://scdt.cn/Zt9Hpop1" ;
        Assertions.assertThrows(TbRuntimeException.class,()->{
            shortLinkDataDomainService.containShortLink(shortLink) ;
        });
    }

    @Test
    public void test1(){
       String shortLink = shortLinkDomainService.generateShorLink("https://www.github.com/test?key=2&key3=5ssssssssssssssssssssssssssssssssssssssssssssssssssssssss") ;
       log.info("shortLink:{}",shortLink);
        shortLink = shortLinkDomainService.generateShorLink("https://www.github.com/test?key=2&key3=5ssssssssssssssssssssssssssssssssssssssssssssssssssssssss") ;
        log.info("shortLink:{}",shortLink);
    }


}
