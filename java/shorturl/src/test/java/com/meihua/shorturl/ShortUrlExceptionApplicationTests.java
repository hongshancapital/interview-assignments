package com.meihua.shorturl;

import com.meihua.shorturl.cmdb.impl.IDGeneratorShortUrlHandler;
import com.meihua.shorturl.controller.IDShortUrlController;
import com.meihua.shorturl.controller.Md5ShortUrlController;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.UUID;

/**
 * @author meihua
 * @version 1.0
 * @date 2021/10/12 10:42
 */
@SpringBootTest
public class ShortUrlExceptionApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(ShortUrlApplicationTests.class);

    private final static int TASK_COUNT = 1000000;

    @Autowired
    private IDShortUrlController idShortUrlController;

    @Autowired
    private Md5ShortUrlController md5ShortUrlController;
    @Test
    public void exceptionTest(){
        //空数据测试
        md5ShortUrlController.toShortUrl(null);
        md5ShortUrlController.getUrl(null);
        idShortUrlController.toShortUrl(null);
        idShortUrlController.getUrl(null);
        md5ShortUrlController.toShortUrl("");
        md5ShortUrlController.getUrl("");
        idShortUrlController.toShortUrl("");
        idShortUrlController.getUrl("");
        //未命中测试
        for (int i=0;i<TASK_COUNT;i++){
            StringBuilder url = new StringBuilder("https://www.555.com?time=");
            url.append(UUID.randomUUID());
            Assert.isTrue(StringUtils.isBlank(md5ShortUrlController.getUrl(url.toString()).getData()),"预期为空！");
            Assert.isTrue(StringUtils.isBlank(idShortUrlController.getUrl(url.toString()).getData()),"预期为空！");
        }

        //最大数 溢出测试
        for (int i=0;i<TASK_COUNT;i++){
            StringBuilder url = new StringBuilder("https://www.555.com?time=");
            url.append(UUID.randomUUID());
            try {
                idShortUrlController.toShortUrl(url.toString());
            }catch (RuntimeException e){
                e.printStackTrace();
                break;
            }
        }
        //位数溢出 测试
        IDGeneratorShortUrlHandler.limit=0L;
        try {
            idShortUrlController.toShortUrl(UUID.randomUUID().toString());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        logger.info("exceptionTest done !");
    }

}
