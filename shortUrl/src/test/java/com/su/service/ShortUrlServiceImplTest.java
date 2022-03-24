package com.su.service;

import com.su.service.impl.ShortUrlServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class ShortUrlServiceImplTest {


    @Value("${su.prefix}")
    private String prefix;

    @Autowired
    private IShortUrlService shortUrlService;

    @Test
    public void getShortUrl() {
       String s1 =  shortUrlService.getShortUrl("http://www.baidu.com");
       String s2 =  shortUrlService.getShortUrl("http://www.baidu.com");
       Assert.isTrue(s1.equals(s2));

       IShortUrlService service = new ShortUrlServiceImpl(prefix,new MyUrlSuffixServiceUImpl(3));
       String s3 =  service.getShortUrl("http://www.huya.com");
       String s4 =  service.getShortUrl("http://www.douyu.com");
    }

    @Test
    public void getLongUrl() {

        Assertions.assertThrows(RuntimeException.class,
                () -> shortUrlService.getLongUrl("aaaa"));

        String longUrl = "http://www.baidu.com";
        String url = shortUrlService.getShortUrl(longUrl);
        Assert.isTrue(longUrl.equals(shortUrlService.getLongUrl(url)));
    }

    static class MyUrlSuffixServiceUImpl implements IUrlSuffixService{
        private int repeatTimes;
        private final int originRepeatTimes;
        public MyUrlSuffixServiceUImpl(int repeatTimes){
            this.originRepeatTimes = repeatTimes;
            this.repeatTimes = originRepeatTimes;
        }
        @Override
        public String getSuffix(String url) {
            while (repeatTimes > 0 ) {
                repeatTimes --;
                return "12345678";
            }
            repeatTimes = originRepeatTimes;
            return "87654321";
        }
    }


}
