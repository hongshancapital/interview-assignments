package com.zc.shorturl.snowflake.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ResultTest {
    @Test
    public void testResult(){
        Result result = new Result();
        result.setId(1);
        result.setMessage("test");
        result.setIdStatus(IdStatus.SUCCESS);
        result.setIdStatus(IdStatus.EXCEPTION);
        log.info(result.toString());
    }
}
