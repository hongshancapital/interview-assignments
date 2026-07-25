package com.example.shortlink;

import static org.junit.Assert.assertEquals;

import com.example.shortlink.controller.LinkController;
import com.example.shortlink.dto.BaseDataResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author tianhao.lan, {@literal <tianhao.lan@leyantech.com>}
 * @date 2021-12-29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTest {

  @Resource
  private LinkController linkController;

  @Test
  public void testGetUniqueId() {
    BaseDataResponse<String> shortResponse = linkController.longChangShort("http://www.baidu.com");
    BaseDataResponse<String> longResponse = linkController.shortChangeLong(shortResponse.getData());
    assertEquals(longResponse.getData(), "http://www.baidu.com");
  }
}
