package indi.zixiu.shortdomainname.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DomainNameTest {
    @Test
    void testIsValidDomainName_givenValidDomainName_thenTrue() {
        String[] domainNames = {
                "example.com",  // 常规域名
                "example-server.com",  // 含 '-' 的域名
                "2.example.com",  // 含数字 的域名
                "a.ab",  // 子域名长度最短（1 位）
                "0123456789-ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.cn",  // 子域名长度最长（63 位）
                "a.abcdef",  // 最长的顶级域名（6 位）
        };

        for (String domainName : domainNames) {
            Assertions.assertTrue(DomainName.isValidDomainName(domainName), domainName + " 域名合法，却被判为非法");
        }
    }

    @Test
    void testIsValidDomainName_givenInvalidDomainName_thenFalse() {
        String[] domainNames = {
                null,
                "example+.com",  // 包含非法字符 '+'
                "a..cn",  // 二级域名长度为 0，小于允许的最小长度 1
                "0123456789-ABCDEFGHIJKLMNOPQRSTUVWXYZ-abcdefghijklmnopqrstuvwxyz.cn",  // 二级域名长度为 64，大于允许的最大长度 63
                "-a.cn",  // 二级域名以 '-' 开始
                "a-.cn",  // 二级域名以 '-' 结束
                "a.c",  // 顶级域名长度为 1，小于允许的最小长度 2
                "a.abcdefg",  // 顶级域名长度为 7，大于允许的最大长度 6
                "cn",  // 域名只有 1 级
        };

        for (String domainName : domainNames) {
            Assertions.assertFalse(DomainName.isValidDomainName(domainName), domainName + " 域名非法，却被判为合法");
        }
    }

    @Test
    public void testConvertShortNameInLongToInBase62_givenIllegalArgument_thenException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> DomainName.convertShortNameInLongToInBase62(-1));
    }

    @Test
    public void testConvertShortNameInLongToInBase62_normalCase() {
        long[] shortNamesInLong = {0, 1, 10, 36, 61, 62, 63, 3844};
        String[] expectedShortNamesInBase62 = {"0", "1", "A", "a", "z", "10", "11", "100"};
        for (int i = 0; i < shortNamesInLong.length; i++) {
            long shortNameInLong = shortNamesInLong[i];
            String expectedShortNameInBase62 = expectedShortNamesInBase62[i];
            String actualShortNameInBase62 = DomainName.convertShortNameInLongToInBase62(shortNameInLong);
            Assertions.assertTrue(expectedShortNameInBase62.equals(actualShortNameInBase62), "shortNameInLong = " + shortNameInLong);
        }
    }

    @Test
    public void testConvertShortNameInBase62ToInLong_givenIllegalArgument_thenException() {
        String[] shortNamesInBase62 = {null, "", "123456789", "1+2"};
        for (String shortNameInBase62 : shortNamesInBase62) {
            Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> DomainName.convertShortNameInBase62ToInLong(shortNameInBase62),
                    "对于 62 进制数 " + shortNameInBase62 + " 应该抛异常"
            );
        }
    }

    @Test
    public void testConvertShortNameInBase62ToInLong_normalCase() {
        String[] shortNamesInBase62 = {"0", "1", "A", "a", "z", "10", "11", "100"};
        long[] expectedShortNamesInLong = {0, 1, 10, 36, 61, 62, 63, 3844};
        for (int i = 0; i < shortNamesInBase62.length; i++) {
            String shortNameInBase62 = shortNamesInBase62[i];
            long expectedShortNameInLong = expectedShortNamesInLong[i];
            long actualShortNameInLong = DomainName.convertShortNameInBase62ToInLong(shortNameInBase62);
            Assertions.assertEquals(expectedShortNameInLong, actualShortNameInLong, "shortNameInBase62 = " + shortNameInBase62);
        }
    }

}
