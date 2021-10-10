package com.scdt.interview.url;

import com.scdt.interview.url.service.UrlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlServiceTests {

    @Autowired
    private UrlService urlService;

    private static String validLongUrl;

    private static String invalidLongUrl;

    @BeforeAll
    public static void before(){
        validLongUrl = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=baidu&wd=%E7%BA%A2%E6%9D%89%E8%B5%84%E6%9C%AC&oq=%25E7%259F%25AD%25E7%25BD%2591%25E5%259D%2580%25E7%2594%259F%25E6%2588%2590%25E7%25AE%2597%25E6%25B3%2595&rsv_pq=9077fa2f00015d27&rsv_t=2ad8Qao6agxjVUy53Ko1CxFL8DnUNOf92%2Bvguo0VOUCmKlKYU5XFE4IYOjM&rqlang=cn&rsv_enter=1&rsv_dl=tb&rsv_sug3=9&rsv_sug1=15&rsv_sug7=100&bs=%E7%9F%AD%E7%BD%91%E5%9D%80%E7%94%9F%E6%88%90%E7%AE%97%E6%B3%95";
    }




    @Test
    void getShortUrlWithNotNullLongUrl() {
        String shortUrl = urlService.storeLongUrl(validLongUrl);

        Assertions.assertNotEquals("", shortUrl);
    }

    @Test
    void getShotUrlWithNullLongUrl() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            urlService.storeLongUrl(invalidLongUrl);
        });
    }


    @Test
    void getLongUrlWithNotNullShortUrl() {
        String longUrl = urlService.getLongUrl("http://dwz.com/g8");

        Assertions.assertNotEquals(validLongUrl, longUrl);
    }

    @Test
    void getLongUrlWithNullShotUrl() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            urlService.getLongUrl(invalidLongUrl);
        });
    }


}
