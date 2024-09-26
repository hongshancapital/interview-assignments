package com.zc.shorturl.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class BaseResponseTest {
    @Test
    public void testBaseResponse() {
        BaseResponse<Object> baseResponse = BaseResponse.ok(null);
        log.info(baseResponse.toString());
    }
}
