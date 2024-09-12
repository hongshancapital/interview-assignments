package com.shortlink.handler;

import com.shortlink.common.GeneralException;
import com.shortlink.entity.CreateShortLinkRequest;
import com.shortlink.entity.FetchOriginalUrlRequest;
import com.shortlink.entity.ShortLinkEntity;
import com.shortlink.util.CaffeineUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortLinkGeneratorTest {

    @Autowired
    private ShortLinkHandler shortLinkHandler;

    @Test
    public void testCreateShortLink() {
        CreateShortLinkRequest request = CreateShortLinkRequest.builder().url("www.baidu.com").reqId("test").appId(0).build();
        Assert.assertNotNull(shortLinkHandler.createShortLink(request));
    }

    @Test
    public void testFetchUrlByShortLink() {
        CreateShortLinkRequest createRequest = CreateShortLinkRequest.builder().url("www.baidu.com").reqId("test").appId(0).build();
        ShortLinkEntity entity = shortLinkHandler.createShortLink(createRequest);
        Assert.assertNotNull(entity);

        FetchOriginalUrlRequest  fetchRequest = FetchOriginalUrlRequest.builder().appId(0).shortLink(entity.getShortLink()).reqId("test").build();
        ShortLinkEntity entity1 = (ShortLinkEntity) CaffeineUtil.get(entity.getShortLink());
        System.out.println("entity1="+entity1);
        ShortLinkEntity entity2 = shortLinkHandler.fetchUrlByShortLink(fetchRequest);
        System.out.println("entity2="+entity2);
        Assert.assertEquals(entity1.getShortLink(), entity2.getShortLink());
    }


    @Test(expected = GeneralException.class)
    public void testException() {
        CreateShortLinkRequest createRequest = CreateShortLinkRequest.builder().url("").reqId("test").appId(0).build();
        shortLinkHandler.createShortLink(createRequest);

        FetchOriginalUrlRequest  fetchRequest = FetchOriginalUrlRequest.builder().appId(0).shortLink("").reqId("test").build();
        shortLinkHandler.fetchUrlByShortLink(fetchRequest);

        fetchRequest.setShortLink("SHFLY27F");
        shortLinkHandler.fetchUrlByShortLink(fetchRequest);
        createRequest.setUrl("www.58.com");

        ShortLinkEntity entity = shortLinkHandler.createShortLink(createRequest);
        CaffeineUtil.del(entity.getShortLink());
        fetchRequest.setShortLink(entity.getShortLink());
        Assert.assertNull(shortLinkHandler.fetchUrlByShortLink(fetchRequest));
    }
}
