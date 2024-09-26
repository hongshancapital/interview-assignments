package com.example.shortlink;

import static org.junit.Assert.assertEquals;

import com.example.shortlink.dto.BaseDataResponse;
import com.example.shortlink.dto.BaseResponse;
import com.example.shortlink.exception.CommonRespEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tianhao.lan, {@literal <tianhao.lan@leyantech.com>}
 * @date 2021-12-29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ResponseTest {

  @Test
  public void testGetUniqueId(){
    assertEquals("0",BaseResponse.success().getResultCode());
    assertEquals("0",BaseDataResponse.success().getResultCode());
    assertEquals("1",BaseResponse.error().getResultCode());
    assertEquals(CommonRespEnum.INPUT_PARAM_ERROR.getCode(),BaseResponse.error(CommonRespEnum.INPUT_PARAM_ERROR.getCode(), CommonRespEnum.INPUT_PARAM_ERROR.getMessage()).getResultCode());
  }

}
