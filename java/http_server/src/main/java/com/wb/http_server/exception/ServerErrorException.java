package com.wb.http_server.exception;

import com.wb.http_server.enumeration.HttpStatus;
import com.wb.http_server.exception.base.ServletException;

/**
 * @author bing.wang
 * @date 2021/1/15
 * 500异常
 */

public class ServerErrorException extends ServletException {
    private static final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    public ServerErrorException() {
        super(status);
    }
}
