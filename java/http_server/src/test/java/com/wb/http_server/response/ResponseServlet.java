package com.wb.http_server.response;

import com.wb.http_server.enumeration.HttpStatus;
import com.wb.http_server.request.Request;
import com.wb.http_server.servlet.impl.DefaultServlet;

/**
 * @author bing.wang
 * @date 2021/1/15
 */
public class ResponseServlet extends DefaultServlet  {

    @Override
    public void service(Request request, Response response) {
        response.addHeader(new Header("testHeader","test"));
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.getRequestHandler().flushResponse();
    }
}
