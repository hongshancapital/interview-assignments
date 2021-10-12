package com.moonciki.interview.service.url;

import com.moonciki.interview.InterviewApplication;
import com.moonciki.interview.InterviewApplicationTests;
import com.moonciki.interview.utils.CommonUtil;
import com.moonciki.interview.utils.ShortUrlGenerator;
import com.moonciki.interview.vo.url.ShortUrlVo;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={InterviewApplication.class, InterviewApplicationTests.class})
public class ShortUrlServiceTest {

    @Autowired
    private ShortUrlService shortUrlService;

    @Test
    public void testCreateShort() {

        long startTime = System.currentTimeMillis();

        System.out.println("尝试次数    短网址");

        String fullUrl = "https://www.sojson.com/encrypt_md5.html";

        ShortUrlVo aShort1 = shortUrlService.createShort(fullUrl);
        ShortUrlVo aShort2 = shortUrlService.createShort(fullUrl);

        int count6 = 0;
        int count9 = 0;
        int count12 = 0;

        for(int i = 0; i<5000; i++) {
            fullUrl = fullUrl + "?uuid=" + CommonUtil.getUUID(false);

            ShortUrlVo aShort = shortUrlService.createShort(fullUrl);

            Integer tryCount = aShort.getTryCount();
            String shortUrl = aShort.getShortUrl();

            if (tryCount > 15) {
                //12位
                count12++;
//
//                if(count12 == 50){
//                    CommonUtil.writeFileContent(reportPath, "\ncount12 over 50 : " + i, true);
//                }

            }else if (tryCount > 12) {
                //9位
                count9++;

//                if(count9 == 50){
//                    CommonUtil.writeFileContent(reportPath, "\ncount9 over 50 : " + i, true);
//                }

                log.info("####### ========== " + i);

            } else if (tryCount > 8) {
                //6位
                count6++;

//                if(count6 == 50){
//                    CommonUtil.writeFileContent(reportPath, "\ncount6 over 50 : " + i, true);
//                }
                log.info("==========" + i);
            }

            if(i % 1000 == 0) {
                System.out.println(tryCount + "    " + shortUrl);
            }
        }

        log.info("6位：" + count6 + "    9位：" + count9 + "    12位：" + count12);

        CommonUtil.reportSpend(startTime);

    }

    @Test
    public void testGetFullUrl() {

        String fullUrl = "https://www.sojson.com/encrypt_md5.html";

        ShortUrlVo aShort = shortUrlService.createShort(fullUrl);

        ShortUrlVo aShort1 = shortUrlService.getFullUrl(aShort.getShortUrl());

        System.out.println(aShort1.getFullUrl());

    }
}