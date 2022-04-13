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
public class ReadPerformanceTest {

    @Autowired
    private UrlConvertorController urlConvertorController;

    private static final List<String> writeCaseUrlList = new ArrayList<>(1 << 10);
    private static final List<String> writeCaseUrlList2 = new ArrayList<>(1 << 15);
    private static final List<String> writeCaseUrlList3 = new ArrayList<>(1 << 20);
    private static final List<String> readCaseUrlList = new ArrayList<>(1 << 10);
    private static final List<String> readCaseUrlList2 = new ArrayList<>(1 << 15);
    private static final List<String> readCaseUrlList3 = new ArrayList<>(1 << 20);
    private static final String BASE_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final List<String> shortUrlList = new ArrayList<>(1 << 10);
    private static final List<String> shortUrlList2 = new ArrayList<>(1 << 15);
    private static final List<String> shortUrlList3 = new ArrayList<>(1 << 20);

    @BeforeAll
    public static void init() {
        for (int i = 0; i < 1 << 10; i++) {
            // 生成随机url
            writeCaseUrlList.add(RandomUtil.randomString(BASE_STR, 32));

            readCaseUrlList.add(RandomUtil.randomString(BASE_STR, 6));
        }
        for (int i = 0; i < 1 << 15; i++) {
            // 生成随机url
            writeCaseUrlList2.add(RandomUtil.randomString(BASE_STR, 32));

            readCaseUrlList2.add(RandomUtil.randomString(BASE_STR, 6));
        }
        for (int i = 0; i < 1 << 20; i++) {
            // 生成随机url
            writeCaseUrlList3.add(RandomUtil.randomString(BASE_STR, 32));

            readCaseUrlList3.add(RandomUtil.randomString(BASE_STR, 6));
        }
    }

    @Test
    public void getLongUrlByShortUrl() {
        // 读存在测试
        writeCaseUrlList.forEach(url -> {
            ApiResult<String> apiResult = urlConvertorController.longUrlToShortUrl(url);
            shortUrlList.add(apiResult.getData());
        });
        writeCaseUrlList2.forEach(url -> {
            ApiResult<String> apiResult = urlConvertorController.longUrlToShortUrl(url);
            shortUrlList2.add(apiResult.getData());
        });
        writeCaseUrlList3.forEach(url -> {
            ApiResult<String> apiResult = urlConvertorController.longUrlToShortUrl(url);
            shortUrlList3.add(apiResult.getData());
        });


        long case1Begin = System.nanoTime();
        for (String url : shortUrlList) {
            urlConvertorController.getLongUrlByShortUrl(url);
        }
        System.out.println("读存在测试: 1 << 10 次耗时:  " + (System.nanoTime() - case1Begin));

        long case2Begin = System.nanoTime();
        for (String url : shortUrlList2) {
            urlConvertorController.getLongUrlByShortUrl(url);
        }
        System.out.println("读存在测试: 1 << 15 次耗时:  " + (System.nanoTime() - case2Begin));

        long case3Begin = System.nanoTime();
        for (String url : shortUrlList3) {
            urlConvertorController.getLongUrlByShortUrl(url);
        }
        System.out.println("读存在测试: 1 << 20 次耗时:  " + (System.nanoTime() - case3Begin));
    }


}
