package com.wb.http_server.request;

import com.wb.http_server.response.Response;
import com.wb.http_server.servlet.impl.DefaultServlet;

/**
 * @author bing.wang
 * @date 2021/1/15
 */
public class RequestServlet extends DefaultServlet  {

    @Override
    public void service(Request request, Response response) {

        String param = request.getParameter("param");

        request.setAttribute("param",param);

        response.setBody(param.getBytes());
    }
}
