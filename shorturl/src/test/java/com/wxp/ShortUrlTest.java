package com.wxp;

import com.wxp.common.Message;
import com.wxp.controller.ShortUrlController;
import com.wxp.service.ShortUrlService;
import com.wxp.util.Base62;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortUrlApplication.class)
public class ShortUrlTest {

    @Autowired
    @Qualifier("shortUrlLocalService")
    private ShortUrlService shortUrlService;

    @Autowired
    private ShortUrlController shortUrlController;

    /**
     * 正常流程测试
     */
    @Test
    public void nomalTest() {
        String url = "http://www.wxp.com/aaabbbccc";
        Message<String> shortUrlMsg = shortUrlController.shortUrl(url);
        String shortUrl = shortUrlMsg.getContent();
        Message<String> originUrlMsg = shortUrlController.originUrl(shortUrl);
        String originUrl = originUrlMsg.getContent();

        Assert.hasLength(shortUrl, "生成短链为空");
        Assert.isTrue(shortUrl.length() <= 8, "短链长度过长：" + shortUrl.length());
        Assert.isTrue(url.equals(originUrl), "根据短链获取的原始链接与原链接不符");
    }

    /**
     * 重复请求测试
     */
    @Test
    public void duplicateTest() {
        String url = "http://www.wxp.com/aaabbbccc/dddeeefff/ggg";
        String shortUrl = shortUrlService.getShortUrl(url);
        Assert.hasLength(shortUrl, "生成短链为空");
        Assert.isTrue(shortUrl.length() <= 8, "短链长度过长：" + shortUrl.length());
        for (int i = 0; i < 10; i++) {
            String tmpShortUrl = shortUrlService.getShortUrl(url);
            Assert.isTrue(shortUrl.equals(tmpShortUrl), "重复生成的短链不一致");
        }

        for (int i = 0; i < 10; i++) {
            String originUrl = shortUrlService.getOriginUrl(shortUrl);
            Assert.isTrue(url.equals(originUrl), "根据短链获取的原始链接与原链接不符");
        }
    }

    /**
     * 多线程测试
     * 测试多线程情况下生成的短链是否有重复
     */
    @Test
    public void multiThreadTest() throws InterruptedException {
        Set<String> set = new HashSet<String>();
        final Set<String> shortUrlSet = Collections.synchronizedSet(set);
        final int perCount = 100000;
        final int threadCount = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int t = i;
            new Thread(new Runnable() {
                public void run() {
                    String url = "http://www.wxp.com/aaabbbccc/dddeeefff/ggghhhiii/jjjkkklll/" + t + "/";
                    for (int i = 0; i < perCount; i++) {
                        String tmpUrl = url + i;
                        shortUrlSet.add(shortUrlService.getShortUrl(tmpUrl));
                    }
                    countDownLatch.countDown();
                }
            }).start();
        }

        countDownLatch.await();

//        System.out.println(shortUrlSet.size());

        Assert.isTrue(shortUrlSet.size() == perCount * threadCount, "生成的短链存在重复。set size is: " + shortUrlSet.size());
    }

    /**
     * 性能测试（单线程）
     */
    @Test
    public void performanceTest() {
        long startTm = System.currentTimeMillis();
        String url = "http://www.wxp.com/aaabbbccc/dddeeefff/ggghhhiii/jjjkkklll";
        int count = 1000000;
        for (int i = 0; i < count; i++) {
            String tmpUrl = url + i;
            shortUrlService.getShortUrl(tmpUrl);
        }
        long endTm = System.currentTimeMillis();
        System.out.println("生成" + count + "个短链，耗时：" + (endTm - startTm));
    }

    @Test
    public void base62Test() {
        long num = 123456789L;
        String base62Num = Base62.encode(num);
        long originNum = Base62.decode(base62Num);

        Assert.isTrue(num == originNum, "Base62 转换错误");
    }
}
