package com.domain.urlshortener.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @Description:
 * @author: rocky.hu
 * @date: 2022/4/6 3:09
 */
public class MessageUtilsTest {

    @Test
    public void test_1() {
       Assertions.assertEquals("A", MessageUtils.get("A"));
    }

    @Test
    public void test_2() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("static/i18n/messages");
        source.setUseCodeAsDefaultMessage(true);

        new MessageUtils(source);
        Assertions.assertEquals("url参数不允许为空", MessageUtils.get("url.notBlank"));
    }

}
