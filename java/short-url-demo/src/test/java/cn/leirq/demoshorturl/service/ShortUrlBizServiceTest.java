package cn.leirq.demoshorturl.service;

import cn.leirq.demoshorturl.model.ShortUrlModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShortUrlBizServiceTest {

    @Autowired
    private ShortUrlBizService shortUrlBizService;

    @Autowired
    private ShortUrlSaveService shortUrlSaveService;

    @Test
    public void createAndSaveByLongUrlTest(){
        String longUrl = "www.leirq.test/abc";
        String shortUrl_1 = shortUrlBizService.createAndSaveByLongUrl(longUrl);
        System.out.println(shortUrl_1);

        // 模拟重复长链接生成
        String shortUrl_2 = shortUrlBizService.createAndSaveByLongUrl(longUrl);
        Assertions.assertEquals(shortUrl_1, shortUrl_2);

        // 模拟hash算法撞车，会添加对长url添加后缀再hash生成新的短链接
        ShortUrlModel shortUrlModel = shortUrlSaveService.getByShortUrl(shortUrl_1);
        // 手动修改对象的值, 让longUrl不同， 触发
        shortUrlModel.setLongUrl(longUrl+"abc");
        // 再次生成短链接， 希望新的短链接和之前不同
        String shortUrl_3 = shortUrlBizService.createAndSaveByLongUrl(longUrl);
        System.out.println(shortUrl_3);
        Assertions.assertNotEquals(shortUrl_1, shortUrl_3);

        // 短url3 的来源 长url仍然等于初始值
        String longUrl3 = shortUrlBizService.getLongUrlByShortUrl(shortUrl_3);
        Assertions.assertEquals(longUrl3, longUrl);

        // 检查是否有添加随机后缀
        ShortUrlModel tmpShortUrlModel =shortUrlSaveService.getByShortUrl(shortUrl_3);
        System.out.println(tmpShortUrlModel.getRandomSuffix());

        // 短url1 对应的长url和 手动修改的值一致
        String longUrl1 = shortUrlBizService.getLongUrlByShortUrl(shortUrl_1);
        Assertions.assertEquals(longUrl+"abc", longUrl1);

        // 不存在的短url 匹配的长url为""
        String longUrl0 = shortUrlBizService.getLongUrlByShortUrl("testabc");
        Assertions.assertEquals("", longUrl0);


    }
}
