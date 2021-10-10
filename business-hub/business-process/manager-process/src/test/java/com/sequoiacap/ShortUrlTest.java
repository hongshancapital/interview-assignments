package com.sequoiacap;

import com.sequoiacap.business.process.manager.controller.UrlShortController;
import com.sequoiacap.business.process.manager.controller.vo.GetLongUrlResVO;
import com.sequoiacap.business.process.manager.controller.vo.SaveShortUrlReqVO;
import com.sequoiacap.business.process.manager.controller.vo.SaveShortUrlResVO;
import com.sequoiacap.business.process.manager.domain.UrlShortDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: ControllerTest
 * @Description: test
 * @Author: xulong.wang
 * @Date 10/9/2021
 * @Version 1.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SequoiacapBootApplicationTest.class)
public class ShortUrlTest {

  @Autowired
  private UrlShortController urlShortController;

  @Autowired
  private UrlShortDao urlShortDao;

  @Test
  public void testGenerateShortUrl() {
    SaveShortUrlReqVO saveShortUrlReqVO = new SaveShortUrlReqVO();
    saveShortUrlReqVO.setLongUrl("test.sequoiacap.com");
    SaveShortUrlResVO saveShortUrlResVO = urlShortController.saveShortUrl(saveShortUrlReqVO);
    Assert.assertEquals(1, saveShortUrlResVO.getCode());
  }

  @Test
  public void testGenerateShortUrlFail() {
    SaveShortUrlReqVO saveShortUrlReqVO = new SaveShortUrlReqVO();
    saveShortUrlReqVO.setLongUrl("");
    SaveShortUrlResVO saveShortUrlResVO = urlShortController.saveShortUrl(saveShortUrlReqVO);
    Assert.assertEquals(2, saveShortUrlResVO.getCode());
  }

  @Test
  public void testGetLongUrl() {
    SaveShortUrlReqVO saveShortUrlReqVO = new SaveShortUrlReqVO();
    saveShortUrlReqVO.setLongUrl("test.sequoiacap.com");
    SaveShortUrlResVO saveShortUrlResVO = urlShortController.saveShortUrl(saveShortUrlReqVO);
    GetLongUrlResVO getLongUrlResVO = urlShortController.getLongUrl(saveShortUrlResVO.getShortUrl());
    Assert.assertEquals(1, getLongUrlResVO.getCode());
  }

  @Test
  public void testSaveDao() {
    for(int i=0;i<101000;i++){
      urlShortDao.save("longurlaaaa"+i,"su"+i);
    }
  }

  @Test
  public void testGetDao() {
    String longUrl =  urlShortDao.get("111111");
    Assert.assertNull(longUrl);
  }

  @Test
  public void testCleanDao() {
    SaveShortUrlReqVO saveShortUrlReqVO = new SaveShortUrlReqVO();
    saveShortUrlReqVO.setLongUrl("test.sequoiacap.com");
    SaveShortUrlResVO saveShortUrlResVO = urlShortController.saveShortUrl(saveShortUrlReqVO);
    urlShortDao.clean(saveShortUrlResVO.getShortUrl());
  }
}
