package qyboot.controller;

import com.qyboot.utils.Base62UrlShorter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {WebApplicationContext.class})
@ComponentScan(basePackages = "com.qyboot")
@RunWith(SpringRunner.class)
public class Base62UrlShorterTest {

    private Base62UrlShorter base62UrlShorter = new Base62UrlShorter();

    @Test
    public void toShortUrl() {
        String url = "http://www.baidu.com/1112312311";
        String result = base62UrlShorter.shorten(url);
        assertTrue(result != null);
    }

    @Test
    public void toLongUrl() {
        String url = "http://www.baidu.com/1112312311";
        String result = base62UrlShorter.shorten(url);
        String lookup = base62UrlShorter.lookup(result);
        assertTrue(lookup != null);
    }

}
