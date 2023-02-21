package com.example.shortlink;

import static org.junit.Assert.assertEquals;

import com.example.shortlink.service.KeyService;
import com.example.shortlink.service.LinkService;
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
public class LinkServiceTest {

  @Resource
  private LinkService linkService;

  @Test
  public void testGetUniqueId(){
    String shortLink = linkService.longChangShort("http://www.baidi.com");
    String longLink = linkService.shortChangeLong(shortLink);
    assertEquals("http://www.baidi.com",longLink);
  }

}
