package com.interview.wph.shorturl;

import com.interview.wph.shorturl.common.utils.EncryptionUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wangpenghao
 * @date 2022/4/24 17:16
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class GenerateIdTest {
    private static Long Id = null;
    private static String shortUrl = null;

    /**
     * 生成Id
     */
    @Order(1)
    @Test
    public void testGenerateId() {
        Long id = EncryptionUtil.generateId();
        assertTrue(id >= 0 && id < Math.pow(62, 8));
        Id = id;
    }

    @Order(2)
    @Test
    public void testIdToStr() {
        String str = EncryptionUtil.to62RadixString(Id);
        assertTrue(str.length() <= 8);
        shortUrl = str;
    }

    @Order(3)
    @Test
    public void testStrToStr() {
        long l = EncryptionUtil.decimalConvertToNumber(shortUrl);
        assertEquals(l, Id);
    }


}
