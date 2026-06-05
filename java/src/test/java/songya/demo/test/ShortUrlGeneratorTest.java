package songya.demo.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import songya.demo.util.ShortUrlGenerator;

@SpringBootTest
public class ShortUrlGeneratorTest {

    @Test
    public void  shortUrlGeneratorTest(){
        Assert.assertNotNull(ShortUrlGenerator.shortUrl("https://baidu.com"));
    }

}
