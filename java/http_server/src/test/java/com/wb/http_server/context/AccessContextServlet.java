package com.wb.http_server.context;

import com.wb.http_server.request.Request;
import com.wb.http_server.response.Response;
import com.wb.http_server.servlet.impl.DefaultServlet;

/**
 * @author bing.wang
 * @date 2021/1/15
 */
public class AccessContextServlet extends DefaultServlet  {

    @Override
    public void service(Request request, Response response) {
        WebApplication.getServletContext().setAttribute("NULL", "aa");
        response.setBody("OK".getBytes());
    }
}
