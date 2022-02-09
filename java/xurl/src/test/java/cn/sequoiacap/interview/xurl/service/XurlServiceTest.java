package cn.sequoiacap.interview.xurl.service;

import cn.sequoiacap.interview.xurl.util.IDGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class XurlServiceTest {

  @Autowired private XurlService xurlService;

  private void normalProcessCheck() {
    String url = "https://www.baidu.com";
    String shortCode = "2Bi";
    try {
      String ret = xurlService.getShortCode(url);
      Assertions.assertEquals(shortCode, ret);
    } catch (XurlService.GenerateError e) {
      Assertions.fail();
    }

    String ret = xurlService.getOriUrl(shortCode);
    Assertions.assertEquals(url, ret);
  }

  @Test
  public void testNormalProcess_Ok() {
    try (MockedStatic<IDGenerator> generator = Mockito.mockStatic(IDGenerator.class)) {
      generator.when(IDGenerator::generate).thenReturn(10000L);
      normalProcessCheck();
    }
  }

  @Test
  public void testNormalProcess_OkWithRetry() {
    try (MockedStatic<IDGenerator> generator = Mockito.mockStatic(IDGenerator.class)) {
      generator
          .when(IDGenerator::generate)
          .thenThrow(IDGenerator.OutOfIndexError.class)
          .thenReturn(10000L);
      normalProcessCheck();
    }
  }

  @Test
  public void testGetShortCode_Fail() {
    try (MockedStatic<IDGenerator> generator = Mockito.mockStatic(IDGenerator.class)) {
      generator.when(IDGenerator::generate).thenThrow(IDGenerator.OutOfIndexError.class);
      try {
        xurlService.getShortCode("https://www.test.com");
        Assertions.fail();
      } catch (XurlService.GenerateError e) {
        Assertions.assertTrue(e.getMessage().contains("failed"));
      }
    }
  }
}
