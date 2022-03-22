package cn.scdt.shorturl;

import cn.scdt.shorturl.exception.SystemErrorType;
import cn.scdt.shorturl.service.ShortURLService;
import cn.scdt.shorturl.service.impl.ShortURLServiceImpl;
import cn.scdt.shorturl.utils.HashUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortURLApplicationTests {

    @Autowired
    private ShortURLService shortURLService;

    @Test
    public void urlTest() {
        String longURL="https://www.baidu.com/";
        String shortURL = shortURLService.getShortURL(longURL, HashUtils.hashToBase62(longURL));
        Assert.assertTrue("链接转换成功", longURL.equals(shortURLService.getFullURL(shortURL)));

        //重复KEY测试
        String longURL2 = "https://www.baidu.com/2";
        String shortURL3 = shortURLService.getShortURL(longURL2, HashUtils.hashToBase62(longURL));
        //传入"https://www.baidu.com/"的短链，制造key重复场景
        String shortURL4 = shortURLService.getShortURL(longURL, "WXEoK");
        Assert.assertNotEquals(shortURL3,shortURL4);


    }

    @Test
    public void longUrlTest() {
        String shortURL = "4Xcft3";
        String longURL = "https://www.baidu.com/";
        Assert.assertTrue(SystemErrorType.NOT_EXISTS_FULLURL.getMesg(), !longURL.equals(shortURLService.getFullURL(shortURL)));
    }
}
