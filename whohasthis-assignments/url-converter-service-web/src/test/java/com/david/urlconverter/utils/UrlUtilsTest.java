package com.david.urlconverter.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlUtilsTest {

    private String validShortUrl = "fcW=====";

    private String invalidShortUrl1 = "fcW=35==";

    private String invalidShortUrl2 = "   ";

    private String invalidShortUrl3 = "123456789";

    private String validLongUrl = "https://www.baidu.com/efdefts";

    private String invalidLongUrl1 = "http://172.2232.284.4782:2300/38idl83?38fs=0909";

    private String invalidLongUrl2 = invalidShortUrl2;

    private String padUrl = "233";

    @Test
    public void testIsValidShortUrl(){
        assertThat(UrlUtils.isValidShortUrl(validShortUrl)).isEqualTo(true);
        assertThat(UrlUtils.isValidShortUrl(invalidShortUrl1)).isEqualTo(false);
        assertThat(UrlUtils.isValidShortUrl(invalidShortUrl2)).isEqualTo(false);
        assertThat(UrlUtils.isValidShortUrl(invalidShortUrl3)).isEqualTo(false);
    }

    @Test
    public void testIsValidLongUrl(){
        assertThat(UrlUtils.isValidLongUrl(validLongUrl)).isEqualTo(true);
        assertThat(UrlUtils.isValidLongUrl(invalidLongUrl1)).isEqualTo(false);
        assertThat(UrlUtils.isValidLongUrl(invalidLongUrl2)).isEqualTo(false);
    }

    @Test
    public void testSetPlaceHolder(){
        assertThat(UrlUtils.setPlaceHolder(padUrl)).isEqualTo("233=====");
    }

    @Test
    public void testConstructor(){
        System.out.println(new UrlUtils());
    }
}
