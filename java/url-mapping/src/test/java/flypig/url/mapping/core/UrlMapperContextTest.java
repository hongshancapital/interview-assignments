package flypig.url.mapping.core;

import flypig.url.mapping.DataInitial;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlMapperContextTest extends DataInitial{

    @Test
    public void testContext() {
        List<String> testUrls = Arrays.asList(
               "www.baidu.com1",
               "www.baidu.com2",
               "hello.world",
               "helloqq.cn",
               "www.baidu.com111",
               "www.baidu.com12",
               "hello.world1",
               "helloqq.cn1",
               "www.baidu.c1om1",
               "www.baidu1.com2",
               "hello.worl1d",
               "helloqq.cn1",
               "www.baidu.c1om1",
               "www.baidu.co1m2",
               "hello.world1",
               "helloqq.cn",
               "1www.baidu.com1",
               "1www.baidu.com2",
               "h1ello.world",
               "he1lloqq.cn",
               "www.b1aidu.com1",
               "www.ba1idu.com2",
               "hello.w1orld",
               "helloqq.cn/h"
        );
        UrlMapperContext urlMapper = UrlMapperContext.getInstance();
        // insert
        Map<String, String> mapper = new HashMap<>();
        for (String url : testUrls) {
            mapper.put(url, urlMapper.long2short(url));
        }

        for (String url : testUrls) {
            Assert.assertTrue(mapper.get(url).equals(urlMapper.long2short(url)));
        }

        for (String url : testUrls) {
            Assert.assertTrue(url.equals(urlMapper.short2long(mapper.get(url))));
        }
    }
}
