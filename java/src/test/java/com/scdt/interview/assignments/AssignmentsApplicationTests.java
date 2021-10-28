package com.scdt.interview.assignments;

import com.scdt.interview.assignments.bean.vo.LongLinkParam;
import com.scdt.interview.assignments.bean.vo.ShortLinkParam;
import com.scdt.interview.assignments.service.impl.LinkServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class AssignmentsApplicationTests {

    @Autowired
    LinkServiceImpl linkService;

//    @RepeatedTest(10)
    @Test
    void generateShortUrl() {
        for (int i = 0; i <= 100; i++) {
            LongLinkParam longLinkParam = LongLinkParam.builder().
                    longUrl("https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9493759439419346152%22%7D&n_type=0&p_from=1" + i).build();
            String shortUrl = linkService.generateShortUrl(longLinkParam).getShortUrl();
            log.info("shortUrl===>{}", shortUrl);
            String longUrl = linkService.getLongUrl(ShortLinkParam.builder().shortUrl(shortUrl).build()).getLongUrl();
            log.info("i==>{},longUrl===>{}", i, longUrl);
        }
    }


}
