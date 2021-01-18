package com.wb.http_server.exception;

import com.wb.http_server.enumeration.HttpStatus;
import com.wb.http_server.exception.base.ServletException;

/**
 * @author bing.wang
 * @date 2021/1/15
 * 请求数据不合法
 */
public class RequestInvalidException extends ServletException {
    private static final HttpStatus status = HttpStatus.BAD_REQUEST;
    public RequestInvalidException() {
        super(status);
    }
}
