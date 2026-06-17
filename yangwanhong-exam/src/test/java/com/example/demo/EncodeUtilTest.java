package com.example.demo;

import com.example.demo.util.EncodeUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EncodeUtilTest {

    @Test
    public void getShortUrlByLongNumTest() {
        String url = EncodeUtil.getShortUrlByLongNum(0L);
        Assert.assertTrue("转换错误",!StringUtils.isEmpty(url));

        url = EncodeUtil.getShortUrlByLongNum(1L);
        Assert.assertTrue("转换错误",!StringUtils.isEmpty(url));
    }

    @Test
    public void getLongNumByShortUrlTest() {
        String url = EncodeUtil.getShortUrlByLongNum(101L);

        Long number = EncodeUtil.getLongNumByShortUrl(url);

        Assert.assertTrue("转换错误",number.longValue() == 101L);
    }

    @Test
    public void valueOfCharacterTest() {
        Character c = 'c';

        int number = EncodeUtil.valueOfCharacter(c);

        Assert.assertTrue("转换错误",number != -1);

        c = '-';

        number = EncodeUtil.valueOfCharacter(c);

        Assert.assertTrue("转换错误",number == -1);
    }

}
