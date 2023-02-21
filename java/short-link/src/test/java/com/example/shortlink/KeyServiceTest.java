package com.example.shortlink;

import static org.junit.Assert.assertEquals;

import com.example.shortlink.service.KeyService;
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
public class KeyServiceTest {

  @Resource
  private KeyService keyService;

  @Test
  public void testGetUniqueId(){
    long id = keyService.getUniqueId();
    assertEquals(id,id);
  }
}
