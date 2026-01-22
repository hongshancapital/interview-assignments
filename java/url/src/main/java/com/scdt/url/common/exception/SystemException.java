package com.scdt.url.common.exception;


import java.util.Map;

import static com.scdt.url.common.exception.ErrorCode.SYSTEM_ERROR;

public class SystemException extends AppException {

    public SystemException(Throwable cause) {
        super(SYSTEM_ERROR, Map.of("detail", cause.getMessage()), cause);
    }
}
