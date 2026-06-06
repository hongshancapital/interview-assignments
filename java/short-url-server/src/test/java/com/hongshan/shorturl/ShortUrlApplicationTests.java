package com.hongshan.shorturl;

import com.hongshan.shorturl.domain.dto.ShortUrlGenerateDTO;
import com.hongshan.shorturl.domain.exception.NotFoundException;
import com.hongshan.shorturl.domain.model.ShortUrlModel;
import com.hongshan.shorturl.service.ShortUrlService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: huachengqiang
 * @date: 2022/3/20
 * @description:
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortUrlApplicationTests {

    @Autowired
    private ShortUrlService service;

    @Test
    public void test() {
        Assert.assertThrows(NotFoundException.class, () -> service.getShorUrl(""));
    }

    /**
     * 测试生成和获取的是不是相同的短链接
     *
     * @param
     * @return
     * @throws
     * @date 2022/3/20
     * @author huachengqiang
     */
    @Test
    public void generateTests() throws InterruptedException {
        String originUrl = "https://zhuanlan.zhihu.com/p/94000190";
        Long expireAt = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10);
        ShortUrlGenerateDTO dto = new ShortUrlGenerateDTO();
        dto.setOriginUrl(originUrl);
        dto.setExpireAt(expireAt);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ShortUrlGenerateDTO>> messages = validator.validate(dto);
        Assert.assertTrue("invalid url request", CollectionUtils.isEmpty(messages));

        ShortUrlModel shortUrlModel = service.createShortUrl(dto);
        String shortUrl = shortUrlModel.getShortUrl();
        ShortUrlModel shorUrl = service.getShorUrl(shortUrl.substring(shortUrl.lastIndexOf("/") + 1));
        Assert.assertEquals(shortUrlModel, shorUrl);
    }

    /**
     * 测试半小时内是不是生成相同的短链接
     *
     * @param
     * @return
     * @throws
     * @date 2022/3/20
     * @author huachengqiang
     */
    @Test
    public void hashTests() throws InterruptedException {
        String originUrl = "https://zhuanlan.zhihu.com/p/94000190";
        Long expireAt = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10);
        ShortUrlGenerateDTO dto = new ShortUrlGenerateDTO();
        dto.setOriginUrl(originUrl);
        dto.setExpireAt(expireAt);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ShortUrlGenerateDTO>> messages = validator.validate(dto);
        Assert.assertTrue("invalid url request", CollectionUtils.isEmpty(messages));

        ShortUrlModel mode1 = service.createShortUrl(dto);
        Thread.sleep(TimeUnit.SECONDS.toMillis(10));
        ShortUrlModel mode2 = service.createShortUrl(dto);
        Assert.assertEquals(mode1, mode2);
    }
}
