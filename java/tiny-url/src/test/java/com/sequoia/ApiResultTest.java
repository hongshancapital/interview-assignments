package com.sequoia;

import com.sequoia.infrastructure.common.ApiResult;
import com.sequoia.infrastructure.common.StatusCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Descript:
 * File: com.sequoia.ApiResultTest
 * Author: daishengkai
 * Date: 2022/3/31
 * Copyright (c) 2022,All Rights Reserved.
 */
@Slf4j
public class ApiResultTest {

    @Test
    public void enumTest() {
        ApiResult errorResult = ApiResult.error(StatusCodeEnum.NOT_FOUND, "not found");
        Assertions.assertEquals(StatusCodeEnum.NOT_FOUND.getCode(), errorResult.getCode());

        for (StatusCodeEnum value : StatusCodeEnum.values()) {
            log.info("{}", ApiResult.error(value));
        }
    }
}
