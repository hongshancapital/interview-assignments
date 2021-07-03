package com.task.shortlinkandlonglink;

import com.task.utils.ShortLinkUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test") //使用单元测试配置
class ShortLinkTests {


    /**
     * 测试短链接
     */
    @Test
    void testShortLinkTests() {
        String longLink = "http://localhost:8080/api/v1/longlink/transformation";
        String shortLinks = ShortLinkUtils.shortLink(longLink);
        System.out.println(shortLinks);
    }

}
