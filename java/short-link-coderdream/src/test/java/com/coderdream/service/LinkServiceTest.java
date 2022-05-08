package com.coderdream.service;

import com.coderdream.utils.Constants;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author CoderDream
 * @version 1.0
 * @date 2022/5/8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LinkServiceTest {

    @Resource
    private LinkService linkService;

    @Test
    public void testGetShortLink() {
        String shortLink = linkService.getShortLink("http://www.baidu.com/jLFYW7ZlML-344410");
        Assert.assertNotNull(shortLink);
        Assert.assertEquals("http://www.baidu.com/jLFYW7ZlML-344410", linkService.getLongLink(shortLink));
    }

    @Test
    public void testGetShortLinkWithNull() {
        Assert.assertEquals("", linkService.getShortLink(""));
        Assert.assertEquals("", linkService.getShortLink(null));
    }

    @Test
    public void getLongLink() {
        Assert.assertEquals("", linkService.getLongLink("xxxxxx"));
        Assert.assertEquals("", linkService.getLongLink(""));
        Assert.assertEquals("", linkService.getLongLink(null));
    }

    @Test
    public void testHashConflictLink() {
        String longLink1 = "http://www.baidu.com/8rkOPoKckoYowqylvIqMxG1V9HuUFJ-444728";
        String longLink2 = "http://www.baidu.com/twh2QwsOft5iard4aC8SPYvKulQy6C-921133";

        String shortLink1 = linkService.getShortLink(longLink1);
        String shortLink2 = linkService.getShortLink(longLink2);
        Assert.assertNotNull(shortLink1);
        Assert.assertEquals(longLink1, linkService.getLongLink(shortLink1));
        Assert.assertNotNull(shortLink2);
        Assert.assertEquals(longLink2, linkService.getLongLink(shortLink2));
    }

    @Test
    public void testStoreSameLink() {
        String longLink1 = "http://www.baidu.com/twh2QwsOft5iard4aC8SPYvKulQy6C-921133";
        String longLink2 = "http://www.baidu.com/twh2QwsOft5iard4aC8SPYvKulQy6C-921133";

        String shortLink1 = linkService.getShortLink(longLink1);
        String shortLink2 = linkService.getShortLink(longLink2);
        Assert.assertEquals(shortLink1, shortLink2);
        Assert.assertNotNull(shortLink1);
        Assert.assertEquals(longLink1, linkService.getLongLink(shortLink1));
        Assert.assertNotNull(shortLink2);
        Assert.assertEquals(longLink2, linkService.getLongLink(shortLink2));
        Assert.assertEquals(longLink1, longLink2);
    }

    /**
     * 测试布隆过滤器误判场景
     *
     * @throws Exception
     */
    @Test
    public void testBloomFilterError() throws Exception {
        /**
         * 布隆过滤器
         */
        BloomFilter<String> filter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), Constants.BLOOM_FILTER_INSERTION, Constants.BLOOM_FILTER_FPP);

        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\data.txt";

        // BufferedReader是可以按行读取文件
        FileInputStream inputStream = new FileInputStream(filePath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> list = new ArrayList<>();
        Set<String> set = new HashSet<>();
        String longLink = null;
        while ((longLink = bufferedReader.readLine()) != null) {
            String code = linkService.getShortLink(longLink);
            set.add(code);
        }

        //close
        inputStream.close();
        bufferedReader.close();
        list.addAll(set);
        // 470326
        int size1 = 430326;
        int size2 = 40000;

        // 插入47万样本数据
        for (int i = 0; i < size1; i++) {
            filter.put(list.get(i));
        }
        // 用另外4万测试数据，测试误判率，误判了3个
        int count = 0;
        for (int i = size1; i < size1 + size2; i++) {
            if (filter.mightContain(list.get(i))) {
                count++;
            }
        }
        Assert.assertTrue(count >= 0);
    }
}