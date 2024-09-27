package com.sequoia.shorturl;

import cn.hutool.core.util.RandomUtil;
import com.sequoia.shorturl.common.ApiResult;
import com.sequoia.shorturl.web.controller.UrlConvertorController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class WritePerformanceTest {

    @Autowired
    private UrlConvertorController urlConvertorController;

    private static final String BASE_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final List<String> writeCaseUrlList = new ArrayList<>(1 << 10);
    private static final List<String> writeCaseUrlList2 = new ArrayList<>(1 << 15);
    private static final List<String> writeCaseUrlList3 = new ArrayList<>(1 << 20);

    @BeforeAll
    public static void init() {
        for (int i = 0; i < 1 << 10; i++) {
            // 生成随机url
            writeCaseUrlList.add(RandomUtil.randomString(BASE_STR, 32));
        }
        for (int i = 0; i < 1 << 15; i++) {
            // 生成随机url
            writeCaseUrlList2.add(RandomUtil.randomString(BASE_STR, 32));
        }
        for (int i = 0; i < 1 << 20; i++) {
            // 生成随机url
            writeCaseUrlList3.add(RandomUtil.randomString(BASE_STR, 32));
        }
    }

    @Test
    public void longUrlToShort() {

        long case1Begin = System.nanoTime();
        writeCaseUrlList.forEach(url -> urlConvertorController.longUrlToShortUrl(url));
        System.out.println("写测试: 1 << 10次耗时:" + (System.nanoTime() - case1Begin));

        long case2Begin = System.nanoTime();
        writeCaseUrlList2.forEach(url -> urlConvertorController.longUrlToShortUrl(url));
        System.out.println("写测试: 1 << 15次耗时:" + (System.nanoTime() - case2Begin));

        long case3Begin = System.nanoTime();
        writeCaseUrlList3.forEach(url -> urlConvertorController.longUrlToShortUrl(url));
        System.out.println("写测试: 1 << 20次耗时:" + (System.nanoTime() - case3Begin));
    }

}
