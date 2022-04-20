package pers.zhufan.shorturl.storage;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.zhufan.shorturl.ShortUrlApplication;
import pers.zhufan.shorturl.domain.shorturl.ShorterUrl;
import pers.zhufan.shorturl.domain.shorturl.ShorterWithSimpleString;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortUrlApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShorterStorageMemoryTest {

    @Resource
    private ShorterStorage shorterStorage;

    @Test
    public void get() {

        this.save();

        String shortKey = "a9IuYb00";

        String s = shorterStorage.get(shortKey);

        Assert.assertEquals("http://www.baidu.cam/api/test/query/09846754834455",s);

    }

    @Test
    public void getNull() {

        this.save();

        String shortKey = "111111";

        String s = shorterStorage.get(shortKey);

        Assert.assertEquals(null,s);

    }

    @Test
    public void clean() {

        shorterStorage.clean();

    }
    @Test
    public void cleanUrl() {
        this.save();
        String longUrl = "http://www.baidu.cam/api/test/query/09846754834455";

        shorterStorage.clean(longUrl);

    }

    @Test
    public void cleanUrlNull() {
        String longUrl = "http://www.baidu.cam/api/test/query/09846754834455";

        shorterStorage.clean(longUrl);

    }

    @Test
    public void cleanShorter() {

        this.save();
        String shortKey = "a9IuYb00";
        shorterStorage.cleanShorter(shortKey);

        String s = shorterStorage.get(shortKey);

        Assert.assertEquals(null,s);

    }

    @Test
    public void cleanShorterNull() {

        this.save();
        String shortKey = "11111";
        shorterStorage.cleanShorter(shortKey);

        String s = shorterStorage.get(shortKey);

        Assert.assertEquals(null,s);

    }

    @Test
    public void save() {

        String longUrl = "http://www.baidu.cam/api/test/query/09846754834455";

        ShorterUrl shortUrl = new ShorterWithSimpleString("a9IuYb00");

        shorterStorage.save(longUrl,shortUrl);

    }

}