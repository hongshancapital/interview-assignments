package com.hongshan.shorturl.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author: huachengqiang
 * @date: 2022/3/20
 * @description:
 * @version: 1.0
 */
public class UtilTests {

    @Test
    public void longConvertCheck() {
        long[] values = {0, 63, 62 * 62 + 61, 1000000L};
        String[] str = {"L", "ee", "eLZ", "7X4f"};
        for (int i = 0; i < values.length; i++) {
            Assert.assertEquals(LongConvertUtil.convertToStr(values[i]), str[i]);
        }
    }

    @Test
    public void md5Check() {
        String[] str1 = {"https://zhuanlan.zhihu.com/p/94000190", "a", "ab", "A"};
        String[] copy = {"https://zhuanlan.zhihu.com/p/94000190", "a", "ab", "A"};
        String[] str2 = {"https://zhuanlan.zhihu.com/p/9400019", "ab", "cd", "a"};
        for (int i = 0; i < str1.length; i++) {
            String md5ToBase64 = MD5Util.md5ToBase64(str1[i]);
            Assert.assertEquals(md5ToBase64, MD5Util.md5ToBase64(copy[i]));
            Assert.assertNotEquals(md5ToBase64, MD5Util.md5ToBase64(str2[i]));
        }
    }
}
