package com.wb.http_server.exception;

import com.wb.http_server.enumeration.HttpStatus;
import com.wb.http_server.exception.base.ServletException;

/**
 * @author bing.wang
 * @date 2021/1/15
 * servlet 没有找到
 */


public class ServletNotFoundException extends ServletException {
    private static final HttpStatus status = HttpStatus.NOT_FOUND;
    public ServletNotFoundException() {
        super(status);
    }
}
