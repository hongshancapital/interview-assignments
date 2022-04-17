package com.yilong.shorturl.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class UrlShorterTest {

    @Test
    public void testEncoderUrlNull() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                UrlShorter.encodeUrl(null);
            }
        });
    }

    @Test
    public void testEncoderUrlEmpty() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                UrlShorter.encodeUrl("");
            }
        });
    }

    @Test
    public void testEncoderUrl() {
        Assertions.assertEquals("1Evr03", UrlShorter.encodeUrl("https://www.vampire.com/url/looooooong/1"));
    }
}
