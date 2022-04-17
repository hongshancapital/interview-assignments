package com.tb.link.qatest.service;

import com.tb.link.client.domain.Result;
import com.tb.link.client.domain.enums.ShortLinkErrorCodeEnum;
import com.tb.link.service.util.ExecuteFunUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

/**
 * @author andy.lhc
 * @date 2022/4/17 14:10
 */
public class ExecuteFunUtilTest {

    @Test
    public void testExecuteFunUtilThrow(){
        Result result =  ExecuteFunUtil.execute("t","t",()->{
           String str = "" ;
           if(StringUtils.isBlank(str)){
               throw new NullPointerException("npe") ;
           }
           return Result.success(null) ;
        });
        Assertions.assertEquals(result.getErrorCode(), ShortLinkErrorCodeEnum.SYSTEM_ERROR.getErrorCode());
    }
}
