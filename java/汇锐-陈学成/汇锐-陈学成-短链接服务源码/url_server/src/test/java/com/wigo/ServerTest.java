package com.wigo;

import com.wigo.core.CustomException;
import com.wigo.service.UrlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wigo.chen
 * @date 2020/7/27 23:05
 * Introduction: 测试单元
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ServerTest {
    @Autowired
    UrlService urlService;

    @Test
    public void checkMakeUrl() {
        String url = "http://www.baidu.com";
        String shortUrl = urlService.getShortUrl(url);
        try {
            String longUrl = urlService.getLongUrl(shortUrl);
            if (url.equals(longUrl)) {
                System.out.println("获取的长链接与原链接一致，测试成功");
            }else{
                System.out.println("获取的长链接与原链接不符，测试失败");
            }
        } catch (CustomException e) {
            System.out.println("获取长链接失败");
        }
    }
}
