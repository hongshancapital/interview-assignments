package cn.sequoiacap.interview.xurl.controller;

import cn.sequoiacap.interview.xurl.config.AppConfig;
import cn.sequoiacap.interview.xurl.service.XurlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
public class XurlControllerTest {
  @InjectMocks private XurlController xurlController;

  @Mock private XurlService xurlService;

  @Mock private AppConfig appConfig;

  @BeforeTestClass
  public void init() {
    Mockito.when(appConfig.getShortDomain()).thenReturn("t.cn");
  }

  @Test
  public void testDecodeXurl_Ok() {
    String url = "https://www.baidu.com";
    Mockito.when(xurlService.getOriUrl(Mockito.anyString())).thenReturn(url);
    try {
      String res = xurlController.decodeXurl("tt");
      Assertions.assertEquals(url, res);
    } catch (ResponseStatusException e) {
      Assertions.fail();
    }
  }

  @Test
  public void testDecodeXurl_Fail() {
    try {
      xurlController.decodeXurl("tt");
      Assertions.fail();
    } catch (ResponseStatusException e) {
      Assertions.assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
    }
  }

  @Test
  public void testGenXurl_Ok() throws XurlService.GenerateError {
    String shortCode = "tt";
    Mockito.when(xurlService.getShortCode(Mockito.anyString())).thenReturn(shortCode);
    try {
      String res = xurlController.genXurl("https://www.baidu.com");
      Assertions.assertEquals(
          String.format("https://%s/%s", appConfig.getShortDomain(), shortCode), res);
    } catch (ResponseStatusException e) {
      Assertions.fail();
    }
  }

  @Test
  public void testGenXurl_Fail() throws XurlService.GenerateError {
    Mockito.when(xurlService.getShortCode(Mockito.anyString()))
        .thenThrow(XurlService.GenerateError.class);
    try {
      xurlController.genXurl("https://www.baidu.com");
      Assertions.fail();
    } catch (ResponseStatusException e) {
      Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED, e.getStatus());
    }
  }

  @Test
  public void testGenXurl_InvalidUrl() {
    try {
      xurlController.genXurl("xxxxx");
      Assertions.fail();
    } catch (ResponseStatusException e) {
      Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
    }
  }
}
