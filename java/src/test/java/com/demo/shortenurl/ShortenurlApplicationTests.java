package com.demo.shortenurl;

import com.demo.shortenurl.service.impl.ShortenUrlServiceImpl;
import com.demo.shortenurl.util.Utils;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * 单元测试类
 * 包含对读取接口和存储接口的参数校验，结果校验，唯一性校验等测试用例
 * 由于时间有限，本题只是有限的构造边界数据，没有写mock类和mock数据，后续可以考虑扩展
 */
@SpringBootTest
class ShortenurlApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 长URL参数合法性校验
     * 合法性：1. 长URL必须长度不超过配置的最大值 2. 长URL不为空
     * 测试结果：参数非法时，转换到的短url为空值，否则不为空
     */
    @Test
    void getShortenUrlParamsTest() {
        int maxLengthOfOriginalUrl = 10;
        int maxNumberOfShortenUrls = 10;
        ShortenUrlServiceImpl service = createShortenUrlService(maxLengthOfOriginalUrl, maxNumberOfShortenUrls);
        String result1 = service.getShortenUrl(Utils.EMPTY_STRING);
        String result2 = service.getOriginalUrl(null);
        String validOriginalUrl = createStringByFixLength('*', maxLengthOfOriginalUrl);
        String result3 = service.getShortenUrl(validOriginalUrl);
        String originalUrlOverLimit = createStringByFixLength('*', maxLengthOfOriginalUrl + 1);
        String result4 = service.getShortenUrl(originalUrlOverLimit);
        Assert.assertTrue(result1.isEmpty());
        Assert.assertTrue(result2.isEmpty());
        Assert.assertTrue(!result3.isEmpty());
        Assert.assertTrue(result4.isEmpty());
    }

    /**
     * 长URL->短URL唯一性用例
     * 测试结果：长URL参数相同时，获得的短URL一定相同。长URL不同，获得的短URL一定不同
     */
    @Test
    void getShortenUrlResultUniquenessTest() {
        int maxLengthOfOriginalUrl = 10;
        int maxNumberOfShortenUrls = 10;
        ShortenUrlServiceImpl service = createShortenUrlService(maxLengthOfOriginalUrl, maxNumberOfShortenUrls);
        String duplicateOriginalUrl = createStringByFixLength('*', maxLengthOfOriginalUrl);
        String result = service.getShortenUrl(duplicateOriginalUrl);
        //长URL参数相同，获得短URL相同
        for (int i = 0; i < maxNumberOfShortenUrls - 1; i++) {
            String tempResult = service.getShortenUrl(duplicateOriginalUrl);
            Assert.assertEquals(result, tempResult);
        }

        //长URL不同，获得的短URL一定不同
        Set<String> results = new HashSet<String>();
        results.add(result);
        for (int i = 0; i < maxNumberOfShortenUrls - 1; i++) {
            String temp = createStringByFixLength((char)(i + '0'), maxLengthOfOriginalUrl);
            String tempResult = service.getShortenUrl(temp);
            Assert.assertTrue(!results.contains(tempResult));
            results.add(tempResult);
        }
    }

    /**
     * 长URL->短URL结果合法性用例
     * 测试结果：获得的短URL一定长度不超过8，由字母和数字组成。当长URL请求数量超过指定值后，返回默认值“ZZZZZZZZ”
     */
    @Test
    void getShortenUrlResultCheckTest() {
        int maxLengthOfOriginalUrl = 100;
        int maxNumberOfShortenUrls = 10;
        ShortenUrlServiceImpl service = createShortenUrlService(maxLengthOfOriginalUrl, maxNumberOfShortenUrls);
        //获得的短URL一定长度不超过8，由字母和数字组成
        for (int i = 1; i < maxNumberOfShortenUrls + 1; i++) {
            String temp = createStringByFixLength('*', i);
            String tempResult = service.getShortenUrl(temp);
            Assert.assertTrue(Utils.isShortenUrlValid(tempResult));
        }

        // 当长URL请求数量超过指定值后，返回默认值“ZZZZZZZZ”
        for (int i = maxNumberOfShortenUrls + 1; i < maxLengthOfOriginalUrl; i++) {
            String temp = createStringByFixLength('*', i);
            String tempResult = service.getShortenUrl(temp);
            Assert.assertEquals(tempResult, Utils.DEFAULT_ERROR_SHORTENURL);
        }
    }

    /**
     * 长URL->短URL 并发性测试
     * junit不支持多线程，作为一个保留测试用例吧
     * 测试：启动多个线程发动长URL->短URL请求，其结果合法性，参数合法性，结果唯一性都能保证
     */
    @Test
    void getShortenUrlInMultiThreads() {

    }


    /**
     * 内存溢出测试
     * 运维可以在application.properties 配置maxLengthOfOriginalUrl和maxNumberOfShortenUrls，
     * 并且可以跑这个用例测试自己配置的值是否会导致OOM
     * maxLengthOfOriginalUrl: 长URL的最大长度
     * maxNumberOfShortenUrls：最多可以提供多少短URL
     */
    @Test
    void getShortenUrlOomTest() {
        int maxLengthOfOriginalUrl = 10; //需要手动将值改为实际配置的url最大长度
        int maxNumberOfShortenUrls = 100; //需要手动将值改为是自己配置的最大short url数量
        ShortenUrlServiceImpl service = createShortenUrlService(maxLengthOfOriginalUrl, maxNumberOfShortenUrls);
        Random random = new Random();
        for (int i = 0; i < maxNumberOfShortenUrls; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < maxLengthOfOriginalUrl; j++) {
                sb.append(random.nextInt(10));
            }

            service.getShortenUrl(sb.toString());
        }
    }


    /**
     * 短url->长url参数合法性测试
     * 合法性：短url长度不能超过8， 短URL不能包含特殊字符, 短URL不能为空
     */
    @Test
    void getOriginalUrlParamTest() {
        int maxLengthOfOriginalUrl = 10;
        int maxNumberOfShortenUrls = 10;
        ShortenUrlServiceImpl service = createShortenUrlService(maxLengthOfOriginalUrl, maxNumberOfShortenUrls);
        String shortenUrlOverLimit = createStringByFixLength('1', 9);
        String ilegalShortenUrl = createStringByFixLength('*', 8);
        String emptyString = Utils.EMPTY_STRING;
        String result1 = service.getOriginalUrl(shortenUrlOverLimit);
        String result2 = service.getOriginalUrl(ilegalShortenUrl);
        String result3 = service.getOriginalUrl(emptyString);
        Assert.assertTrue(result1.isEmpty());
        Assert.assertTrue(result2.isEmpty());
        Assert.assertTrue(result3.isEmpty());
    }

    /**
     * 短url->长url结果唯一性
     * 测试：发出一定数量后的长URL-》短URL请求后，使用短URL-》长URL请求进行长URL比较，得到的和原始值都必须相同
     */
    @Test
    void getOriginalUrlUniquenessTest() {
        int maxLengthOfOriginalUrl = 10; //需要手动将值改为实际配置的url最大长度
        int maxNumberOfShortenUrls = 10; //需要手动将值改为是自己配置的最大short url数量
        ShortenUrlServiceImpl service = createShortenUrlService(maxLengthOfOriginalUrl, maxNumberOfShortenUrls);
        Random random = new Random();
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < maxNumberOfShortenUrls; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < maxLengthOfOriginalUrl; j++) {
                sb.append(random.nextInt(10));
            }

            String result = service.getShortenUrl(sb.toString());
            map.put(result, sb.toString());
        }

        for (String shortenUrl : map.keySet()) {
            String result = service.getOriginalUrl(shortenUrl);
            Assert.assertEquals(result, map.get(shortenUrl));
        }
    }

    /**
     * 短url->长url结果合法性测试
     * 合法要求；1. 当前短URL为zzzzzzzz, 得到的长url应当为空 2. 查询一个不存在的短URL，得到的长URL应当为空
     */
    @Test
    void getOriginalUrlResultTest() {
        int maxLengthOfOriginalUrl = 10; //需要手动将值改为实际配置的url最大长度
        int maxNumberOfShortenUrls = 10; //需要手动将值改为是自己配置的最大short url数量
        ShortenUrlServiceImpl service = createShortenUrlService(maxLengthOfOriginalUrl, maxNumberOfShortenUrls);
        Random random = new Random();
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < maxNumberOfShortenUrls; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < maxLengthOfOriginalUrl; j++) {
                sb.append(random.nextInt(10));
            }

            service.getShortenUrl(sb.toString());
            set.add(sb.toString());
        }

        String result1 = service.getOriginalUrl(Utils.DEFAULT_ERROR_SHORTENURL);
        Assert.assertTrue(result1.isEmpty());

        for (int i = 0; i < maxNumberOfShortenUrls; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < Utils.SHORTENURL_MAXLENGTH; j++) {
                sb.append((char)(random.nextInt(10) + '0'));
            }

            if (set.contains(sb.toString())) {
                 continue;
            }

            String result = service.getOriginalUrl(sb.toString());
            Assert.assertTrue(result.isEmpty());
        }
    }


    /**
     * 短url->长url 并发性测试
     * junit不支持多线程，作为一个保留测试用例吧
     */
    @Test
    void getOriginalUrlInMultiThreadTest() {
    }

    private static ShortenUrlServiceImpl createShortenUrlService(int maxLengthOfOriginalUrl, int maxNumberOfShortenUrls) {
        ShortenUrlServiceImpl service = new ShortenUrlServiceImpl();
        service.setMaxNumberOfShortenUrls(maxNumberOfShortenUrls);
        service.setMaxLengthOfOriginalUrl(maxLengthOfOriginalUrl);
        return service;
    }

    private static String createStringByFixLength(char c, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(c);
        }

        return sb.toString();
    }

}
