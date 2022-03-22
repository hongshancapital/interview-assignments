package com.zjm.sdct_work;

import com.zjm.sdct_work.controller.Validator;
import com.zjm.sdct_work.controller.api.ShortcutPostReq;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author:   billzzzhang
 * Date:     2022/3/20 下午9:12
 * Desc:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidatorTest {

    @Autowired
    private Validator validator;

    @Test
    public void testValidateShorcutPath() {

        String right = "abcdegfh";

        String shortStr = "abcdegf";
        String wrongStr = "abcdegf";
        String longStr = "131313dada";

        Assert.assertTrue(validator.validateShortcutPath(right));
        Assert.assertFalse(validator.validateShortcutPath(wrongStr));
        Assert.assertFalse(validator.validateShortcutPath(shortStr));
        Assert.assertFalse(validator.validateShortcutPath(longStr));

    }

    @Test
    public void testValidateUrl() {

        String good1 = "http://www.baidu.com";
        String good2 = "https://www.baidu.com";

        String wrongStr1 = "www.baidu.com";
        String wrongStr2 = "baidu.com";
        String wrongStr3 = "http://www.baidu.com1";
        String wrongStr4 = "https://www.baidu.com1";

        Assert.assertTrue(validator.validateUrl(good1));
        Assert.assertTrue(validator.validateUrl(good2));
        Assert.assertFalse(validator.validateUrl(wrongStr1));
        Assert.assertFalse(validator.validateUrl(wrongStr2));
        Assert.assertFalse(validator.validateUrl(wrongStr3));
        Assert.assertFalse(validator.validateUrl(wrongStr4));

    }

    @Test
    public void testValidateToken() {

        String good = "abcdefgh";
        String wrong = "123455";

        Assert.assertTrue(validator.validateToken(good));
        Assert.assertFalse(validator.validateToken(wrong));

    }

    @Test
    public void testValidateReq() {

        String goodToken = "abcdefgh";
        String wrongToken = "123455";

        String goodUrl = "http://www.baidu.com";
        String wrongUrl = "www.baidu.com";

        ShortcutPostReq req1 = new ShortcutPostReq(goodUrl);
        ShortcutPostReq req2 = new ShortcutPostReq(goodUrl);
        ShortcutPostReq req3 = new ShortcutPostReq(wrongUrl);
        ShortcutPostReq req4 = new ShortcutPostReq(wrongUrl);


        Assert.assertTrue(validator.validateShortcutPostReq(req1));
        Assert.assertFalse(validator.validateShortcutPostReq(req2));
        Assert.assertFalse(validator.validateShortcutPostReq(req4));
        Assert.assertFalse(validator.validateShortcutPostReq(req3));

    }


}
