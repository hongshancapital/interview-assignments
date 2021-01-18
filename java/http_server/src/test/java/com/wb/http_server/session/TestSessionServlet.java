package com.wb.http_server.session;

import com.wb.http_server.exception.base.ServletException;
import com.wb.http_server.request.Request;
import com.wb.http_server.response.Response;
import com.wb.http_server.servlet.impl.DefaultServlet;

import java.io.IOException;

public class TestSessionServlet extends DefaultServlet {

    @Override
    public void service(Request request, Response response) throws IOException {
        response.setContentType("text/plain");

        HttpSession session = request.getSession(true);
        session.setAttribute("test","test");
        session.removeAttribute("test");
        session.invalidate();
        boolean result;
        try {
            session.getLastAccessed();
            result = false;
        } catch (IllegalStateException ise) {
            result = true;
        }
        if (result) {
            response.setBody("PASS".getBytes("utf-8"));
        } else {
            response.setBody("FAIL".getBytes("utf-8"));
        }
        response.getRequestHandler().flushResponse();
    }
}