package com.sequcap.shorturl.service;

import com.sequcap.shorturl.web.exception.BusinessException;
import com.sequcap.shorturl.web.model.UrlModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShortUrlConvertServiceTests {

    @Autowired
    private ShortUrlConvertService convertService;

    @Autowired
    private UrlManagementService urlManagementService;

    @Test
    void testLong2Short() {
        UrlModel urlModel = convertService.long2Short("https://blog.csdn.net/zhanggonglalala/article/details/89083622");
        Assertions.assertNotNull(urlModel);
        Assertions.assertEquals("http://sequcap.cn/a3b379b7", urlModel.getShortUrl());
    }

    /**
     * 这里模拟murmurHashCode重复的case
     */
//    @Test
    void testLong2ShortRepeatHashCode() {
        String longUrl = "https://blog.csdn.net/zhanggonglalala/article/details/776566765";
        // 1. 新增一个longUrl
        UrlModel urlModel = convertService.long2Short(longUrl);
        Assertions.assertNotNull(urlModel);
        Assertions.assertEquals("http://sequcap.cn/28bba8fb", urlModel.getShortUrl());

        // 2. 手动修改缓存中的longUrl
        String murmurCode = urlModel.getShortUrl().substring(urlModel.getShortUrl().lastIndexOf("/") +1);
        urlManagementService.buildUrlModel(murmurCode, longUrl+"updatedAAA");

        // 3. 再次传入longUrl，尝试生成shortUrl
        UrlModel newUrlModel = convertService.long2Short(longUrl);
        Assertions.assertNotNull(newUrlModel);
        Assertions.assertEquals("http://sequcap.cn/6d39104b", newUrlModel.getShortUrl());
    }

    @Test
    void testGetLongUrlByShort() {
        UrlModel urlModel = convertService.long2Short("https://blog.csdn.net/zhanggonglalala/article/details/6778755448");
        Assertions.assertNotNull(urlModel);
        Assertions.assertEquals("http://sequcap.cn/ca219c69", urlModel.getShortUrl());

        urlModel = convertService.getLongUrlByShort("http://sequcap.cn/ca219c69");
        Assertions.assertNotNull(urlModel);
        Assertions.assertEquals("https://blog.csdn.net/zhanggonglalala/article/details/6778755448", urlModel.getLongUrl());
    }

    @Test
    void testGetLongUrlByShortWithNullResult() {
        UrlModel urlModel = convertService.getLongUrlByShort("http://sequcap.cn/12345678");
        Assertions.assertNull(urlModel);
    }

    @Test
    void testGetLongUrlByShortWithErrorUrl() {
        try {
            UrlModel urlModel = convertService.getLongUrlByShort("111233aab.ccom.cn.12345678");
        } catch (BusinessException e) {
            System.out.println("The shortUrl format is not correct");
        }
    }

}
