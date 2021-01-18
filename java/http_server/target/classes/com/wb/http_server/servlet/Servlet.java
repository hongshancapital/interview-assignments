package com.wb.http_server.servlet;

import com.wb.http_server.exception.base.ServletException;
import com.wb.http_server.request.Request;
import com.wb.http_server.response.Response;

import java.io.IOException;

/**
 * @author bing.wang
 * @date 2021/1/15
 */
public interface Servlet {
    void init();

    void destroy();

    void service(Request request, Response response) throws ServletException, IOException;
}
