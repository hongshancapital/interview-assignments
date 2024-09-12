package cn.leirq.demoshorturl.service;


import cn.leirq.demoshorturl.model.ShortUrlModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShortUrlSaveServiceTest {

    @Autowired
    private ShortUrlSaveService shortUrlSaveService;

    @Test
    public void getByShortUrlTest(){
        String shortUrl = "abc";
        ShortUrlModel shortUrlModel = shortUrlSaveService.getByShortUrl(shortUrl);
        Assertions.assertEquals(shortUrlModel, null);
    }

    @Test
    public void saveModelTest(){
        String longUrl = "www.leirq.test/abc";
        String shortUrl = "1234";
        ShortUrlModel model = new ShortUrlModel();
        model.setLongUrl(longUrl);
        model.setShortUrl(shortUrl);
        // 新对象保存，期望true
        boolean saveResult = shortUrlSaveService.saveModel(model);
        Assertions.assertEquals(true, saveResult);

        // 重复url对象保存， 期望返回true
        ShortUrlModel model2 = new ShortUrlModel();
        model2.setLongUrl(longUrl);
        model2.setShortUrl(shortUrl);
        boolean saveResult2 = shortUrlSaveService.saveModel(model2);
        Assertions.assertEquals(true, saveResult2);

        // 再次重复url对象保存， 期望返回true
        ShortUrlModel model3 = new ShortUrlModel();
        model3.setLongUrl(longUrl);
        model3.setShortUrl(shortUrl);
        boolean saveResult3 = shortUrlSaveService.saveModel(model3);
        Assertions.assertEquals(true, saveResult3);

        // 替换长链接内容，模拟hash算法撞车场景，保存，由于短链接相同， 而长链接不同， 期望返回 false
        ShortUrlModel model4 = new ShortUrlModel();
        model4.setShortUrl(shortUrl);
        model4.setLongUrl(longUrl+"test");
        boolean saveResult4 = shortUrlSaveService.saveModel(model4);
        Assertions.assertEquals(false, saveResult4);
    }


}
