package com.wb.http_server.request.dispatcher;

import com.wb.http_server.exception.base.ServletException;
import com.wb.http_server.request.Request;
import com.wb.http_server.response.Response;

import java.io.IOException;

/**
 * @author bing.wang
 * @date 2021/1/15
 */
public interface RequestDispatcher {
    
    void forward(Request request, Response response) throws ServletException, IOException;
}
