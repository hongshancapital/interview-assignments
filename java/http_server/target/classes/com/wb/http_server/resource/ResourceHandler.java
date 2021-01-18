package com.wb.http_server.resource;

import com.wb.http_server.exception.handler.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bing.wang
 * @date 2021/1/15
 * 处理静态资源
 */
@Slf4j
public class ResourceHandler {

    private ExceptionHandler exceptionHandler;

    public ResourceHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }
}