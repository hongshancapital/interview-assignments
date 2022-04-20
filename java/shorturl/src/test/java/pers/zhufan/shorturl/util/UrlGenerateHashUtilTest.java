package pers.zhufan.shorturl.util;

import org.junit.Assert;
import org.junit.Test;

public class UrlGenerateHashUtilTest {

    @Test
    public void generate() {

        String key = "md5hashkey";

        int lenth = 8;

        String longUrl = "http://www.baidu.cam/api/test/query/09846754834455";

        String generate = UrlGenerateHashUtil.generate(key, lenth, longUrl);

        //Assert.assertEquals("InmUraaa",generate);

        Assert.assertTrue(lenth == generate.length());

    }

    @Test
    public void md5ByHex() {

        String longUrl = "http://www.baidu.cam/api/test/query/09846754834455";

        String s = UrlGenerateHashUtil.md5ByHex(longUrl);

        Assert.assertEquals("EEEF00880272B8DF8497E6F4FBE98BF5",s);
    }
}