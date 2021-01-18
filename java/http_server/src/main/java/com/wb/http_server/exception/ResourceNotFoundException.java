package com.wb.http_server.exception;

import com.wb.http_server.enumeration.HttpStatus;
import com.wb.http_server.exception.base.ServletException;

/**
 * @author bing.wang
 * @date 2021/1/15
 * 资源没找到
 */

public class ResourceNotFoundException extends ServletException {
    private static final HttpStatus status = HttpStatus.NOT_FOUND;
    public ResourceNotFoundException() {
        super(status);
    }
}
