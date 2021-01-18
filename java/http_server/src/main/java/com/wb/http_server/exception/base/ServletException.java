package com.wb.http_server.exception.base;

import com.wb.http_server.enumeration.HttpStatus;
import lombok.Getter;

/**
 * @author bing.wang
 * @date 2021/1/15
 * 根异常
 */

@Getter
public class ServletException extends Exception {
    private HttpStatus status;
    public ServletException(HttpStatus status){
        this.status = status;
    }
}
